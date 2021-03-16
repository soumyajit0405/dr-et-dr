package com.energytrade.app.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.energytrade.app.model.AllEvent;
import com.energytrade.app.model.EventCustomerDevices;
import com.energytrade.app.model.EventCustomerMapping;
import com.energytrade.app.model.UserDRDevices;
import com.energytrade.app.model.UserDevice;
import com.energytrade.app.util.CustomMessages;

@Transactional
@Repository
public class EventCustomerMappingDao extends AbstractBaseDao {

	@Autowired
	EventCustomerMappingRepository eventCustomerMappingRepo;

	@Autowired
	UserDevicesRepository userDeviceRepository;

	@Autowired
	EventCustomerDevicesRepository eventCustomerDevicesRepository;
	
	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	EventSetRepository eventSetRepository;

	public HashMap<String, Object> participateInEvent(HashMap<String, Object> inputDetails) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		int userId = (int) inputDetails.get("userId");
		int eventId = (int) inputDetails.get("eventId");
		double committedPower = Double.parseDouble((String) inputDetails.get("committedPower"));
		ArrayList<Integer> deviceList = (ArrayList<Integer>) inputDetails.get("devices");

		try {
			AllEvent event = eventRepository.getEventById(eventId);
			if (event.getCommitedPower()+committedPower > event.getPlannedPower()) {
				response.put("message", "Power Committed is already more than Planned Power");
				response.put("key", "200");
				return response;
			}
			eventCustomerMappingRepo.participateInEvent(userId, eventId, committedPower);
			ArrayList<EventCustomerDevices> eventCustomerDevicesList = new ArrayList<EventCustomerDevices>();
			EventCustomerMapping eventCustomerMapping = eventCustomerMappingRepo.getEventCustomerMapping(eventId,
					userId);
			for (int i = 0; i < deviceList.size(); i++) {
				EventCustomerDevices eventCustomerDevices = new EventCustomerDevices();
				UserDRDevices userDrDevice = userDeviceRepository.getUserDRDevice(deviceList.get(i));
				eventCustomerDevices.setUserDrDevice(userDrDevice);
				eventCustomerDevices.setEventCustomerMapping(eventCustomerMapping);
				eventCustomerDevicesList.add(eventCustomerDevices);

			}
			eventCustomerDevicesRepository.saveAll(eventCustomerDevicesList);
			eventRepository.addEventPower(committedPower, eventId);
			eventSetRepository.addCommittedPower(committedPower, event.getAllEventSet().getEventSetId());	
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

	public HashMap<String, Object> counterbidInEvent(HashMap<String, Object> inputDetails) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		int userId = (int) inputDetails.get("userId");
		int eventId = (int) inputDetails.get("eventId");
		double committedPower = Double.parseDouble((String) inputDetails.get("committedPower"));
		double counterBidAmount = Double.parseDouble((String) inputDetails.get("counterBidAmount"));
		ArrayList<Integer> deviceList = (ArrayList<Integer>) inputDetails.get("devices");

		try {
			AllEvent event = eventRepository.getEventById(eventId);
			if (event.getCommitedPower()+committedPower > event.getPlannedPower()) {
				response.put("message", "Power Committed is already more than Planned Power");
				response.put("key", "200");
				return response;
			}
			eventCustomerMappingRepo.counterbidInEvent(userId, eventId, committedPower, counterBidAmount, "Y");
			ArrayList<EventCustomerDevices> eventCustomerDevicesList = new ArrayList<EventCustomerDevices>();
			EventCustomerMapping eventCustomerMapping = eventCustomerMappingRepo.getEventCustomerMapping(eventId,
					userId);
			for (int i = 0; i < deviceList.size(); i++) {
				EventCustomerDevices eventCustomerDevices = new EventCustomerDevices();
				UserDRDevices userDrDevice = userDeviceRepository.getUserDRDevice(deviceList.get(i));
				eventCustomerDevices.setUserDrDevice(userDrDevice);
				eventCustomerDevices.setEventCustomerMapping(eventCustomerMapping);
				eventCustomerDevicesList.add(eventCustomerDevices);

			}
			eventCustomerDevicesRepository.saveAll(eventCustomerDevicesList);
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

	public HashMap<String, Object> withdrawFromEvent(int userId, int eventId) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			EventCustomerMapping evmt= eventCustomerMappingRepo.getEventCustomerMapping(eventId, userId);
			AllEvent event = eventRepository.getEventById(eventId);
			if (event.getEventStatusPl().getEventStatusId()>=3) {
				response.put("message", "Event is not in either published or created state. So cannot withdraw");
				response.put("key", "200");
				return response;
			}
			eventCustomerMappingRepo.withdrawFromEvent(userId, eventId);
			eventCustomerMappingRepo.deleteEventDevices(evmt);
			if (evmt.getEventCustomerStatusId() == 3) {
			eventRepository.removeEventPower(evmt.getCommitedPower(),eventId);
			eventSetRepository.removeCommittedPower(evmt.getCommitedPower(), event.getAllEventSet().getEventSetId());
			}
			else if (evmt.getEventCustomerStatusId() == 4) {
				eventCustomerMappingRepo.withdrawFromEventInCounterBid(userId, eventId,event.getExpectedPrice());
			}
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

	public HashMap<String, Object> updateEventCommitments(HashMap<String, Object> inputDetails) {
		double updatedCommitedPower = 0, updatedCounterBidAmount = 0;
		ArrayList<Integer> updatedDeviceList = null;
		boolean noDevicesFlag = false;
		HashMap<String, Object> response = new HashMap<String, Object>();
		int userId = (int) inputDetails.get("userId");
		int eventId = (int) inputDetails.get("eventId");
		if ((String) inputDetails.get("updatedCommitedPower") != null) {
			updatedCommitedPower = Double.parseDouble((String) inputDetails.get("updatedCommitedPower"));
		}
		if ((String) inputDetails.get("updatedCounterBidAmount") != null) {
			updatedCounterBidAmount = Double.parseDouble((String) inputDetails.get("updatedCounterBidAmount"));
		}
		if (inputDetails.get("updatedDeviceList") != null) {
			updatedDeviceList = (ArrayList<Integer>) inputDetails.get("updatedDeviceList");
		} else {
			noDevicesFlag = true;
		}

		try {
			EventCustomerMapping evtmp = eventCustomerMappingRepo.getEventCustomerMapping(eventId, userId);
			double resultantPower = updatedCommitedPower - evtmp.getCommitedPower();
			eventCustomerMappingRepo.updateEventCommitments(userId, eventId, updatedCommitedPower,
					updatedCounterBidAmount);
			if (evtmp.getEventCustomerStatusId() == 3) {
			eventRepository.addEventPower(resultantPower, eventId);
			}
			ArrayList<EventCustomerDevices> eventCustomerDevicesList = new ArrayList<EventCustomerDevices>();
			EventCustomerMapping eventCustomerMapping = eventCustomerMappingRepo.getEventCustomerMapping(eventId,
					userId);
			if (!noDevicesFlag) {
				eventCustomerMappingRepo.deleteEventDevices(eventCustomerMapping);
				for (int i = 0; i < updatedDeviceList.size(); i++) {
					EventCustomerDevices eventCustomerDevices = new EventCustomerDevices();
					UserDRDevices userDrDevice = userDeviceRepository.getUserDRDevice(updatedDeviceList.get(i));
					eventCustomerDevices.setUserDrDevice(userDrDevice);
					eventCustomerDevices.setEventCustomerMapping(eventCustomerMapping);
					eventCustomerDevicesList.add(eventCustomerDevices);

				}
			}
			eventCustomerDevicesRepository.saveAll(eventCustomerDevicesList);
			response.put("message", CustomMessages.getCustomMessages("SUC"));
			response.put("key", "200");
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
