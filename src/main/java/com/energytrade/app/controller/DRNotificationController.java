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
import com.energytrade.app.services.DRNotificationService;

@CrossOrigin
@RestController
public class DRNotificationController extends AbstractBaseController {

	@Autowired
	DRNotificationService drNotificationService;

		
	@RequestMapping(value = "getUserNotifications/{userId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public HashMap<String, Object> getEventCounts(@PathVariable("userId") int userId) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("response", drNotificationService.getUserNotifications(userId));
		return response;
	}
	
	@RequestMapping(value = "updateNotificationStatus/{notificationId}", method = RequestMethod.PUT, headers = "Accept=application/json")
	public HashMap<String, Object> updateNotificationStatus(@PathVariable("notificationId") int notificationId) {

		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("response", drNotificationService.updateNotificationStatus(notificationId));
		return response;
	}
}
