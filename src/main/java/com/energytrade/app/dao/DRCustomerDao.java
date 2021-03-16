package com.energytrade.app.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.energytrade.app.model.AllUser;
import com.energytrade.app.model.DRContracts;
import com.energytrade.app.model.DevicePl;
import com.energytrade.app.model.DrDeviceTypePl;
import com.energytrade.app.model.EventCustomerMapping;
import com.energytrade.app.model.EventSetEventDto;
import com.energytrade.app.model.KiotUserMapping;
import com.energytrade.app.model.UserAccessTypeMapping;
import com.energytrade.app.model.UserDRDevices;
import com.energytrade.app.model.UserDevice;
import com.energytrade.app.model.UserRolesPl;
import com.energytrade.app.model.UserTypePl;
import com.energytrade.app.util.CompareHelper;
import com.energytrade.app.util.CustomMessages;
import com.energytrade.AppStartupRunner;
import com.energytrade.app.dto.AllEventSetDto;
import com.energytrade.app.dto.CustomerEventDetailsDto;
import com.energytrade.app.dto.CustomerEventMappingDetailsDto;
import com.energytrade.app.dto.DRDeviceDto;
import com.energytrade.app.dto.EventSetDetailsDto;
import com.energytrade.app.model.AllEvent;
import com.energytrade.app.model.AllEventSet;
import com.energytrade.app.model.AllKiotRemote;
import com.energytrade.app.model.AllKiotSwitch;

@Transactional
@Repository
public class DRCustomerDao {

	@Autowired
	DRCustomerRepository drCustomerRepo;

	@Autowired
	AllUserRepository alluserrepo;

	@Autowired
	UserAccessRepository useraccessrepo;

	@Autowired
	UserDRDevicesRepository userdrdevicerepo;

	@Autowired
	EventCustomerRepository eventCustomerRepo;

	@Autowired
	KiotUserMappingRepository kiotUserMappingRepo;

	@Autowired
	AllKiotSwitchesRepository allKiotSwithcesRepo;

	public HashMap<String, Object> getBusinessContractDetails(String contractNumber) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			response.put("contractDetails", drCustomerRepo.getBusinessContractDetails(contractNumber));
			response.put("userCount", drCustomerRepo.getUserDetailsfromContract(contractNumber));
			response.put("message", CustomMessages.getCustomMessages("SUC"));
			response.put("key", "200");
		} catch (Exception e) {
			System.out.println("Error in checkExistence" + e.getMessage());
			e.printStackTrace();
			response.put("message", CustomMessages.getCustomMessages("ISE"));
			response.put("key", "500");
		}
		return response;
	}

	public HashMap<String, Object> updateDrUserDetails(String phone, String fullName, String drContractNumber) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			drCustomerRepo.updateDrUserDetails(fullName, drContractNumber, phone);
			AllUser alluser = drCustomerRepo.getUserIdByPhone(phone);
			UserTypePl userType = alluserrepo.getUserType("DR");
			UserAccessTypeMapping ustmp = new UserAccessTypeMapping();
			ustmp.setAllUser(alluser);
			ustmp.setUserTypepl(userType);
			useraccessrepo.saveAndFlush(ustmp);

			response.put("message", CustomMessages.getCustomMessages("AS"));
			response.put("key", "200");
			response.put("userId", alluser.getUserId());
			response.put("userRole", alluser.getUserRolesPl().getUserRoleName());
			response.put("name", alluser.getFullName());
			response.put("phoneNumber", alluser.getPhoneNumber());
			List<HashMap<String, String>> listOfAccessLevels = new ArrayList<>();
			if (alluser.getUserAccessMap() != null) {
				for (int i = 0; i < alluser.getUserAccessMap().size(); i++) {
					HashMap<String, String> userAccessLevel = new HashMap<>();
					userAccessLevel.put("accessLevel",
							alluser.getUserAccessMap().get(i).getUserTypepl().getUserTypeName());
					listOfAccessLevels.add(userAccessLevel);

				}
				response.put("userTypes", listOfAccessLevels);
			}
		} catch (Exception e) {
			System.out.println("Error in checkExistence" + e.getMessage());
			e.printStackTrace();
			response.put("message", CustomMessages.getCustomMessages("ISE"));
			response.put("key", "500");

		}
		return response;
	}

	public HashMap<String, Object> getEventSetsForCustomer(int customerId) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			List<AllEventSet> customerEventSets = drCustomerRepo.getEventSetsForCustomer(customerId);
			List<EventSetDetailsDto> eventSetDetailsDtoList = new ArrayList<EventSetDetailsDto>();
			for (int i = 0; i < customerEventSets.size(); i++) {
				EventSetDetailsDto eventSetDetailsDto = new EventSetDetailsDto();
				eventSetDetailsDto.setEventSetId(customerEventSets.get(i).getEventSetId());
				eventSetDetailsDto.setCreatedTs(customerEventSets.get(i).getCreatedTs());
				eventSetDetailsDto.setEventSetStatus(customerEventSets.get(i).getEventSetStatusPl().getStatusName());
				eventSetDetailsDto.setEventSetName(customerEventSets.get(i).getName());
				eventSetDetailsDto.setEventSetDate(customerEventSets.get(i).getDate());
				List<EventSetEventDto> listOfEvents = new ArrayList<EventSetEventDto>();

//				List<AllEvent> eventsForCustomer = drCustomerRepo.getEventsForCustomerAndEventSet(customerId,
//						customerEventSets.get(i).getEventSetId());
				List<AllEvent> eventsForCustomer = customerEventSets.get(i).getAllEvents();
				for (int j = 0; j < eventsForCustomer.size(); j++) {
					EventCustomerMapping evmp = drCustomerRepo.getEventCustomerMapping(customerId,
							eventsForCustomer.get(j).getEventId());
					EventSetEventDto eventSetEvent = new EventSetEventDto();
					eventSetEvent.setEventId(eventsForCustomer.get(j).getEventId());
					eventSetEvent.setPlannedPower(String.valueOf(eventsForCustomer.get(j).getPlannedPower()));
					eventSetEvent.setPlannedPrice(String.valueOf(eventsForCustomer.get(j).getExpectedPrice()));
					eventSetEvent.setEventName(eventsForCustomer.get(j).getEventName());
					eventSetEvent.setEventStartTime(eventsForCustomer.get(j).getEventStartTime());
					eventSetEvent.setEventEndTime(eventsForCustomer.get(j).getEventEndTime());
					eventSetEvent.setEventEndTime(eventsForCustomer.get(j).getEventEndTime());
					eventSetEvent.setCommittedPower(evmp.getCommitedPower());
					eventSetEvent.setActualPower(evmp.getActualPower());
					eventSetEvent.setBidprice(evmp.getBidPrice());
					eventSetEvent.setCounterBidAmount(evmp.getCounterBidAmount());
					eventSetEvent.setCounterBidFlag(evmp.getCounterBidFlag());
					eventSetEvent.setCustomerFine(evmp.getCustomerFine());
					eventSetEvent.setEventCustomerMappingStatus(String.valueOf(evmp.getEventCustomerStatusId()));
					eventSetEvent.setEarnings(Double.toString(evmp.getEarnings()));
					eventSetEvent.setIsFineApplicable(evmp.getIsFineApplicable());
					eventSetEvent.setEventTypeId(eventsForCustomer.get(j).getEventTypeId().getDrEventTypeId());
					eventSetEvent.setEventTypeName(eventsForCustomer.get(j).getEventTypeId().getName());
					listOfEvents.add(eventSetEvent);

				}
				eventSetDetailsDto.setEvents(listOfEvents);
				eventSetDetailsDtoList.add(eventSetDetailsDto);
			}

			response.put("eventSets", eventSetDetailsDtoList);
			response.put("message", CustomMessages.getCustomMessages("SUC"));
			response.put("key", "200");
		} catch (Exception e) {
			System.out.println("Error in checkExistence" + e.getMessage());
			e.printStackTrace();
			response.put("message", CustomMessages.getCustomMessages("ISE"));
			response.put("key", "500");

		}
		return response;
	}

	public HashMap<String, Object> getEventsForCustomerAndEventSet(int customerId, int eventSetId) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			List<AllEvent> cusomterEvents = drCustomerRepo.getEventsForCustomerAndEventSet(customerId, eventSetId);
			List<CustomerEventDetailsDto> allEvents = new ArrayList<CustomerEventDetailsDto>();
			for (int i = 0; i < cusomterEvents.size(); i++) {
				CustomerEventDetailsDto customerEventDetails = new CustomerEventDetailsDto();
				customerEventDetails.setEventId(cusomterEvents.get(i).getEventId());
				customerEventDetails.setEventStatus(cusomterEvents.get(i).getEventStatusPl().getName());
				customerEventDetails.setEventName(cusomterEvents.get(i).getEventName());
				customerEventDetails.setEventStartTime(cusomterEvents.get(i).getEventStartTime());
				customerEventDetails.setEventEndTime(cusomterEvents.get(i).getEventEndTime());
				customerEventDetails.setPlannedPower(cusomterEvents.get(i).getPlannedPower());
				customerEventDetails.setCommitedPower(cusomterEvents.get(i).getCommitedPower());
				customerEventDetails.setActualPower(cusomterEvents.get(i).getActualPower());
				customerEventDetails.setExpectedPrice(cusomterEvents.get(i).getExpectedPrice());
				customerEventDetails.setCreatedTs(cusomterEvents.get(i).getCreatedTs());
				customerEventDetails.setEventTypeId(cusomterEvents.get(i).getEventTypeId().getDrEventTypeId());
				customerEventDetails.setEventTypeName(cusomterEvents.get(i).getEventTypeId().getName());
				if (cusomterEvents.get(i).getIsFineApplicable() != null) {
					customerEventDetails.setIsFineApplicable(cusomterEvents.get(i).getIsFineApplicable());
				}

				if (Double.valueOf(cusomterEvents.get(i).getBuyerFine()) != null) {
					customerEventDetails.setBuyerFine(cusomterEvents.get(i).getBuyerFine());
				}
				// customerEventDetails.setAllCustomerDevices(drCustomerRepo.getAllUserDevices(customerId));
				EventCustomerMapping ecm = drCustomerRepo.getEventCustomerMapping(customerId,
						cusomterEvents.get(i).getEventId());
				CustomerEventMappingDetailsDto cem = new CustomerEventMappingDetailsDto();
				cem.setEventCustomerMappingId(ecm.getEventCustomerMappingId());
				cem.setBidTs(ecm.getBidTs());
				cem.setBidPrice(ecm.getBidPrice());
				cem.setCommittedPower(ecm.getCommitedPower());
				cem.setActualPower(ecm.getActualPower());
				cem.setEventCustomerStatus(ecm.getEventCustomerStatusId());
				cem.setCounterBidFlag(ecm.getCounterBidFlag());
				cem.setCounterBidAmount(ecm.getCounterBidAmount());
				cem.setCreatedTs(ecm.getCreatedTs());
				if (ecm.getIsFineApplicable() != null) {
					cem.setIsFineApplicable(ecm.getIsFineApplicable());
				}

				if (Double.valueOf(ecm.getCustomerFine()) != null) {
					cem.setCustomerFine(ecm.getCustomerFine());
				}
				List<UserDRDevices> udd = drCustomerRepo.getuserMappedDevices(ecm.getEventCustomerMappingId());

				List<DRDeviceDto> mappedDevices = new ArrayList<DRDeviceDto>();
				for (int j = 0; j < udd.size(); j++) {
					DRDeviceDto mappedDevice = new DRDeviceDto();
					mappedDevice.setDeviceCapacity(udd.get(j).getDevice_capacity());
					mappedDevice.setDrDeviceId(udd.get(j).getUserDrDeviceId());
					mappedDevice.setDrDeviceName(udd.get(j).getDeviceName());
					mappedDevice.setDeviceTypeName(udd.get(j).getDeviceTypeId().getName());
					mappedDevice.setPairedDevice(udd.get(j).getPairedDevice());
					mappedDevice.setDeviceTypeId(udd.get(j).getDeviceTypeId().getDrDeviceTypeId());
					mappedDevices.add(mappedDevice);
				}
				cem.setMappedDevices(mappedDevices);
				customerEventDetails.setEventCustomerDetails(cem);
				allEvents.add(customerEventDetails);
			}
			byte status = 0;
		List<UserDRDevices> aud = drCustomerRepo.getAllUserDevices(customerId,status);
			List<DRDeviceDto> userDRdevices = new ArrayList<DRDeviceDto>();

			for (int i = 0; i < aud.size(); i++) {
				DRDeviceDto ddd = new DRDeviceDto();
				ddd.setDeviceCapacity(aud.get(i).getDevice_capacity());
				ddd.setDrDeviceId(aud.get(i).getUserDrDeviceId());
				ddd.setDrDeviceName(aud.get(i).getDeviceName());
				ddd.setPortNumber(aud.get(i).getPortNumber());
				ddd.setDeviceTypeId(aud.get(i).getDeviceTypeId().getDrDeviceTypeId());
				ddd.setDeviceTypeName(aud.get(i).getDeviceTypeId().getName());
				ddd.setPairedDevice(aud.get(i).getPairedDevice());
				userDRdevices.add(ddd);
			}
			response.put("events", allEvents);
			response.put("allCustomerDevices", userDRdevices);
			response.put("message", CustomMessages.getCustomMessages("AS"));
			response.put("key", "200");

		} catch (Exception e) {
			System.out.println("Error in checkExistence" + e.getMessage());
			e.printStackTrace();
			response.put("message", CustomMessages.getCustomMessages("ISE"));
			response.put("key", "500");

		}
		return response;
	}

	public HashMap<String, Object> getDRCustomerProfile(int customerId) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			AllUser alluser = alluserrepo.getUserById(customerId);
			HashMap<String, Object> userDetails = new HashMap<>();
			response.put("fullName", alluser.getFullName());
			response.put("phoneNumber", alluser.getPhoneNumber());
			response.put("email", alluser.getEmail());
			if (alluser.getRegistrationDate() != null) {
				response.put("registrationDate", alluser.getRegistrationDate());
			} else {
				response.put("registrationDate", null);
			}
			response.put("activeStatus", alluser.getActiveStatus());
			List<HashMap<String, String>> listOfAccessLevels = new ArrayList<>();
			if (alluser.getUserAccessMap() != null) {
				for (int i = 0; i < alluser.getUserAccessMap().size(); i++) {
					HashMap<String, String> userAccessLevel = new HashMap<>();
					userAccessLevel.put("accessLevel",
							alluser.getUserAccessMap().get(i).getUserTypepl().getUserTypeName());
					listOfAccessLevels.add(userAccessLevel);

				}
			}
			response.put("userType", listOfAccessLevels);
			if (alluser.getDrContractNumber() != null) {
				response.put("drContractNumber", alluser.getDrContractNumber());
			} else {
				response.put("drContractNumber", null);
			}
			byte status = 0;
			List<UserDRDevices> aud = drCustomerRepo.getAllUserDevices(customerId,status);
			List<DRDeviceDto> userDRdevices = new ArrayList<DRDeviceDto>();

			for (int i = 0; i < aud.size(); i++) {
				DRDeviceDto ddd = new DRDeviceDto();
				ddd.setDeviceCapacity(aud.get(i).getDevice_capacity());
				ddd.setDrDeviceId(aud.get(i).getUserDrDeviceId());
				ddd.setDrDeviceName(aud.get(i).getDeviceName());
				ddd.setPortNumber(aud.get(i).getPortNumber());
				ddd.setDeviceTypeId(aud.get(i).getDeviceTypeId().getDrDeviceTypeId());
				ddd.setDeviceTypeName(aud.get(i).getDeviceTypeId().getName());
				ddd.setPairedDevice(aud.get(i).getPairedDevice());
				if (!aud.get(i).getPortNumber().equalsIgnoreCase("NA")) {
				AllKiotSwitch kiotSwitch= allKiotSwithcesRepo.getAllKiotSwitchesById(Integer.parseInt((aud.get(i).getPortNumber())));
				if (kiotSwitch != null) {
					ddd.setCustomData(kiotSwitch.getCustomData());
					ddd.setKiotDeviceId(Integer.toString(kiotSwitch.getId()));
					ddd.setUsedFlag(kiotSwitch.getUsedFlag());
					ddd.setDeviceName(kiotSwitch.getDeviceCustomName());	
				}
				userDRdevices.add(ddd);
				}
				if (!aud.get(i).getRemoteNumber().equalsIgnoreCase("NA")) {
					AllKiotRemote kiotRemote= allKiotSwithcesRepo.getAllKiotRemoteById(Integer.parseInt((aud.get(i).getRemoteNumber())));
					if (kiotRemote != null) {
						ddd.setCustomData(kiotRemote.getCustomData());
						ddd.setKiotDeviceId(Integer.toString(kiotRemote.getId()));
						ddd.setUsedFlag(kiotRemote.getUsedFlag());
						ddd.setDeviceName(kiotRemote.getDeviceCustomName());	
					}
					userDRdevices.add(ddd);
				}
			}
			response.put("drCustomerDevice", userDRdevices);

			response.put("drContractDetails", drCustomerRepo.getBusinessContractDetails(alluser.getDrContractNumber()));
			// response.put("eventSets", eventSetDetailsDtoList);
			response.put("message", CustomMessages.getCustomMessages("FS"));
			response.put("key", "200");
		} catch (Exception e) {
			System.out.println("Error in checkExistence" + e.getMessage());
			e.printStackTrace();
			response.put("message", CustomMessages.getCustomMessages("ISE"));
			response.put("key", "500");

		}
		return response;
	}

	public List<HashMap<String, Object>> getAllCustomers() {

		List<HashMap<String, Object>> userList = new ArrayList<>();
		try {
			List<AllUser> users = alluserrepo.getAllDrCustomers();
			
			for (AllUser user : users) {
				double capacity = 0;
				DRContracts drcontracts = alluserrepo.getDrContracts(user.getDrContractNumber());
				HashMap<String, Object> response = new HashMap<String, Object>();
				response.put("customerId", user.getUserId());
				response.put("customerName", user.getFullName());
				response.put("customerType", "");
				response.put("contactNumber", user.getPhoneNumber());
				if (user.getUserDrDevices() != null) {
					response.put("energyAssets", user.getUserDrDevices().size());
					for (UserDRDevices device : user.getUserDrDevices()) {
						capacity = capacity + device.getDevice_capacity();
					}
					response.put("flexibleCapacity", capacity);
				} else {
					response.put("energyAssets", 0);
					response.put("flexibleCapacity", 0);
				}
				if (drcontracts != null) {
					response.put("division", drcontracts.getDivision());
					response.put("contractAccount", drcontracts.getContactNumber());
					response.put("meterNumber", drcontracts.getMeterNumber());
					response.put("sL", drcontracts.getSl());
					response.put("billedMus", drcontracts.getBilledMus());
					response.put("companyName", drcontracts.getCompnayName());
					response.put("companyAddress", drcontracts.getCompanyAddress());
					response.put("type", drcontracts.getCompanyType());
				} else {
					response.put("division", null);
					response.put("contractAccount", null);
					response.put("meterNumber", null);
					response.put("sL", null);
					response.put("billedMus", null);
					response.put("companyName", null);
					response.put("companyAddress", null);
					response.put("type", null);
				}
				userList.add(response); 
			}

		} catch (Exception e) {
			System.out.println("Error in checkExistence" + e.getMessage());
			e.printStackTrace();
			HashMap<String, Object> response = new HashMap<String, Object>();
			response.put("message", CustomMessages.getCustomMessages("ISE"));
			response.put("key", "500");
			userList.add(response);
		}
		return userList;
	}

	public HashMap<String, Object> updateDRCustomerDevice(HashMap<String, Object> inputDetails) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			drCustomerRepo.updateDrDeviceDetails((String) inputDetails.get("deviceName"),
					Double.parseDouble((String) inputDetails.get("deviceCapacity")),
					(int) inputDetails.get("userDrDeviceId"));
			response.put("message", CustomMessages.getCustomMessages("AS"));
			response.put("key", "200");
		} catch (Exception e) {
			System.out.println("Error in checkExistence" + e.getMessage());
			e.printStackTrace();
			response.put("message", CustomMessages.getCustomMessages("ISE"));
			response.put("key", "500");

		}
		return response;
	}

	public HashMap<String, Object> deleteDrDevice(int userDrDeviceId) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			UserDRDevices drDevice = drCustomerRepo.getDrDeviceDetails(userDrDeviceId);
			byte status = 1;
			if (drDevice.getPairedDevice() == -1 && drDevice != null) { 
			drCustomerRepo.updateKiotSwitch(Integer.parseInt(drDevice.getPortNumber()));
			drCustomerRepo.deleteEventCustomerDevices(userDrDeviceId);
			drCustomerRepo.deleteDrDeviceDetails(userDrDeviceId, status);
			} else {
				UserDRDevices pairedDevice = drCustomerRepo.getDrDeviceDetails(drDevice.getPairedDevice());
				List<Integer> devices = new ArrayList<>();
				devices.add(drDevice.getUserDrDeviceId());
				devices.add(pairedDevice.getUserDrDeviceId());
				
				if (drDevice.getPortNumber().equalsIgnoreCase("NA")) {
					drCustomerRepo.updateKiotRemote(Integer.parseInt(drDevice.getRemoteNumber()));
				} else {
					drCustomerRepo.updateKiotSwitch(Integer.parseInt(drDevice.getPortNumber()));
				}
				if (pairedDevice.getPortNumber().equalsIgnoreCase("NA")) {
					drCustomerRepo.updateKiotRemote(Integer.parseInt(pairedDevice.getRemoteNumber()));
				} else {
					drCustomerRepo.updateKiotSwitch(Integer.parseInt(pairedDevice.getPortNumber()));
				}
				drCustomerRepo.deleteEventCustomerDevices(devices);
//				drCustomerRepo.deleteCustomerPower(drDevice.getUserDrDeviceId());
//				drCustomerRepo.deleteCustomerPower(pairedDevice.getUserDrDeviceId());
				drCustomerRepo.deleteDrDeviceDetails(devices,status);
			}
			response.put("message", CustomMessages.getCustomMessages("DS"));
			response.put("key", "200");
		} catch (Exception e) {
			System.out.println("Error in checkExistence" + e.getMessage());
			e.printStackTrace();
			response.put("message", CustomMessages.getCustomMessages("ISE"));
			response.put("key", "500");

		}
		return response;
	}

	public HashMap<String, Object> addDrDevice(HashMap<String, Object> deviceDetails) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			AllUser alluser = alluserrepo.getUserById((int) (deviceDetails.get("userId")));
			KiotUserMapping kiotUserMapping = kiotUserMappingRepo
					.getKiotUserMappingByContract(alluser.getDrContractNumber());
			if (kiotUserMapping !=null) {
			List<AllKiotSwitch> kiotSwitches = allKiotSwithcesRepo
					.getAllKiotSwitches(kiotUserMapping.getKiotUserMappingId());
			List<AllKiotRemote> kiotRemotes = allKiotSwithcesRepo
					.getAllKiotRemotes(kiotUserMapping.getKiotUserMappingId());
			List<HashMap<String, String>> listOfUserDevice = (ArrayList<HashMap<String, String>>) deviceDetails
					.get("deviceDetails");
			if (kiotSwitches.isEmpty()) {
				response.put("message", "No switches available. Cannot add device");
				response.put("key", "200");
				return response;
			} else if (kiotSwitches.size() < listOfUserDevice.size()) {
				response.put("message", "Not enough switches. Cannot add device");
				response.put("key", "200");
				return response;
			}
			
			int count = userdrdevicerepo.getDrDeviceCount();
			ArrayList<UserDRDevices> listofDevices = new ArrayList<UserDRDevices>();
			for (int i = 0; i < listOfUserDevice.size(); i++) {
				HashMap<String, String> drdeviceDetails = listOfUserDevice.get(i);
				count = count + 1;
				int remoteCount = count;	
					if ((drdeviceDetails.get("deviceType").equalsIgnoreCase("AC"))) {
						if (kiotRemotes.isEmpty()) {
							DrDeviceTypePl switchType = userdrdevicerepo.getDrDeviceType("Switch");
							UserDRDevices userdrdevices = new UserDRDevices();
							userdrdevices.setAllUser(alluser);
							userdrdevices.setUserDrDeviceId(remoteCount);
							userdrdevices.setDeviceTypeId(switchType);
							userdrdevices.setDeviceName(drdeviceDetails.get("deviceName"));
							userdrdevices.setDevice_capacity(Double.parseDouble(drdeviceDetails.get("deviceCapacity")));
							userdrdevices.setPortNumber(Integer.toString(kiotSwitches.get(i).getId()));
							userdrdevices.setRemoteNumber("NA");
							userdrdevices.setPairedDevice(-1);
							allKiotSwithcesRepo.updateKiotSwicthes(kiotSwitches.get(i).getId());
							kiotSwitches.remove(i);
							userdrdevicerepo.saveAndFlush(userdrdevices);
							response.put("message", CustomMessages.getCustomMessages("SUC"));
							response.put("key", "200");
							return response;
						}
					DrDeviceTypePl switchType = userdrdevicerepo.getDrDeviceType("Switch");
					DrDeviceTypePl remote = userdrdevicerepo.getDrDeviceType("Remote");
					UserDRDevices userdrdevices = new UserDRDevices();
					userdrdevices.setAllUser(alluser);
					userdrdevices.setUserDrDeviceId(remoteCount);
					userdrdevices.setDeviceTypeId(switchType);
					userdrdevices.setDeviceName(drdeviceDetails.get("deviceName"));
					userdrdevices.setDevice_capacity(Double.parseDouble(drdeviceDetails.get("deviceCapacity")));
					userdrdevices.setPortNumber(Integer.toString(kiotSwitches.get(i).getId()));
					userdrdevices.setRemoteNumber("NA");
					allKiotSwithcesRepo.updateKiotSwicthes(kiotSwitches.get(i).getId());
					kiotSwitches.remove(i);
					listofDevices.add(userdrdevices);
					count = count + 1;
					int switchCount = count;
					UserDRDevices userdrdevices1 = new UserDRDevices();
					userdrdevices1.setAllUser(alluser);
					userdrdevices1.setUserDrDeviceId(switchCount);
					userdrdevices1.setDeviceTypeId(remote);
					userdrdevices1.setDeviceName(drdeviceDetails.get("deviceName")+" 27");
					double remotePower = (Double.parseDouble(AppStartupRunner.configValues.get("IRpowerRatio"))*(Double.parseDouble(drdeviceDetails.get("deviceCapacity"))));
					remotePower = (double)Math.round(remotePower * 100) / 100;	
					userdrdevices1.setDevice_capacity(remotePower);
					
					userdrdevices1.setRemoteNumber(Integer.toString(kiotRemotes.get(i).getId()));
					userdrdevices1.setPortNumber("NA");
					allKiotSwithcesRepo.updateKiotRemotes(kiotRemotes.get(i).getId());
					kiotRemotes.remove(i);
					listofDevices.add(userdrdevices1);
					userdrdevicerepo.saveAndFlush(userdrdevices);
					userdrdevicerepo.saveAndFlush(userdrdevices1);
					userdrdevicerepo.updatePairedDevice(remoteCount, switchCount);
					userdrdevicerepo.updatePairedDevice(switchCount, remoteCount);
				} else if((drdeviceDetails.get("deviceType").equalsIgnoreCase("LOAD_SHIFT"))){
					DrDeviceTypePl switchType = userdrdevicerepo.getDrDeviceType("Load Shift");
				UserDRDevices userdrdevices = new UserDRDevices();
				userdrdevices.setAllUser(alluser);
				userdrdevices.setUserDrDeviceId(count);
				userdrdevices.setDeviceTypeId(switchType);
				userdrdevices.setDeviceName(drdeviceDetails.get("deviceName"));
				userdrdevices.setDevice_capacity(Math.ceil((Double.parseDouble(drdeviceDetails.get("deviceCapacity")))));
				userdrdevices.setPortNumber(Integer.toString(kiotSwitches.get(i).getId()));
				userdrdevices.setPairedDevice(-1);
				userdrdevices.setRemoteNumber("NA");
				allKiotSwithcesRepo.updateKiotSwicthes(kiotSwitches.get(i).getId());
				kiotSwitches.remove(i);
				listofDevices.add(userdrdevices);
				userdrdevicerepo.saveAndFlush(userdrdevices);
				} else {
					DrDeviceTypePl switchType = userdrdevicerepo.getDrDeviceType("Switch");
				UserDRDevices userdrdevices = new UserDRDevices();
				userdrdevices.setAllUser(alluser);
				userdrdevices.setUserDrDeviceId(count);
				userdrdevices.setDeviceTypeId(switchType);
				userdrdevices.setDeviceName(drdeviceDetails.get("deviceName"));
				userdrdevices.setDevice_capacity(Math.ceil((Double.parseDouble(drdeviceDetails.get("deviceCapacity")))));
				userdrdevices.setPortNumber(Integer.toString(kiotSwitches.get(i).getId()));
				userdrdevices.setPairedDevice(-1);
				userdrdevices.setRemoteNumber("NA");
				allKiotSwithcesRepo.updateKiotSwicthes(kiotSwitches.get(i).getId());
				kiotSwitches.remove(i);
				listofDevices.add(userdrdevices);
				userdrdevicerepo.saveAndFlush(userdrdevices);
				}
				
			}
			
			response.put("message", CustomMessages.getCustomMessages("SUC"));
			response.put("key", "200");

			} else {
				response.put("message", "No switches available. Cannot add device");
				response.put("key", "200");
				return response;
			
			}
		} catch (Exception e) {
			System.out.println("Error in checkExistence" + e.getMessage());
			e.printStackTrace();
			response.put("message", CustomMessages.getCustomMessages("ISE"));
			response.put("key", "500");

		}
		return response;
	}

	public HashMap<String, Object> getEventCounts(int userId) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		int penaltyEventsCount = 0, participatedEventsCount = 0, successfulEventsCount = 0, cancelledEventsCount = 0;
		double earnings =0, customerFine=0;
		try {
			List<EventCustomerMapping> eventCustomerMappings = eventCustomerRepo.getEventCustomerByUserId(userId);
			for (EventCustomerMapping evmp : eventCustomerMappings) {
				if (evmp.getEventCustomerStatusId() == 3 || evmp.getEventCustomerStatusId() == 5) {
					participatedEventsCount++;
				} else if (evmp.getEventCustomerStatusId() == 15 && evmp.getIsFineApplicable().equalsIgnoreCase("N")) {
					successfulEventsCount++;
				} else if (evmp.getEventCustomerStatusId() == 15 && evmp.getIsFineApplicable().equalsIgnoreCase("Y")) {
					penaltyEventsCount++;
				} else if (evmp.getEventCustomerStatusId() == 9) {
					cancelledEventsCount++;
				}
			}
			List<EventCustomerMapping> eventCustomerMapEarnings = eventCustomerRepo.getEventCustomerEarnings(userId);
			for (EventCustomerMapping evmp : eventCustomerMapEarnings) {
				earnings=earnings+evmp.getEarnings();	
				customerFine = customerFine +evmp.getCustomerFine();
			}
			response.put("participatedEventsCount", participatedEventsCount);
			response.put("successfulEventsCount", successfulEventsCount);
			response.put("penaltyEventsCount", penaltyEventsCount);
			response.put("cancelledEventsCount", cancelledEventsCount);
			response.put("totalEarnings", earnings);
			response.put("totalPenalty", customerFine);
			response.put("message", CustomMessages.getCustomMessages("SUC"));
			response.put("key", "200");

		} catch (Exception e) {
			System.out.println("Error in checkExistence" + e.getMessage());
			e.printStackTrace();
			response.put("message", CustomMessages.getCustomMessages("ISE"));
			response.put("key", "500");

		}
		return response;
	}

}
