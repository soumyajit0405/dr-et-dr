package com.energytrade.app.dao;

import org.springframework.data.jpa.repository.Modifying;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.energytrade.app.model.AllDrNotification;
import com.energytrade.app.model.AllElectricityBoard;
import com.energytrade.app.model.AllEventSet;
import com.energytrade.app.model.AllForecast;
import com.energytrade.app.model.AllOtp;
import com.energytrade.app.model.AllSellOrder;
import com.energytrade.app.model.AllState;
import com.energytrade.app.model.AllTimeslot;
import com.energytrade.app.model.AllUser;
import com.energytrade.app.model.ContractStatusPl;
import com.energytrade.app.model.EventCustomerMapping;
import com.energytrade.app.model.EventCustomerStatusPl;
import com.energytrade.app.model.EventSetStatusPl;
import com.energytrade.app.model.LocalityPl;
import com.energytrade.app.model.NonTradeHour;
import com.energytrade.app.model.NonTradehourStatusPl;
import com.energytrade.app.model.OrderStatusPl;
import com.energytrade.app.model.StateBoardMapping;
import com.energytrade.app.model.UserAccessLevelMapping;
import com.energytrade.app.model.UserRolesPl;
import com.energytrade.app.model.UserTypePl;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AllDRNotificationsRepository extends JpaRepository<AllDrNotification, Long>
{
      	  
	  @Query("Select a from AllDrNotification a where  a.allUser.userId=?1") 
	  List<AllDrNotification> getUserNotifications(int userId);
	  
	  @Modifying
	    @Query("update AllDrNotification a set a.notificationStatusPl.notifStatusId=?1  where a.notificationId=?2")
	     void updateNotificationStatus(int status,int notificationId);
}