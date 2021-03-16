package com.energytrade.app.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
//import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.energytrade.app.dto.AllDsoDto;
import com.energytrade.app.dto.AllEventDto;
import com.energytrade.app.dto.AllEventSetDto;
import com.energytrade.app.dto.EventCustomerDto;
//import com.energytrade.app.model.AllContract;
import com.energytrade.app.model.AllDso;
import com.energytrade.app.model.AllEvent;
import com.energytrade.app.model.AllEventSet;
//import com.energytrade.app.model.AllForecast;
//import com.energytrade.app.model.AllOtp;
//import com.energytrade.app.model.AllSellOrder;
//import com.energytrade.app.model.AllTimeslot;
import com.energytrade.app.model.AllUser;
import com.energytrade.app.model.DREventTypePl;
//import com.energytrade.app.model.EventCustomerDevices;
//import com.energytrade.app.model.ContractStatusPl;
//import com.energytrade.app.model.DevicePl;
import com.energytrade.app.model.EventCustomerMapping;
//import com.energytrade.app.model.EventCustomerStatusPl;
import com.energytrade.app.model.EventSetStatusPl;
import com.energytrade.app.model.EventSetVersionHistory;
import com.energytrade.app.model.EventStatusPl;
import com.energytrade.app.model.GeneralConfig;
//import com.energytrade.app.model.NonTradehourStatusPl;
//import com.energytrade.app.model.NotificationRequestDto;
//import com.energytrade.app.model.OrderStatusPl;
import com.energytrade.app.model.UserAccessLevelMapping;
//import com.energytrade.app.model.UserDevice;
//import com.energytrade.app.model.UserRolesPl;
//import com.energytrade.app.util.ApplicationConstant;
import com.energytrade.app.util.CommonUtility;
import com.energytrade.app.util.CompareHelper;
import com.energytrade.app.util.CustomMessages;
//import com.energytrade.app.util.PushHelper;
//import com.energytrade.app.util.ValidationUtil;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Transactional
@Repository
public class DRDao extends AbstractBaseDao {
	@Autowired
	EventSetRepository eventsetrepo;

	@Autowired
	EventRepository eventrepo;

	@Autowired
	AllUserRepository alluserrepo;
	
	@Autowired
	AllDsoRepository alldsoRepo;

	@Autowired
	EventCustomerRepository eventcustomerrepo;
	
	@Autowired
	EventSetVersionHistoryRepository eventsetVersionHistRepo;
	
	@Autowired
	EventCustomerDevicesRepository eventcustomerdevicerepo;
	
	private int eventNameSuffix = 0;

	public HashMap<String, Object> createEventSet(String filePath, byte[] imageByte, String location, int userId, String date) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		HashMap<String, Object> internalresponse = new HashMap<String, Object>();
		try {
			Date today = new Date();
			DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date formatDate = targetFormat.parse(date);
			String todates= targetFormat.format(today);
			Date toDate = targetFormat.parse(todates);
			if (toDate.compareTo(formatDate) > 0) {
				response.put("responseStatus", "1");
				response.put("responseMessage", "Uploaded Date is before Current Date");
				response.put("response", internalresponse);
				response.put("customMessage", null);
				return response;
			}
			if (eventsetrepo.getEventSetCountPerDay(formatDate, userId, location) > 0) {
				response.put("responseStatus", "1");
				response.put("responseMessage", "File already uploaded with same date, user and location");
				response.put("response", internalresponse);
				response.put("customMessage", null);
				return response;
			}
			
			ArrayList<Object> eventSetObjects = createEventSetData(location, userId, date);
			createOrUpdateEventSetVersionHistoryData(imageByte, formatDate, (AllEventSet) eventSetObjects.get(1));	// This method is saving file as Base64 string in Database
			AllEventSetDto allEventSets = (AllEventSetDto) eventSetObjects.get(0);
			List<AllEventDto> listOfEvents = createFile(filePath, imageByte, eventSetObjects.get(1));
			allEventSets.setAllEvents(listOfEvents);
			ArrayList<String> powerAndPrice = getPower(listOfEvents);
			allEventSets.setPlannedPower(powerAndPrice.get(0));
			allEventSets.setTotalPrice(powerAndPrice.get(1));
			allEventSets.setActualPower("0");
			allEventSets.setCancelledEvents("0");
			allEventSets.setCompletedEvents("0");
			allEventSets.setPublishedEvents("0");
			eventsetrepo.updateEventSet(Double.parseDouble(powerAndPrice.get(0)),
					Double.parseDouble(powerAndPrice.get(1)), allEventSets.getEventSetId());
			internalresponse.put("eventSet", allEventSets);

			response.put("responseStatus", "1");
			response.put("responseMessage", "The request was successfully served.");
			response.put("response", internalresponse);
			response.put("customMessage", null);

		} catch (Exception e) {
			e.printStackTrace();
			response.put("responseStatus", "2");
			response.put("responseMessage", "Internal Server Error.");
			response.put("customMessage", null);
			response.put("response", null);
		} finally {

		}
		return response;
	}
	
	public HashMap<String, Object> updateEventSet(String filePath, byte[] imageByte, int eventSetId) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		HashMap<String, Object> internalresponse = new HashMap<String, Object>();
		try {
			AllEventSet eventSet = eventsetrepo.getEventSet(eventSetId);
			AllUser alluser = eventSet.getAllUser();
			Date today = new Date();
			DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date formatDate = eventSet.getDate();
			String todates= targetFormat.format(today);
			Date toDate = targetFormat.parse(todates);
			if (toDate.compareTo(formatDate) > 0) {
				response.put("responseStatus", "1");
				response.put("responseMessage", "Uploaded Date is before Current Date");
				response.put("response", internalresponse);
				response.put("customMessage", null);
				return response;
			}
			if (eventsetrepo.getEventSetCountPerDay(formatDate, alluser.getUserId(),eventSet.getDivison()) == 0) {
				response.put("responseStatus", "1");
				response.put("responseMessage", "No event set for this location and date");
				response.put("response", internalresponse);
				response.put("customMessage", null);
				return response;
			}
			
			int userId = alluser.getUserId();
			AllEventSet alleventset = eventsetrepo.getEventSet(eventSetId);
			List<AllEvent> allEventList = eventrepo.getLatestEvent(eventSetId);
			
			int createdStatusId = eventrepo.getEventStatus("Created").getEventStatusId();
			int publishedStatusId = eventrepo.getEventStatus("Published").getEventStatusId();
			int cancelledStatusId = eventrepo.getEventStatus("Cancelled").getEventStatusId();
			int expiredStatusId = eventrepo.getEventStatus("Expired").getEventStatusId();
			
			List<AllEvent> CreatedEvents = new ArrayList<>();
			List<AllEvent> PublishedEvents = new ArrayList<>();
			List<AllEvent> cancelledEvents = new ArrayList<>();
			List<AllEvent> expireEvents = new ArrayList<>();
			
			Iterator<AllEvent> itr = allEventList.iterator();
			while(itr.hasNext()) {
				AllEvent event = itr.next();
				if(event.getEventStatusPl().getEventStatusId() == createdStatusId) {
					CreatedEvents.add(event);
					itr.remove();
				}
				if(event.getEventStatusPl().getEventStatusId() == publishedStatusId) {
					PublishedEvents.add(event);
					itr.remove();
				}
				if(event.getEventStatusPl().getEventStatusId() == cancelledStatusId) {
					cancelledEvents.add(event);
					itr.remove();
				}
				if(event.getEventStatusPl().getEventStatusId() == expiredStatusId) {
					expireEvents.add(event);
					itr.remove();
				}
			}
			
			if(allEventList.size()>0) {
				AllEvent currentLastEvent = allEventList.get(0);
				this.eventNameSuffix = Integer.parseInt(currentLastEvent.getEventName().substring(alleventset.getName().length()));
			}
			
			EventSetVersionHistory eventSetVerHist = createOrUpdateEventSetVersionHistoryData(imageByte, formatDate, eventSet);	// This method is saving file as Base64 string in Database
			AllEventSetDto allEventSets = new AllEventSetDto();
			allEventSets.setEventSetId(eventSetId);
			allEventSets.setEventSetName(eventSet.getName());
			allEventSets.setUserId(userId);
			allEventSets.setUserName(alluser.getFullName());
			allEventSets.setEventSetStatus("Created");
			allEventSets.setDateOfOccurence(eventSet.getUploadTime().toString());
			List<AllEventDto> listOfEvents = createFile(filePath, imageByte, eventSet);
			
			allEventSets.setAllEvents(listOfEvents);
			ArrayList<String> powerAndPrice = getPower(listOfEvents);
			allEventSets.setPlannedPower(powerAndPrice.get(0));
			allEventSets.setTotalPrice(powerAndPrice.get(1));
			allEventSets.setActualPower("0");
			allEventSets.setCancelledEvents("0");
			allEventSets.setCompletedEvents("0");
			allEventSets.setPublishedEvents("0");
			eventsetrepo.updateEventSet(Double.parseDouble(powerAndPrice.get(0)),
					Double.parseDouble(powerAndPrice.get(1)), allEventSets.getEventSetId());
			
			deletionWhileUpdatingEventSetData(CreatedEvents);
			deletionWhileUpdatingEventSetData(PublishedEvents);
			deletionWhileUpdatingEventSetData(cancelledEvents);
			deletionWhileUpdatingEventSetData(expireEvents);
			eventsetrepo.updateVersion(eventSetId, eventSetVerHist.getVersion());
			
			this.eventNameSuffix = 0;
			internalresponse.put("eventSet", allEventSets);

			response.put("responseStatus", "1");
			response.put("responseMessage", "The request was successfully served.");
			response.put("response", internalresponse);
			response.put("customMessage", null);

		} catch (Exception e) {
			e.printStackTrace();
			response.put("responseStatus", "2");
			response.put("responseMessage", "Internal Server Error.");
			response.put("customMessage", null);
			response.put("response", null);
		} finally {

		}
		return response;
	}
	
	public HashMap<String, Object> restoreEventSet(String filePath, int eventSetId, int version) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		HashMap<String, Object> internalresponse = new HashMap<String, Object>();
		try {
			EventSetVersionHistory eventSet = eventsetVersionHistRepo.getByEventSeIdAndVersion(eventSetId, version);
			int eventSetCount = eventsetVersionHistRepo.getCountByEventSetId(eventSetId);
			if (eventSetCount == 0) {
				response.put("responseStatus", "1");
				response.put("responseMessage", "Incorrect eventSet");
				response.put("response", internalresponse);
				response.put("customMessage", null);
				return response;
			}
			if (eventSet == null) {
				response.put("responseStatus", "1");
				response.put("responseMessage", "Input version not available for eventSet");
				response.put("response", internalresponse);
				response.put("customMessage", null);
				return response;
			}
			AllEventSet allEventSet = eventSet.getAllEventSet();
			AllUser alluser = allEventSet.getAllUser();
			int userId = alluser.getUserId();
			
			List<AllEvent> allEventList = eventrepo.getLatestEvent(eventSetId);
			
			int createdStatusId = eventrepo.getEventStatus("Created").getEventStatusId();
			int publishedStatusId = eventrepo.getEventStatus("Published").getEventStatusId();
			int cancelledStatusId = eventrepo.getEventStatus("Cancelled").getEventStatusId();
			int expiredStatusId = eventrepo.getEventStatus("Expired").getEventStatusId();
			
			List<AllEvent> CreatedEvents = new ArrayList<>();
			List<AllEvent> PublishedEvents = new ArrayList<>();
			List<AllEvent> cancelledEvents = new ArrayList<>();
			List<AllEvent> expireEvents = new ArrayList<>();
			
			Iterator<AllEvent> itr = allEventList.iterator();
			while(itr.hasNext()) {
				AllEvent event = itr.next();
				if(event.getEventStatusPl().getEventStatusId() == createdStatusId) {
					CreatedEvents.add(event);
					itr.remove();
				}
				if(event.getEventStatusPl().getEventStatusId() == publishedStatusId) {
					PublishedEvents.add(event);
					itr.remove();
				}
				if(event.getEventStatusPl().getEventStatusId() == cancelledStatusId) {
					cancelledEvents.add(event);
					itr.remove();
				}
				if(event.getEventStatusPl().getEventStatusId() == expiredStatusId) {
					expireEvents.add(event);
					itr.remove();
				}
			}
			
			if(allEventList.size()>0) {
				AllEvent currentLastEvent = allEventList.get(0);
				this.eventNameSuffix = Integer.parseInt(currentLastEvent.getEventName().substring(allEventSet.getName().length()));
			}
			
			AllEventSetDto allEventSets = new AllEventSetDto();
			allEventSets.setEventSetId(eventSetId);
			allEventSets.setEventSetName(allEventSet.getName());
			allEventSets.setUserId(userId);
			allEventSets.setUserName(alluser.getFullName());
			allEventSets.setEventSetStatus("Created");
			allEventSets.setDateOfOccurence(allEventSet.getUploadTime().toString());
			List<AllEventDto> listOfEvents = createFile(filePath, Base64.decodeBase64(eventSet.getUploadedFile()), allEventSet);
			
			allEventSets.setAllEvents(listOfEvents);
			ArrayList<String> powerAndPrice = getPower(listOfEvents);
			allEventSets.setPlannedPower(powerAndPrice.get(0));
			allEventSets.setTotalPrice(powerAndPrice.get(1));
			allEventSets.setActualPower("0");
			allEventSets.setCancelledEvents("0");
			allEventSets.setCompletedEvents("0");
			allEventSets.setPublishedEvents("0");
			eventsetrepo.updateEventSet(Double.parseDouble(powerAndPrice.get(0)),
					Double.parseDouble(powerAndPrice.get(1)), allEventSets.getEventSetId());
			deletionWhileUpdatingEventSetData(CreatedEvents);
			deletionWhileUpdatingEventSetData(PublishedEvents);
			deletionWhileUpdatingEventSetData(cancelledEvents);
			deletionWhileUpdatingEventSetData(expireEvents);
			eventsetrepo.updateVersion(eventSetId, version);
			this.eventNameSuffix = 0;
			internalresponse.put("eventSet", allEventSets);

			response.put("responseStatus", "1");
			response.put("responseMessage", "The request was successfully served.");
			response.put("response", internalresponse);
			response.put("customMessage", null);

		} catch (Exception e) {
			e.printStackTrace();
			response.put("responseStatus", "2");
			response.put("responseMessage", "Internal Server Error.");
			response.put("customMessage", null);
			response.put("response", null);
		} finally {

		}
		return response;
	}
	
	public EventSetVersionHistory createOrUpdateEventSetVersionHistoryData(byte[] imageByte, Date uploadDate, AllEventSet eventSet) {
		EventSetVersionHistory eventSetVerHist = new EventSetVersionHistory();
		Timestamp ts = new Timestamp(uploadDate.getTime());
		int eventSetId = eventSet.getEventSetId();
		int version = eventsetVersionHistRepo.getLatestEventSetVersion(eventSetId) + 1;
		String uploadedFile = Base64.encodeBase64String(imageByte);
		eventSetVerHist.setAllEventSet(eventSet);
		eventSetVerHist.setVersion(version);
		eventSetVerHist.setUploadedFile(uploadedFile);
		eventSetVerHist.setCreatedTs(ts);
		
		eventsetVersionHistRepo.saveAndFlush(eventSetVerHist);
		return eventSetVerHist;
	}
	
	public void deletionWhileUpdatingEventSetData(List<AllEvent> eventsToBeDeleted) {
		Iterator<AllEvent> it = eventsToBeDeleted.iterator();
		while(it.hasNext()) {
			AllEvent event = it.next();
			int eventId = event.getEventId();
			
			eventcustomerdevicerepo.deleteByEventId(eventId);
			eventcustomerrepo.deleteByEventId(eventId);
			eventrepo.deleteById(eventId);
		}
	}

	public ArrayList<Object> createEventSetData(String location, int userId, String uploadDate) {
		AllEventSet alleventset1 = new AllEventSet();
		AllEventSetDto alleventsetdto = new AllEventSetDto();
		ArrayList<Object> listOfObjects = new ArrayList<Object>();
		try {
			int count = eventsetrepo.getEventSetCount() + 1;
			DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date formatDate = targetFormat.parse(uploadDate);
			Timestamp ts = new Timestamp(formatDate.getTime());
			AllUser alluser = eventsetrepo.getUserById(userId);
			AllEventSet alleventset = new AllEventSet();
			String dateArr[]=uploadDate.split("-");
			EventSetStatusPl eventsetstatuspl = eventsetrepo.getEventSetStatus("Created");
			alleventset.setName(dateArr[0]+dateArr[1]+dateArr[2]+location );
			alleventset.setAllUser(alluser);
			alleventset.setEventSetId(count);
			alleventset.setEventSetStatusPl(eventsetstatuspl);
			alleventset.setUploadTime(ts);
			alleventset.setDate(formatDate);
			alleventset.setDivison(location);
			alleventset.setActiveVersion(1);
			alleventsetdto.setEventSetName(dateArr[0]+dateArr[1]+dateArr[2]+location );
			alleventsetdto.setUserId(alluser.getUserId());
			alleventsetdto.setUserName(alluser.getFullName());
			alleventsetdto.setEventSetId(count);
			alleventsetdto.setEventSetStatus(eventsetstatuspl.getStatusName());
			alleventsetdto.setDateOfOccurence(ts.toString());
			eventsetrepo.saveAndFlush(alleventset);
			alleventset1 = eventsetrepo.getEventSet(count);
			listOfObjects.add(alleventsetdto);
			listOfObjects.add(alleventset1);
			// return alleventset1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return listOfObjects;

	}

	public List<EventCustomerDto> createEventCustomer(AllEvent event) {
		ArrayList<Object> listOfObjects = new ArrayList<Object>();
		List<EventCustomerMapping> listOfEventCustMapping = new ArrayList<EventCustomerMapping>();
		List<EventCustomerDto> listOfEventCustDto = new ArrayList<EventCustomerDto>();
		try {
			int count = eventcustomerrepo.getEventCustomerCount();
			List<UserAccessLevelMapping> listOfusers = eventcustomerrepo
					.getUserAccessLevel(event.getAllEventSet().getAllUser().getUserId(),event.getAllEventSet().getDivison());
			// EventCustomerStatusPl eventstatus =
			// eventcustomerrepo.getEventCustomerStatus(1);
			for (int j = 0; j < listOfusers.size(); j++) {
				count++;
				EventCustomerDto eventCustomerDto = new EventCustomerDto();
				EventCustomerMapping eventCustomerMapping = new EventCustomerMapping();
				eventCustomerMapping.setAllUser(listOfusers.get(j).getAllUser());
				eventCustomerMapping.setEventCustomerMappingId(count);
				eventCustomerMapping.setAllEvent(event);
				eventCustomerMapping.setCounterBidAmount(0);
				eventCustomerMapping.setCommitedPower(0);
				eventCustomerMapping.setActualPower(0);
				eventCustomerMapping.setCustomerNetMeterReadinge(0);
				eventCustomerMapping.setCustomerNetMeterReadings(0);
				eventCustomerMapping.setEarnings(0);
				eventCustomerMapping.setCounterBidFlag("N");
				eventCustomerMapping.setBidPrice(event.getExpectedPrice());
				eventCustomerDto.setUserId(listOfusers.get(j).getAllUser().getUserId());
				eventCustomerDto.setUserName(listOfusers.get(j).getAllUser().getFullName());
				eventCustomerDto.setEventId(event.getEventId());
				eventCustomerDto.setIsSelected("Y");
				eventCustomerMapping.setCustomerFine(0);
				eventCustomerMapping.setIsFineApplicable("N");
				eventCustomerMapping.setEventCustomerStatusId(1);
				// eventCustomerDto.setActualPower(allevent.getActualPower());
				// eventCustomerDto.setParticipationStatus();
				listOfEventCustDto.add(eventCustomerDto);
				listOfEventCustMapping.add(eventCustomerMapping);
			}
			eventcustomerrepo.saveAll(listOfEventCustMapping);
			// return alleventset1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return listOfEventCustDto;

	}

	public List<AllEventDto> createFile(String filePath, byte[] imageByte, Object eventSet) throws IOException {

		FileInputStream file = null;
		FileOutputStream fo = null;
		List<AllEventDto> listOfEvents = new ArrayList<AllEventDto>();
		//new File(filePath).mkdirs();
		try {
			fo = new FileOutputStream(filePath);
			fo.write(imageByte);
			file = new FileInputStream(new File(filePath));
			listOfEvents = createEventSetObjects(file, eventSet);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			file.close();
			fo.close();
		}
		return listOfEvents;
	}

	public List<AllEventDto> createEventSetObjects(FileInputStream file, Object alleventset1) throws IOException {

		List<AllEventDto> listOfEventsDto = new ArrayList<AllEventDto>();
		List<AllEvent> listOfEvents = new ArrayList<>();
		List<HashMap<String, Object>> listOfMap = new ArrayList<>(); 
		 final long HOUR = 3600*1000; // in milli-seconds.
		 XSSFWorkbook workbook = null;
		Date now = new Date();
		HashMap<String, Object> map = null;
		try {
			workbook = new XSSFWorkbook(file);
			CommonUtility cm = new CommonUtility();
			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);
			AllEventSet alleventset = (AllEventSet) alleventset1;
			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			Row firstrow = rowIterator.next();
			int count = 0;
			int rowCount = eventrepo.getEventCount();
			ArrayList<Date> listOfDates = new ArrayList<Date>();
			EventStatusPl eventstatuspl = eventrepo.getEventStatus("Created");
			DREventTypePl eventType = eventrepo.getDrEventTypePl("Peak Shave");
			// List<AllEvent> listOfEvents = new ArrayList<AllEvent>();
			while (rowIterator.hasNext()) {
				count++;
				rowCount++;
				AllEvent allevent = new AllEvent();
				AllEventDto alleventsetdto = new AllEventDto();
				Row row = rowIterator.next();
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
//                 Cell cell1= row.getCell(0);
//                 System.out.println(cell1.getStringCellValue());
				Cell cell2 = row.getCell(0);
				System.out.println(cell2.getStringCellValue());
				Cell cell3 = row.getCell(1);
				System.out.println(cell3.getNumericCellValue());
				Cell cell4 = row.getCell(2);
				if (cell4 != null) {
					System.out.println(cell4.getNumericCellValue());
				}
				 final long ONE_MINUTE_IN_MILLIS=60000;//millisecs
				  final long HALFHOUR = 1800*1000;
			     Date d1=new Date(new Date().getTime() +5*HOUR+HALFHOUR - 5*ONE_MINUTE_IN_MILLIS);
			      
				Date afterHour = new Date(now.getTime() +5*HOUR+HALFHOUR+ 1 * HOUR);
				// Date d1=new Date(new Date().getTime() );
			      
				//Date afterHour = new Date(now.getTime() + 1 * HOUR);

				listOfDates = CommonUtility.getDateFormatted(cell2.getStringCellValue(),alleventset.getDate() );
				if(listOfDates.get(0).compareTo(afterHour) < 0) {
					continue;
				} 
				if (cell3 == null || cell3.getNumericCellValue() <=0) {
					 continue;
				}
//				if (cell3 == null || cell3.getNumericCellValue() <=0 || cell4 == null || cell4.getNumericCellValue() <=0) {
//					// continue; -- Commented for C&I Users
//				}
				alleventsetdto.setEventId(rowCount);
				int eventNameSuffix = this.eventNameSuffix + count;
				if (eventNameSuffix < 10) {
					alleventsetdto.setEventName(alleventset.getName() +"0"+ eventNameSuffix);
					allevent.setEventName(alleventset.getName() +"0"+ eventNameSuffix);
				} else {
					alleventsetdto.setEventName(alleventset.getName() + eventNameSuffix);
					allevent.setEventName(alleventset.getName() + eventNameSuffix);
				}
				alleventsetdto.setEventSetId(alleventset.getEventSetId());
				alleventsetdto.setEventStatus(eventstatuspl.getName());
				allevent.setEventId(rowCount);
				allevent.setAllEventSet(alleventset);
				allevent.setEventStatusPl(eventstatuspl);
				allevent.setPlannedPower(cell3.getNumericCellValue());
				allevent.setBuyerFine(0);
				allevent.setDsoNetMeterReadinge(0);
				allevent.setDsoNetMeterReadings(0);
				alleventsetdto.setPlannedPower(Double.toString(cell3.getNumericCellValue()));
				allevent.setEventTypeId(eventType);
				alleventsetdto.setEventTypeId(eventType.getDrEventTypeId());
				alleventsetdto.setEventTypeName(eventType.getName());
				if (cell4 != null) {
					allevent.setExpectedPrice(cell4.getNumericCellValue());
					alleventsetdto.setPrice(Double.toString(cell4.getNumericCellValue()));
				}
				
				allevent.setEventStartTime(listOfDates.get(0));
				allevent.setEventEndTime(listOfDates.get(1));
				alleventsetdto.setActualPower(null);
				alleventsetdto.setCommittedPower(null);
				alleventsetdto.setCounterBidCustomers(0);
				alleventsetdto.setInvitedCustomers(0);
				alleventsetdto.setNoResponseCustomers(0);
				alleventsetdto.setParticipatedCustomers(0);
				alleventsetdto.setEndTime(listOfDates.get(1).toString());
				alleventsetdto.setStartTime(listOfDates.get(0).toString());
				alleventsetdto.setParticipatedCustomers(0);
				alleventsetdto.setShortfall(null);
				allevent.setActualPower(0);
				allevent.setIsFineApplicable("N");
				// allevent.setCommittedPower(0);
				allevent.setCounterBidCustomers("0");
				allevent.setInvitedCustomers(0);
				allevent.setNoResponseCustomers(0);
				allevent.setParticipatedCustomers(0);
				allevent.setParticipatedCustomers(0);
				alleventsetdto.setShortfall("0");
				alleventsetdto.setIsFineApplicable("N");
				/*
				 * eventrepo.saveAndFlush(allevent); List<EventCustomerDto> listOfCustomers =
				 * createEventCustomer(allevent);
				 * alleventsetdto.setListOfCustomers(listOfCustomers);
				 * alleventsetdto.setNumberOfCustomers(Integer.toString(listOfCustomers.size()))
				 * ;
				 */
				map = new HashMap<>();
				map.put("AllEvent", allevent);
				map.put("AllEventDto", alleventsetdto);
				System.out.println("");
				listOfEvents.add(allevent);
				listOfMap.add(map);
				// listOfEventsDto.add(alleventsetdto);
			}
			eventrepo.saveAll(listOfEvents);
			for(HashMap<String, Object> e: listOfMap) {
				AllEventDto alleventdto = (AllEventDto) e.get("AllEventDto");
				List<EventCustomerDto> listOfCustomers = createEventCustomer((AllEvent) e.get("AllEvent"));
				alleventdto.setListOfCustomers(listOfCustomers);
				alleventdto.setNumberOfCustomers(Integer.toString(listOfCustomers.size()));
				listOfEventsDto.add(alleventdto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			file.close();
			workbook.close();
		}
		return listOfEventsDto;
	}

	public HashMap<String, Object> loginUser(String phoneNumber, String password) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		try {

			int count = eventsetrepo.loginUser(phoneNumber, password);
			if (count > 0) {
				response.put("status", "LOGGED_IN");
			} else {
				response.put("status", "WRONG_CREDENTIALS");
			}

		} catch (Exception e) {

		} finally {

		}
		return response;
	}

	public HashMap<String, Object> getEventDetails(List<Integer> eventId) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			int participationCount=0, noResponseCount=0,counterBid=0, notifiedCount=0;
			List<AllEventDto> listOfEventsdto = new ArrayList<AllEventDto>();
			for (int i = 0; i < eventId.size(); i++) {
				participationCount=0;  noResponseCount=0; counterBid=0; notifiedCount=0;
				AllEvent allevent = eventrepo.getEventById(eventId.get(i));
				AllEventDto alleventdto = new AllEventDto();
				alleventdto.setEventId(allevent.getEventId());
				alleventdto.setEventName(allevent.getEventName());
				alleventdto.setEventSetId(allevent.getAllEventSet().getEventSetId());
				alleventdto.setEventStatus(allevent.getEventStatusPl().getName());
				alleventdto.setPower(allevent.getActualPower());
				alleventdto.setPlannedPower(Double.toString(allevent.getPlannedPower()));
				alleventdto.setPrice(Double.toString(allevent.getExpectedPrice()));
				alleventdto.setNumberOfCustomers(Integer.toString(allevent.getEventCustomerMappings().size()));
				alleventdto.setStartTime(allevent.getEventStartTime().toString());
				alleventdto.setEndTime(allevent.getEventEndTime().toString());
				alleventdto.setCommittedPower(Double.toString(allevent.getCommitedPower()));
				alleventdto.setEventTypeId(allevent.getEventTypeId().getDrEventTypeId());
				alleventdto.setEventTypeName(allevent.getEventTypeId().getName());
				if (allevent.getEventStatusPl().getEventStatusId() == 9) {
				alleventdto.setShortfall(Double.toString(allevent.getCommitedPower() - allevent.getActualPower()));
				} else {
					alleventdto.setShortfall("0");
				}
				alleventdto.setActualPower(Double.toString(allevent.getActualPower()));
				alleventdto.setStartTime(allevent.getEventStartTime().toString());
				List<EventCustomerDto> listOfCustomers = new ArrayList<EventCustomerDto>();

				for (int j = 0; j < allevent.getEventCustomerMappings().size(); j++) {
					EventCustomerDto evdto = new EventCustomerDto();
					evdto.setEventId(allevent.getEventId());
					evdto.setUserId(allevent.getEventCustomerMappings().get(j).getAllUser().getUserId());
					evdto.setUserName(allevent.getEventCustomerMappings().get(j).getAllUser().getFullName());
					evdto.setActualPower(allevent.getActualPower());
					evdto.setCommitments(allevent.getCommitedPower());
					evdto.setPrice(allevent.getEventCustomerMappings().get(j).getBidPrice());
					evdto.setStatus(allevent.getEventCustomerMappings().get(j).getEventCustomerStatusId());
					evdto.setIsSelected("Y");
					// eventCustomerDto.setActualPower(allevent.getActualPower());
					
					 if (allevent.getEventCustomerMappings().get(j).getEventCustomerStatusId() != 1 && allevent.getEventCustomerMappings().get(j).getEventCustomerStatusId() !=9) {
						
						notifiedCount++;
						noResponseCount++;
					} 
					if (allevent.getEventCustomerMappings().get(j).getEventCustomerStatusId() == 3 || allevent.getEventCustomerMappings().get(j).getEventCustomerStatusId() == 5 || allevent.getEventCustomerMappings().get(j).getEventCustomerStatusId() == 8 || allevent.getEventCustomerMappings().get(j).getEventCustomerStatusId() == 10  || allevent.getEventCustomerMappings().get(j).getEventCustomerStatusId()==15 || allevent.getEventCustomerMappings().get(j).getEventCustomerStatusId()==11 || allevent.getEventCustomerMappings().get(j).getEventCustomerStatusId()==12 || allevent.getEventCustomerMappings().get(j).getEventCustomerStatusId() == 13) {
						evdto.setParticipationStatus("1");	
						participationCount++;
						noResponseCount--;
					}

					 if (allevent.getEventCustomerMappings().get(j).getEventCustomerStatusId() == 4) {
						
						counterBid++;
						noResponseCount--;
					}
					
					
					
//					else if (allevent.getEventCustomerMappings().get(j).getEventCustomerStatusId() == 1) {
//						
//						noResponseCount++;
//					}
					
					if (allevent.getEventCustomerMappings().get(j).getIsFineApplicable() != null) {
						evdto.setIsFineApplicable(allevent.getEventCustomerMappings().get(j).getIsFineApplicable());
					}
					if (Double.valueOf(allevent.getEventCustomerMappings().get(j).getCustomerFine()) != null) {
						evdto.setCustomerFine(allevent.getEventCustomerMappings().get(j).getCustomerFine());
						evdto.setEarnings((allevent.getEventCustomerMappings().get(j).getCommitedPower()*allevent.getEventCustomerMappings().get(j).getBidPrice())- allevent.getEventCustomerMappings().get(j).getCustomerFine());
					}
					if(evdto.getParticipationStatus() == null) {
						evdto.setParticipationStatus("0");
					}
					listOfCustomers.add(evdto);
				}
				alleventdto.setParticipatedCustomers(participationCount);
				alleventdto.setListOfCustomers(listOfCustomers);
				alleventdto.setInvitedCustomers(notifiedCount);
				alleventdto.setCounterBidCustomers(counterBid);
				alleventdto.setNoResponseCustomers(noResponseCount);
				
				listOfEventsdto.add(alleventdto);
			}
			response.put("responseStatus", "1");
			response.put("responseMessage", "The request was successfully served.");
			response.put("response", listOfEventsdto);
			response.put("customMessage", null);
			// createEventSetObjects(file);
			// Create Workbook instance holding reference to .xlsx file

		} catch (Exception e) {
			e.printStackTrace();
			response.put("responseStatus", "2");
			response.put("responseMessage", "Internal Server Error.");
			response.put("customMessage", null);
			response.put("response", null);
		} finally {

		}
		return response;
	}

	public HashMap<String, Object> getEventSetDetails(int eventSetId) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		HashMap<String, Object> internalresponse = new HashMap<String, Object>();
		List<AllEventDto> listOfEvents = new ArrayList<AllEventDto>();
		int participationCount=0, noResponseCount=0,counterBid=0, notifiedCount=0;
		try {
			int count=0;
			AllEventSet alleventset = eventsetrepo.getEventSet(eventSetId);
			ArrayList<String> status = new ArrayList<String>();
			status = CompareHelper.countdata(alleventset.getAllEvents());
			AllEventSetDto alleventsetdto = new AllEventSetDto();
			alleventsetdto.setEventSetId(alleventset.getEventSetId());
			alleventsetdto.setEventSetName(alleventset.getName());
			alleventsetdto.setEventSetStatus(alleventset.getEventSetStatusPl().getStatusName());
			alleventsetdto.setUserId(alleventset.getAllUser().getUserId());
			alleventsetdto.setUserName(alleventset.getAllUser().getFullName());
			alleventsetdto.setDateOfOccurence(alleventset.getDate().toString());
			alleventsetdto.setTotalPrice(Double.toString(alleventset.getTotalPrice()));
			alleventsetdto.setPlannedPower(Double.toString(alleventset.getPlannedPower()));
			alleventsetdto.setActualPower(Double.toString(alleventset.getActualPower()));
			if (status.size() > 0) {
				alleventsetdto.setPublishedEvents(status.get(0));
				alleventsetdto.setCompletedEvents(status.get(1));
				alleventsetdto.setCancelledEvents(status.get(2));
			}
			List<AllEvent> events = eventrepo.getEvent(eventSetId);
			for (int i = 0; i < events.size(); i++) {
				count = 0;
				participationCount=0;noResponseCount=0;counterBid=0;notifiedCount=0;
				AllEventDto alleventdto = new AllEventDto();
				alleventdto.setEventId(events.get(i).getEventId());
				alleventdto.setEventName(events.get(i).getEventName());
				alleventdto.setEventStatus(events.get(i).getEventStatusPl().getName());
				alleventdto.setPower(events.get(i).getActualPower());
				alleventdto.setEventTypeId(events.get(i).getEventTypeId().getDrEventTypeId());
				alleventdto.setEventTypeName(events.get(i).getEventTypeId().getName());
				
				alleventdto.setPlannedPower(
						Double.toString(events.get(i).getPlannedPower()));
				alleventdto.setPrice(
						Double.toString(events.get(i).getExpectedPrice()));
				alleventdto.setNumberOfCustomers(Integer.toString(
						events.get(i).getEventCustomerMappings().size()));
				alleventdto.setStartTime(
						events.get(i).getEventStartTime().toString());
				alleventdto.setEndTime(
						events.get(i).getEventEndTime().toString());
				alleventdto.setCommittedPower(
						Double.toString(events.get(i).getCommitedPower()));
				if (events.get(i).getEventStatusPl().getEventStatusId() == 9) {
				alleventdto.setShortfall(Double.toString(events.get(i).getCommitedPower() - alleventset.getAllEvents().get(i).getActualPower()));
				} else {
					alleventdto.setShortfall("0");
				}
				alleventdto.setActualPower(
						Double.toString(events.get(i).getActualPower()));
				alleventdto.setStartTime(
						events.get(i).getEventStartTime().toString());
				if (events.get(i).getIsFineApplicable() != null) {
					alleventdto.setIsFineApplicable(
							events.get(i).getIsFineApplicable());
				}
				if (Double.valueOf(events.get(i).getBuyerFine()) != null) {
					alleventdto.setBuyerFine(events.get(i).getBuyerFine());
				}
				List<EventCustomerMapping> listOfCustomers = eventcustomerrepo
						.getEventCustomerMappings(events.get(i).getEventId());
				List<EventCustomerDto> listOfCustomersdto = new ArrayList<EventCustomerDto>();
				for (int k = 0; k < listOfCustomers.size(); k++) {
					EventCustomerDto eventcustomerdto = new EventCustomerDto();
					eventcustomerdto.setUserId(listOfCustomers.get(k).getAllUser().getUserId());
					eventcustomerdto.setUserName(listOfCustomers.get(k).getAllUser().getFullName());
					eventcustomerdto.setActualPower(listOfCustomers.get(k).getActualPower());
					eventcustomerdto.setCommitments(listOfCustomers.get(k).getCommitedPower());
					eventcustomerdto.setPrice(listOfCustomers.get(k).getBidPrice());
					eventcustomerdto.setIsSelected("Y");
					// eventCustomerDto.setActualPower(allevent.getActualPower()); 
					if (listOfCustomers.get(k).getEventCustomerStatusId() == 3 || listOfCustomers.get(k).getEventCustomerStatusId() == 5 || listOfCustomers.get(k).getEventCustomerStatusId() == 8 || listOfCustomers.get(k).getEventCustomerStatusId() == 10 || listOfCustomers.get(k).getEventCustomerStatusId() == 15 || listOfCustomers.get(k).getEventCustomerStatusId() == 11 || listOfCustomers.get(k).getEventCustomerStatusId() == 12 || listOfCustomers.get(k).getEventCustomerStatusId() == 13) {
						eventcustomerdto.setParticipationStatus("1");	
						participationCount++;
					}

					else if (listOfCustomers.get(k).getEventCustomerStatusId() == 4) {
						
						counterBid++;
					}
					
					else if (listOfCustomers.get(k).getEventCustomerStatusId() != 1 && listOfCustomers.get(k).getEventCustomerStatusId() !=9) {
						
						notifiedCount++;
						noResponseCount++;
					} 
					
					else if (listOfCustomers.get(k).getEventCustomerStatusId() == 1) {
						
						noResponseCount++;
					}
					if (listOfCustomers.get(k).getIsFineApplicable() != null) {
						eventcustomerdto.setIsFineApplicable(listOfCustomers.get(k).getIsFineApplicable());
					}
					if (Double.valueOf(listOfCustomers.get(k).getCustomerFine()) != null) {
						eventcustomerdto.setCustomerFine(listOfCustomers.get(k).getCustomerFine());
						eventcustomerdto.setEarnings((listOfCustomers.get(k).getCommitedPower()*listOfCustomers.get(k).getBidPrice())-listOfCustomers.get(k).getCustomerFine() );
					}
					if(eventcustomerdto.getParticipationStatus() == null) {
						eventcustomerdto.setParticipationStatus("0");
					}
					
					listOfCustomersdto.add(eventcustomerdto);

				}
				alleventdto.setParticipatedCustomers(count);
				alleventdto.setListOfCustomers(listOfCustomersdto);
				alleventdto.setParticipatedCustomers(participationCount);
				alleventdto.setNoResponseCustomers(noResponseCount);
				alleventdto.setCounterBidCustomers(counterBid);
				alleventdto.setInvitedCustomers(notifiedCount);
				listOfEvents.add(alleventdto);

			}

			internalresponse.put("eventSetDetails", alleventsetdto);
			internalresponse.put("events", listOfEvents);
			response.put("responseStatus", "1");
			response.put("responseMessage", "The request was successfully served.");
			response.put("response", internalresponse);
			response.put("customMessage", null);

		} catch (Exception e) {
			response.put("responseStatus", "2");
			response.put("responseMessage", "Internal Server Error");
			response.put("response", null);
			response.put("customMessage", null);
		} finally {

		}
		return response;
	}

//	public HashMap<String, Object> loginDSOUser(String email, String password) throws ParseException {
//		
//		HashMap<String, Object> response = new HashMap<String, Object>();
//		HashMap<String, Object> internalresponse = new HashMap<String, Object>();
//		CompareHelper ch = new CompareHelper();
//		try {
//			int count = this.alluserrepo.loginDSOUser(email, password);
//			ArrayList<String> status = new ArrayList<String>();
//			if (count >= 1) {
//				AllUser alluser = alluserrepo.getUserBymail(email);
//				internalresponse.put("userId", alluser.getUserId());
//				response.put("responseStatus", "1");
//				response.put("responseMessage", "The request was successfully served.");
//				response.put("response", internalresponse);
//				response.put("customMessage", CustomMessages.getCustomMessages("SL"));
//
//			} else if (count < 1) {
//				response.put("responseStatus", "1");
//				response.put("responseMessage", "The request was successfully served.");
//				response.put("customMessage", CustomMessages.getCustomMessages("WL"));
//				response.put("response", internalresponse);
//			}
//
//		} catch (Exception e) {
//			System.out.println("Error in checkExistence" + e.getMessage());
//			e.printStackTrace();
//			response.put("responseStatus", "2");
//			response.put("responseMessage", "Internal Server Error");
//			response.put("response", null);
//			response.put("customMessage", null);
//
//		}
//		return response;
//	}

public HashMap<String, Object> loginDSOUser(String email, String password) throws ParseException {
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		HashMap<String, Object> internalresponse = new HashMap<String, Object>();
		CompareHelper ch = new CompareHelper();
		try {
			AllDso dsoUser = this.alldsoRepo.loginDSOUser(email, password);
			if (dsoUser !=null) {
				AllUser alluser = alluserrepo.getUserBymail(email);
				internalresponse.put("userId", dsoUser.getDsoId());
				response.put("responseStatus", "1");
				response.put("responseMessage", "The request was successfully served.");
				response.put("response", internalresponse);
				response.put("customMessage", CustomMessages.getCustomMessages("SL"));

			} else  {
				response.put("responseStatus", "1");
				response.put("responseMessage", "The request was successfully served.");
				response.put("customMessage", CustomMessages.getCustomMessages("WL"));
				response.put("response", internalresponse);
			}

		} catch (Exception e) {
			System.out.println("Error in checkExistence" + e.getMessage());
			e.printStackTrace();
			response.put("responseStatus", "2");
			response.put("responseMessage", "Internal Server Error");
			response.put("response", null);
			response.put("customMessage", null);

		}
		return response;
	}

	public HashMap<String, Object> updateEvent(List<HashMap<String,Integer>> events, int eventSetId) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		try {

			for (int i = 0; i < events.size(); i++) {
				HashMap<String,Integer> event = events.get(i);
				eventrepo.updateEvent(2, event.get("id"),event.get("type"));
				eventcustomerrepo.updateEventCustomer(2, event.get("id"));
			}
			int eventCount = eventrepo.getEventByStatusId(events.get(0).get("id"));
			if (eventCount > 0) {
				eventsetrepo.updateEvent(3, eventSetId);
			} else {
				eventsetrepo.updateEvent(2, eventSetId);
			}
			response.put("responseStatus", "1");
			response.put("responseMessage", "The request was successfully served.");
			response.put("response", null);
			response.put("customMessage", null);

		}

		catch (Exception e) {
			System.out.println("Error in checkExistence" + e.getMessage());
			e.printStackTrace();
			response.put("responseStatus", "2");
			response.put("responseMessage", "Internal Server Error.");
			response.put("response", null);
			response.put("customMessage", null);

		}
		return response;
	}

	public HashMap<String, Object> cancelEvent(int event) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		try {

			eventrepo.updateEvent(4, event);
			eventcustomerrepo.updateEventCustomer(9, event);
			// eventcustomerrepo.updateEventCustomer(2, events.get(i));

			response.put("responseStatus", "1");
			response.put("responseMessage", "The request was successfully served.");
			response.put("response", null);
			response.put("customMessage", null);

		}

		catch (Exception e) {
			System.out.println("Error in checkExistence" + e.getMessage());
			e.printStackTrace();
			response.put("responseStatus", "2");
			response.put("responseMessage", "Internal Server Error.");
			response.put("response", null);
			response.put("customMessage", null);

		}
		return response;
	}

	public HashMap<String, Object> updateCustomer(List<Integer> customer, List<Integer> events) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		List<EventCustomerMapping> listOfEventCustMapping = new ArrayList<EventCustomerMapping>();
		try {
			for (int i = 0; i < events.size(); i++) {
				eventcustomerrepo.removeCustomers(events.get(i));
				int count = eventcustomerrepo.getEventCustomerCount();
				AllEvent allevent = eventrepo.getEventById(events.get(i));
				for (int j = 0; j < customer.size(); j++) {
					count++;
					AllUser alluser = alluserrepo.getUserById(customer.get(j));
					// EventCustomerDto eventCustomerDto = new EventCustomerDto();
					EventCustomerMapping eventCustomerMapping = new EventCustomerMapping();
					eventCustomerMapping.setAllUser(alluser);
					eventCustomerMapping.setEventCustomerMappingId(count);
					eventCustomerMapping.setAllEvent(allevent);
					listOfEventCustMapping.add(eventCustomerMapping);
				}
			}
			eventcustomerrepo.saveAll(listOfEventCustMapping);
			response.put("responseStatus", "1");
			response.put("responseMessage", "The request was successfully served.");
			response.put("response", null);
			response.put("customMessage", null);

		}

		catch (Exception e) {
			System.out.println("Error in checkExistence" + e.getMessage());
			e.printStackTrace();
			response.put("responseStatus", "2");
			response.put("responseMessage", "Internal Server Error.");
			response.put("response", null);
			response.put("customMessage", null);

		}
		return response;
	}

	public HashMap<String, Object> getCustomerForEvents(List<Integer> events) {

		List<EventCustomerDto> listOfEventCustDto = new ArrayList<EventCustomerDto>();
		HashMap<String, Object> response = new HashMap<String, Object>();
		HashMap<String, Object> internalResponse = new HashMap<String, Object>();
		try {

			if (events.size() > 1) {
				List<UserAccessLevelMapping> listOfCustomers = eventcustomerrepo.getUserAccessLevel();
				for (int i = 0; i < events.size(); i++) {
					for (int j = 0; j < listOfCustomers.size(); j++) {
						AllEvent allevent = eventrepo.getEventById(events.get(i));
						EventCustomerDto eventCustomerDto = new EventCustomerDto();
						eventCustomerDto.setUserId(listOfCustomers.get(j).getAllUser().getUserId());
						eventCustomerDto.setUserName(listOfCustomers.get(j).getAllUser().getFullName());
						eventCustomerDto.setIsSelected("N");
						eventCustomerDto.setActualPower(allevent.getActualPower());
						// eventCustomerDto.setParticipationStatus("1"); // hardcoded
						eventCustomerDto.setCommitments(allevent.getCommitedPower());
						eventCustomerDto.setPrice(allevent.getExpectedPrice());

						listOfEventCustDto.add(eventCustomerDto);
					}
				}
			} else {
				AllEvent allevent = eventrepo.getEventById(events.get(0));
				List<UserAccessLevelMapping> listOfCustomers = eventcustomerrepo
						.getUserAccessLevel(allevent.getAllEventSet().getAllUser().getUserId(),allevent.getAllEventSet().getDivison());
				List<EventCustomerMapping> listOfEventCustomers = eventcustomerrepo
						.getEventCustomerMappings(events.get(0));
				for (int j = 0; j < listOfCustomers.size(); j++) {
					EventCustomerDto eventCustomerDto = new EventCustomerDto();
					if (listOfEventCustomers.size() > 0) {

						if (CompareHelper.compareData(listOfEventCustomers,
								listOfCustomers.get(j).getAllUser().getUserId()) != null) {
							EventCustomerMapping evtmap = CompareHelper.compareData(listOfEventCustomers,
									listOfCustomers.get(j).getAllUser().getUserId());
							eventCustomerDto.setIsSelected("Y");
//        			} else {
//        				eventCustomerDto.setIsSelected("N");
//        			}

							eventCustomerDto.setUserId(listOfCustomers.get(j).getAllUser().getUserId());
							eventCustomerDto.setUserName(listOfCustomers.get(j).getAllUser().getFullName());
							eventCustomerDto.setActualPower(evtmap.getActualPower());
							if (evtmap.getEventCustomerStatusId() >= 3) {
								eventCustomerDto.setParticipationStatus("1"); // hardcoded
							} else {
								eventCustomerDto.setParticipationStatus("0"); // hardcoded
							}
							if (evtmap.getCounterBidFlag() != null) {
								eventCustomerDto.setCounterBidFlag(evtmap.getCounterBidFlag()); // hardcoded
							}
							eventCustomerDto.setCouterBidAmount(evtmap.getCounterBidAmount()); // hardcoded
							eventCustomerDto.setCommitments(evtmap.getCommitedPower());
							eventCustomerDto.setPrice(evtmap.getBidPrice());
							eventCustomerDto.setStatus(evtmap.getEventCustomerStatusId());
							eventCustomerDto.setIsFineApplicable(evtmap.getIsFineApplicable());
							eventCustomerDto.setEarnings(evtmap.getEarnings());
							eventCustomerDto.setCustomerFine(evtmap.getCustomerFine());
							// eventCustomerDto.setEventId(event.getEventId());
							listOfEventCustDto.add(eventCustomerDto);
						}
					}
				}
			}

			internalResponse.put("customers", listOfEventCustDto);
			response.put("responseStatus", "1");
			response.put("responseMessage", "The request was successfully served.");
			response.put("response", internalResponse);
			response.put("customMessage", null);

		}

		catch (Exception e) {
			System.out.println("Error in checkExistence" + e.getMessage());
			e.printStackTrace();
			response.put("responseStatus", "2");
			response.put("responseMessage", "Internal Server Error.");
			response.put("response", null);
			response.put("customMessage", null);

		}
		return response;
	}

	public ArrayList<String> getPower(List<AllEventDto> listOfEvents) {
		ArrayList<String> response = new ArrayList<String>();
		double power = 0;
		double price = 0;
		try {
			for (int i = 0; i < listOfEvents.size(); i++) {
				power = power + Double.parseDouble(listOfEvents.get(i).getPlannedPower());
				price = price + Double.parseDouble(listOfEvents.get(i).getPrice());
			}
			response.add(Double.toString(power));
			response.add(Double.toString(price));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public HashMap<String, Object> rejectCustomer(int eventId, int customerId) {

		HashMap<String, Object> response = new HashMap<String, Object>();

		try {
			EventCustomerMapping eventcustomermapping = eventcustomerrepo.getEventCustomerById(eventId, customerId);
			eventcustomerrepo.updateEventCustomerbyId(6, eventId, customerId);
			eventrepo.removeEventPower(eventcustomermapping.getCommitedPower(), eventId);
			response.put("responseStatus", "1");
			response.put("responseMessage", "The request was successfully served.");
			response.put("response", null);
			response.put("customMessage", null);

		}

		catch (Exception e) {
			System.out.println("Error in checkExistence" + e.getMessage());
			e.printStackTrace();
			response.put("responseStatus", "2");
			response.put("responseMessage", "Internal Server Error.");
			response.put("response", null);
			response.put("customMessage", null);

		}
		return response;
	}
	
	public HashMap<String, Object> rejectCounterBid(int eventId, int customerId) {

		HashMap<String, Object> response = new HashMap<String, Object>();

		try {
			EventCustomerMapping eventcustomermapping = eventcustomerrepo.getEventCustomerById(eventId, customerId);
			eventcustomerrepo.updateEventCustomerbyId(6, eventId, customerId);
		//	eventrepo.removeEventPower(eventcustomermapping.getCommitedPower(), eventId);
			response.put("responseStatus", "1");
			response.put("responseMessage", "The request was successfully served.");
			response.put("response", null);
			response.put("customMessage", null);

		}

		catch (Exception e) {
			System.out.println("Error in checkExistence" + e.getMessage());
			e.printStackTrace();
			response.put("responseStatus", "2");
			response.put("responseMessage", "Internal Server Error.");
			response.put("response", null);
			response.put("customMessage", null);

		}
		return response;
	}

	public HashMap<String, Object> acceptCounterBid(int eventId, int customerId) {

		HashMap<String, Object> response = new HashMap<String, Object>();

		try {
			EventCustomerMapping eventcustomermapping = eventcustomerrepo.getEventCustomerById(eventId, customerId);
			eventcustomerrepo.acceptCounterBid("Y", 5, eventId, customerId);
			eventrepo.addEventPower(eventcustomermapping.getCommitedPower(), eventId);
			response.put("responseStatus", "1");
			response.put("responseMessage", "The request was successfully served.");
			response.put("response", null);
			response.put("customMessage", null);

		}

		catch (Exception e) {
			System.out.println("Error in checkExistence" + e.getMessage());
			e.printStackTrace();
			response.put("responseStatus", "2");
			response.put("responseMessage", "Internal Server Error.");
			response.put("response", null);
			response.put("customMessage", null);

		}
		return response;
	}
	
	public HashMap<String, Object> getEventSetsByUser(int userId) throws ParseException {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -7);
		Date weekdate = cal.getTime();
		cal.setTime(date);
		cal.add(Calendar.DATE, -30);
		Date monthdate = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String weekdates = sdf.format(weekdate);
		String monthdates = sdf.format(monthdate);
		String dates = sdf.format(date);
		weekdate = sdf.parse(weekdates);
		monthdate = sdf.parse(monthdates);
		date = sdf.parse(dates);
		HashMap<String, Object> response = new HashMap<String, Object>();
		HashMap<String, Object> internalresponse = new HashMap<String, Object>();
		ArrayList<String> status = new ArrayList<>();
		ArrayList<Integer> weeklyEvents = new ArrayList<>();
		ArrayList<Integer> monthlyEvents = new ArrayList<>();
		ArrayList<Integer> upcomingEvents = new ArrayList<>();
		try {
				List<AllEventSet> listOfWeeklyEvents = eventrepo.getEventSetBydate(weekdate, date, userId);
				List<AllEventSet> listOfMonthlyEvents = eventrepo.getEventSetBydate(monthdate, date, userId);
				List<AllEventSet> listOfUpcomingEvents = eventrepo.getupcomingEventSet(date, userId);
				List<AllEventSetDto> listOfWeeklyEventSetDto = new ArrayList<AllEventSetDto>();
				List<AllEventSetDto> listOfMonthlyEventSetDto = new ArrayList<AllEventSetDto>();
				List<AllEventSetDto> listOfUpcomingEventSetDto = new ArrayList<AllEventSetDto>();

				for (int i = 0; i < listOfWeeklyEvents.size(); i++) {
					 status = CompareHelper.countdata(listOfWeeklyEvents.get(i).getAllEvents());
					AllEventSetDto alleventsetdto = new AllEventSetDto();
					alleventsetdto.setEventSetName(listOfWeeklyEvents.get(i).getName());
					alleventsetdto.setEventSetId(listOfWeeklyEvents.get(i).getEventSetId());
					alleventsetdto.setEventSetStatus(listOfWeeklyEvents.get(i).getEventSetStatusPl().getStatusName());
					alleventsetdto.setUserId(listOfWeeklyEvents.get(i).getAllUser().getUserId());
					alleventsetdto.setUserName(listOfWeeklyEvents.get(i).getAllUser().getFullName());
					alleventsetdto.setActualPower(Double.toString(listOfWeeklyEvents.get(i).getActualPower()));
					alleventsetdto.setCommittedPower(Double.toString(listOfWeeklyEvents.get(i).getCommitedPower()));
					if (status.size() > 0) {
						alleventsetdto.setPublishedEvents(status.get(0));
						alleventsetdto.setCompletedEvents(status.get(1));
						alleventsetdto.setCancelledEvents(status.get(2));
					}
					alleventsetdto.setCreatedTs(listOfWeeklyEvents.get(i).getCreatedTs());
					alleventsetdto.setDateOfOccurence(listOfWeeklyEvents.get(i).getDate().toString());
					alleventsetdto.setTotalPrice(Double.toString(listOfWeeklyEvents.get(i).getTotalPrice()));
					alleventsetdto.setPlannedPower(Double.toString(listOfWeeklyEvents.get(i).getPlannedPower()));
					weeklyEvents.add(alleventsetdto.getEventSetId());
					listOfWeeklyEventSetDto.add(alleventsetdto);
				}

				for (int i = 0; i < listOfMonthlyEvents.size(); i++) {
					 status = CompareHelper.countdata(listOfMonthlyEvents.get(i).getAllEvents());
					if (weeklyEvents.contains(listOfMonthlyEvents.get(i).getEventSetId())) {
						continue;
					}
					AllEventSetDto alleventsetdto = new AllEventSetDto();
					alleventsetdto.setEventSetName(listOfMonthlyEvents.get(i).getName());
					alleventsetdto.setEventSetId(listOfMonthlyEvents.get(i).getEventSetId());
					alleventsetdto.setEventSetStatus(listOfMonthlyEvents.get(i).getEventSetStatusPl().getStatusName());
					alleventsetdto.setUserId(listOfMonthlyEvents.get(i).getAllUser().getUserId());
					alleventsetdto.setUserName(listOfMonthlyEvents.get(i).getAllUser().getFullName());
					alleventsetdto.setActualPower(Double.toString(listOfMonthlyEvents.get(i).getActualPower()));
					alleventsetdto.setCommittedPower(Double.toString(listOfMonthlyEvents.get(i).getCommitedPower()));
					if (status.size() > 0) {
						alleventsetdto.setPublishedEvents(status.get(0));
						alleventsetdto.setCompletedEvents(status.get(1));
						alleventsetdto.setCancelledEvents(status.get(2));
					}
					alleventsetdto.setCreatedTs(listOfMonthlyEvents.get(i).getCreatedTs());
					alleventsetdto.setDateOfOccurence(listOfMonthlyEvents.get(i).getDate().toString());
					alleventsetdto.setTotalPrice(Double.toString(listOfMonthlyEvents.get(i).getTotalPrice()));
					alleventsetdto.setPlannedPower(Double.toString(listOfMonthlyEvents.get(i).getPlannedPower()));
					
					listOfMonthlyEventSetDto.add(alleventsetdto);
				}

				for (int i = 0; i < listOfUpcomingEvents.size(); i++) {
					 status = CompareHelper.countdata(listOfUpcomingEvents.get(i).getAllEvents());
					if (weeklyEvents.contains(listOfUpcomingEvents.get(i).getEventSetId()) || monthlyEvents.contains(listOfUpcomingEvents.get(i).getEventSetId())) {
						continue;
					}
					AllEventSetDto alleventsetdto = new AllEventSetDto();
					alleventsetdto.setEventSetName(listOfUpcomingEvents.get(i).getName());
					alleventsetdto.setEventSetId(listOfUpcomingEvents.get(i).getEventSetId());
					alleventsetdto.setEventSetStatus(listOfUpcomingEvents.get(i).getEventSetStatusPl().getStatusName());
					alleventsetdto.setUserId(listOfUpcomingEvents.get(i).getAllUser().getUserId());
					alleventsetdto.setUserName(listOfUpcomingEvents.get(i).getAllUser().getFullName());
					alleventsetdto.setActualPower(Double.toString(listOfUpcomingEvents.get(i).getActualPower()));
					alleventsetdto.setCommittedPower(Double.toString(listOfUpcomingEvents.get(i).getCommitedPower()));
					if (status.size() > 0) {
						alleventsetdto.setPublishedEvents(status.get(0));
						alleventsetdto.setCompletedEvents(status.get(1));
						alleventsetdto.setCancelledEvents(status.get(2));
					}
					alleventsetdto.setCreatedTs(listOfUpcomingEvents.get(i).getCreatedTs());
					alleventsetdto.setDateOfOccurence(listOfUpcomingEvents.get(i).getDate().toString());
					alleventsetdto.setTotalPrice(Double.toString(listOfUpcomingEvents.get(i).getTotalPrice()));
					alleventsetdto.setPlannedPower(Double.toString(listOfUpcomingEvents.get(i).getPlannedPower()));
					
				listOfUpcomingEventSetDto.add(alleventsetdto);
				}
				internalresponse.put("currWeek", listOfWeeklyEventSetDto);
				internalresponse.put("currMonth", listOfMonthlyEventSetDto);
				internalresponse.put("upcoming", listOfUpcomingEventSetDto);
				HashMap<String,Object> eventSetdata = new HashMap<String, Object>();
				eventSetdata.put("eventSets", internalresponse);
				response.put("responseStatus", "1");
				response.put("responseMessage", "The request was successfully served.");
				response.put("eventSets", internalresponse);
				response.put("customMessage", CustomMessages.getCustomMessages("SL"));

			}

		 catch (Exception e) {
			System.out.println("Error in checkExistence" + e.getMessage());
			e.printStackTrace();
			response.put("responseStatus", "2");
			response.put("responseMessage", "Internal Server Error");
			response.put("response", null);
			response.put("customMessage", null);

		}
		return response;
	}
	
	public HashMap<String, Object> getVersionHistory(int eventSetId) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		HashMap<String, Object> internalresponse = new HashMap<String, Object>();
		List<HashMap<String, String>> eventSetVersions = new ArrayList<>();
		try {
			int activeVersion = eventsetrepo.getActiveVersion(eventSetId);
			List<EventSetVersionHistory> evetSetVersionObjs = eventsetVersionHistRepo.getByEventSeId(eventSetId);
			Iterator<EventSetVersionHistory> it = evetSetVersionObjs.iterator();
			HashMap<String, String> temp;
			while(it.hasNext()) {
				EventSetVersionHistory obj = it.next();
				temp = new HashMap<>();
				String currentVersionFlag = "false";
				if(obj.getVersion() == activeVersion)
					currentVersionFlag = "true";
				temp.put("id", Integer.toString(obj.getId()));
				temp.put("version", Integer.toString(obj.getVersion()));
				temp.put("uploadTime", obj.getCreatedTs().toString());
				temp.put("currentVersionFlag", currentVersionFlag);
				eventSetVersions.add(temp);
			}
			internalresponse.put("eventSetVersions", eventSetVersions);
			response.put("responseStatus", "1");
			response.put("responseMessage", "The request was successfully served.");
			response.put("eventSets", internalresponse);
			response.put("customMessage", CustomMessages.getCustomMessages("SL"));
		}
		catch (Exception e) {
			System.out.println("Error in checkExistence" + e.getMessage());
			e.printStackTrace();
			response.put("responseStatus", "2");
			response.put("responseMessage", "Internal Server Error");
			response.put("response", null);
			response.put("customMessage", null);
		}
		return response;
	}
	
	public HashMap<String, String> downloadVersion(int eventSetId, int version){
		HashMap<String, String> fileData = new HashMap<>();
		EventSetVersionHistory obj = eventsetVersionHistRepo.getByEventSeIdAndVersion(eventSetId, version);
		String fileName = obj.getAllEventSet().getName() + "-" + version + ".xlsx";
		fileData.put("fileName", fileName);
		fileData.put("fileEncodedString", obj.getUploadedFile());
		
		return fileData;
	}

	public AllDsoDto getDsoDetails(int dsoId) {
		AllDsoDto alldsodto = new AllDsoDto();
		try {
			AllDso dso=alldsoRepo.getDsoDetails(dsoId);
				if(dso != null) {
				
				if (dso.getCity() !=null) {
					alldsodto.setCity(dso.getCity());	
				} 
				if (dso.getCompanyAddress() != null) {
					alldsodto.setCompanyAddress(dso.getCompanyAddress());
				}
				if (dso.getContractNumber() != null) {
					alldsodto.setContractNumber(dso.getContractNumber());
				} 
				if (dso.getDsoLogo() != null) {
					alldsodto.setDsoLogo(dso.getDsoLogo());
				}
				if (dso.getDsoName() != null) {
					alldsodto.setDsoName(dso.getDsoName());
				}
				if (dso.getDsoProfilePicture() != null) {
					alldsodto.setDsoName(dso.getDsoName());
				}
				if (dso.getDsoProfilePicture() != null) {
					alldsodto.setDsoProfilePicture(dso.getDsoProfilePicture());
				}
				if (dso.getEmailId() != null) {
					alldsodto.setEmailId(dso.getEmailId());
				}
				if (dso.getPincode() != null) {
					alldsodto.setPincode(dso.getPincode());
				}
				if (dso.getPrimaryContactEmail() != null) {
					alldsodto.setPrimaryContactEmail(dso.getPrimaryContactEmail());
				}
				if (dso.getPrimaryContactName() != null) {
					alldsodto.setPrimaryContactName(dso.getPrimaryContactName());
				}
				if (dso.getPrimaryContactPhone() != null) {
					alldsodto.setPrimaryContactPhone(dso.getPrimaryContactPhone());
				}
				if (dso.getRegion() != null) {
					alldsodto.setRegion(dso.getRegion());
				}
				if (dso.getSecondaryContactEmail() != null) {
					alldsodto.setSecondaryContactEmail(dso.getSecondaryContactEmail());
				}
				if (dso.getSecondaryContactName() != null) {
					alldsodto.setSecondaryContactName(dso.getSecondaryContactName());
				}
				if (dso.getSecondaryContactPhone() != null) {
					alldsodto.setSecondaryContactPhone(dso.getSecondaryContactPhone());
				}
				if (dso.getState() != null) {
					alldsodto.setState(dso.getState());
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return alldsodto;
	}
	
	
	public ArrayList<GeneralConfig> getConfigValues(ArrayList<String> values) {
		ArrayList<GeneralConfig> configValue = null ;
		try {
			configValue=alluserrepo.getConfigValues(values);
		}
		catch(Exception e) {
			
		} finally {
			
		}
		return configValue;
	}
}
