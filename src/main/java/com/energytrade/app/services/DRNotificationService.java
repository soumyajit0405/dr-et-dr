package com.energytrade.app.services;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.energytrade.app.dao.DRDao;
import com.energytrade.app.dao.DRNotificationDao;


@Service("DRNotificationService")
public class DRNotificationService extends AbstractBaseService
{
    @Autowired
    private DRNotificationDao drNotificationDao;
    
    public HashMap<String,Object> getUserNotifications(int userId) {
         return this.drNotificationDao.getUserNotifications(userId);
    }
    
    public HashMap<String, Object> updateNotificationStatus(int notificationId) {
    	 return this.drNotificationDao.updateNotificationStatus(notificationId);
    }
}