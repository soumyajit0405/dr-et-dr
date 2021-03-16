package com.energytrade.app.dao;

import org.springframework.data.jpa.repository.Modifying;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.energytrade.app.model.AllElectricityBoard;
import com.energytrade.app.model.AllEventSet;
import com.energytrade.app.model.AllForecast;
import com.energytrade.app.model.AllOtp;
import com.energytrade.app.model.AllSellOrder;
import com.energytrade.app.model.AllState;
import com.energytrade.app.model.AllTimeslot;
import com.energytrade.app.model.AllUser;
import com.energytrade.app.model.ContractStatusPl;
import com.energytrade.app.model.EventSetStatusPl;
import com.energytrade.app.model.LocalityPl;
import com.energytrade.app.model.NonTradeHour;
import com.energytrade.app.model.NonTradehourStatusPl;
import com.energytrade.app.model.OrderStatusPl;
import com.energytrade.app.model.StateBoardMapping;
import com.energytrade.app.model.UserRolesPl;
import com.energytrade.app.model.UserTypePl;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface EventSetRepository extends JpaRepository<AllEventSet, Long>
{
      
	  
	  @Query("Select a from AllUser a where a.userId=?1 ") 
	  AllUser getUserById( int userId);
	 
	  @Query("Select a from AllEventSet a where a.eventSetId=?1 ") 
	  AllEventSet getEventSet(int eventSetId);
	  
	  @Query("Select COALESCE(max(eventSetId),0) from AllEventSet a ") 
	  int getEventSetCount();
	 
	  @Query("Select count(a.userId) from AllUser a where a.phoneNumber=?1  and a.password=?2") 
	  int loginUser(String phoneNumber, String password);
	  
	  @Query("Select a.activeVersion from AllEventSet a where a.eventSetId=?1") 
	  int getActiveVersion(int eventSetId);
	  
	  @Query("Select a from EventSetStatusPl a where a.statusName=?1 ") 
	  EventSetStatusPl getEventSetStatus(String eventStatusName);
	  
	  @Modifying
	  @Query("update AllEventSet a set a.eventSetStatusPl.eventSetStatusId=?1 where a.eventSetId=?2")
	  void updateEvent(int eventStatusId, int eventSetId);
	  
	  @Modifying
	  @Query("update AllEventSet a set a.plannedPower=?1,a.totalPrice=?2 where a.eventSetId=?3")
	  void updateEventSet(double power, double price, int eventSetId);
	  
	  @Modifying
	  @Query("update AllEventSet a set a.commitedPower=a.commitedPower+?1 where a.eventSetId=?2")
	  void addCommittedPower(double power, int eventSetId);
	  
	  @Modifying
	  @Query("update AllEventSet a set a.commitedPower=a.commitedPower-?1 where a.eventSetId=?2")
	  void removeCommittedPower(double power, int eventSetId);
	  
	  @Modifying
	  @Query("update AllEventSet a set a.activeVersion=?2 where a.eventSetId=?1")
	  void updateVersion(int eventSetId, int version);
	
	  @Query("Select count(a.eventSetId) from AllEventSet a where a.date=?1  and a.allUser.userId=?2 and a.divison=?3") 
	  int getEventSetCountPerDay(Date uploadDate, int userId, String divison);
        
}