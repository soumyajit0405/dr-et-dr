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
import com.energytrade.app.model.DevicePl;
import com.energytrade.app.model.EventCustomerMapping;
import com.energytrade.app.model.EventSetEventDto;
import com.energytrade.app.model.UserAccessTypeMapping;
import com.energytrade.app.model.UserDRDevices;
import com.energytrade.app.model.UserDevice;
import com.energytrade.app.model.UserRolesPl;
import com.energytrade.app.model.UserTypePl;
import com.energytrade.app.util.CompareHelper;
import com.energytrade.app.util.CustomMessages;
import com.energytrade.app.dto.AllDrNotificationDto;
import com.energytrade.app.dto.AllEventSetDto;
import com.energytrade.app.dto.CustomerEventDetailsDto;
import com.energytrade.app.dto.CustomerEventMappingDetailsDto;
import com.energytrade.app.dto.DRDeviceDto;
import com.energytrade.app.dto.EventSetDetailsDto;
import com.energytrade.app.model.AllDrNotification;
import com.energytrade.app.model.AllEvent;
import com.energytrade.app.model.AllEventSet;

@Transactional
@Repository
public class DRNotificationDao {

	@Autowired
	AllDRNotificationsRepository allDrNotificationRepo;
	
	
	public HashMap<String, Object> getUserNotifications(int userId) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		List<AllDrNotificationDto> allNotifications = new ArrayList<AllDrNotificationDto>();
		try {
			List<AllDrNotification> listOfNotifications = allDrNotificationRepo.getUserNotifications(userId);
			for (AllDrNotification notification: listOfNotifications) {
				AllDrNotificationDto notificationdto = new AllDrNotificationDto();
				notificationdto.setActionId(notification.getActionId());
				notificationdto.setNotificationId(notification.getNotificationId());
				notificationdto.setNotificationType(notification.getNotificationTypePl().getTypeName());
				notificationdto.setStatus(notification.getNotificationStatusPl().getTypeName());
				notificationdto.setUserId(notification.getAllUser().getUserId());
				allNotifications.add(notificationdto);
			}
			response.put("notifications", allNotifications);
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
	
	
	public HashMap<String, Object> updateNotificationStatus(int notificationId) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		
		try {
		
			allDrNotificationRepo.updateNotificationStatus(2,notificationId);
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

