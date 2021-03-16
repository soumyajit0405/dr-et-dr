package com.energytrade.app.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.energytrade.app.services.DRCustomerService;

@CrossOrigin
@RestController
public class EventCustomerMappingController {

	@Autowired
	private DRCustomerService eventCustomerMappingService;

	@RequestMapping(value = "participateInEvent", method = RequestMethod.POST, headers = "Accept=application/json")
	public HashMap<String, Object> participateInEvent(@RequestBody HashMap<String, Object> inputDetails) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("response", eventCustomerMappingService.participateInEvent(inputDetails));
		return response;
	}

	@RequestMapping(value = "counterbidInEvent", method = RequestMethod.POST, headers = "Accept=application/json")
	public HashMap<String, Object> counterbidInEvent(@RequestBody HashMap<String, Object> inputDetails) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("response", eventCustomerMappingService.counterbidInEvent(inputDetails));
		return response;
	}

	@RequestMapping(value = "withdrawFromEvent", method = RequestMethod.POST, headers = "Accept=application/json")
	public HashMap<String, Object> withdrawFromEvent(@RequestBody HashMap<String, String> inputDetails) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		int userId = Integer.parseInt(inputDetails.get("userId"));
		int eventId = Integer.parseInt(inputDetails.get("eventId"));
		response.put("response", eventCustomerMappingService.withdrawFromEvent(userId, eventId));
		return response;
	}

	@RequestMapping(value = "updateEventCommitments", method = RequestMethod.POST, headers = "Accept=application/json")
	public HashMap<String, Object> updateEventCommitments(@RequestBody HashMap<String, Object> inputDetails) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		
		
		response.put("response", eventCustomerMappingService.updateEventCommitments(inputDetails));
		return response;
	}

}
