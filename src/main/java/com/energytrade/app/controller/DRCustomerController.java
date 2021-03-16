package com.energytrade.app.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.energytrade.app.services.DRCustomerService;

@CrossOrigin
@RestController
public class DRCustomerController extends AbstractBaseController {

	@Autowired
	DRCustomerService drCustomerService;

	@RequestMapping(value = "health", method = RequestMethod.GET, headers = "Accept=application/json")
	public HashMap<String, Object> healthCheck() {

		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("msg", "Server is up");
		return response;
	}

	@RequestMapping(value = "updateDrCustomerDetails", method = RequestMethod.POST, headers = "Accept=application/json")
	public HashMap<String, Object> updateDrCustomerDetails(@RequestBody HashMap<String, Object> inputDetails) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("response", drCustomerService.updateDrUserDetails((String) inputDetails.get("phoneNumber"),
				(String) inputDetails.get("fullName"), (String) inputDetails.get("drContractNumber")));
		return response;
	}

	@RequestMapping(value = "getBusinessContractDetails/{contractId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public HashMap<String, Object> getBusinessContractDetails(@PathVariable("contractId") String contractId) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("response", drCustomerService.getBusinessContractDetails(contractId));
		return response;
	}

	@RequestMapping(value = "getEventSetsForCustomer/{customerId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public HashMap<String, Object> getEventSetsForCustomer(@PathVariable("customerId") int customerId) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("response", drCustomerService.getEventSetsForCustomer(customerId));
		return response;
	}

	@RequestMapping(value = "getEventsForCustomerAndEventSet", method = RequestMethod.POST, headers = "Accept=application/json")
	public HashMap<String, Object> getEventsForCustomerAndEventSet(@RequestBody HashMap<String, Object> inputDetails) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("response", drCustomerService.getEventsForCustomerAndEventSet((int) inputDetails.get("customerId"),
				(int) inputDetails.get("eventSetId")));
		return response;
	}
	
	@RequestMapping(value = "getDRCustomerProfile", method = RequestMethod.POST, headers = "Accept=application/json")
	public HashMap<String, Object> getDRCustomerProfile(@RequestBody HashMap<String, Object> inputDetails) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("response", drCustomerService.getDRCustomerProfile((int) inputDetails.get("userId")));
		return response;
	}
	
	@RequestMapping(value = "updateDRCustomerDevice", method = RequestMethod.POST, headers = "Accept=application/json")
	public HashMap<String, Object> updateDRCustomerDevice(@RequestBody HashMap<String, Object> inputDetails) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("response", drCustomerService.updateDRCustomerDevice(inputDetails));
		return response;
	}
	
	@RequestMapping(value = "deleteDRCustomerDevice/{userDrDeviceId}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public HashMap<String, Object> deleteDRCustomerDevice(@PathVariable("userDrDeviceId") int drDeviceId) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("response", drCustomerService.deleteDrDevice(drDeviceId));
		return response;
	}
	
	@RequestMapping(value = "addDRCustomerDevice", method = RequestMethod.POST, headers = "Accept=application/json")
	public HashMap<String, Object> addDrDevice(@RequestBody HashMap<String, Object> deviceDetails) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("response", drCustomerService.addDrDevice(deviceDetails));
		return response;
	}
	
	@RequestMapping(value = "getAllCustomers", method = RequestMethod.GET, headers = "Accept=application/json")
	public HashMap<String, Object> getAllCustomers() {

		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("response", drCustomerService.getAllCustomers());
		return response;
	}
	
	@RequestMapping(value = "fetchEventCounts/{userId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public HashMap<String, Object> getEventCounts(@PathVariable("userId") int userId) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("response", drCustomerService.getEventCounts(userId));
		return response;
	}
}
