package com.energytrade.app.dao;

import org.springframework.data.jpa.repository.Modifying;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.energytrade.app.model.AllElectricityBoard;
import com.energytrade.app.model.AllEventSet;
import com.energytrade.app.model.AllForecast;
import com.energytrade.app.model.AllOtp;
import com.energytrade.app.model.AllSellOrder;
import com.energytrade.app.model.AllState;
import com.energytrade.app.model.AllTimeslot;
import com.energytrade.app.model.AllUser;
import com.energytrade.app.model.ContractStatusPl;
import com.energytrade.app.model.DRContracts;
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
public interface EventCustomerRepository extends JpaRepository<EventCustomerMapping, Long>
{
      
	  
	  @Query("Select COALESCE(max(eventCustomerMappingId),0) from EventCustomerMapping a ") 
	  int getEventCustomerCount();
	  
	  @Query("Select a from EventCustomerMapping a where a.allEvent.eventId=?1 and a.allUser.userId=?2") 
	  EventCustomerMapping getEventCustomerById(int eventId,int customerId);
	 
	  @Query("Select a from UserAccessLevelMapping a, AllUser b, DRContracts c where a.userTypepl.userTypeId=2 and a.allUser.userId <> ?1 and b.userId=a.allUser.userId  and b.drContractNumber=c.contractNumber and c.division=?2") 
	  List<UserAccessLevelMapping> getUserAccessLevel(int userId, String location);
	  
	  
	  @Modifying
	  @Query("delete from EventCustomerMapping a where a.allEvent.eventId=?1") 
	  void removeCustomers(int eventId);
	 
	  @Query("Select a from UserAccessLevelMapping a where a.userTypepl.userTypeId=2") 
	  List<UserAccessLevelMapping> getUserAccessLevel();
	  
	  @Query("Select a from EventCustomerMapping a where a.allEvent.eventId=?1") 
	  List<EventCustomerMapping> getEventCustomerMappings(int eventId);
	  
	  @Query("Select a from EventCustomerStatusPl a where a.eventCustomerStatusId=?1") 
	  EventCustomerStatusPl getEventCustomerStatus(int statusId);
	  
	  @Modifying
	  @Query("update EventCustomerMapping set eventCustomerStatusId=?1 where allEvent.eventId=?2 ") 
	  void updateEventCustomer(int statusId, int eventId);
	  
	  @Modifying
	  @Query("update EventCustomerMapping set eventCustomerStatusId=?1 where allEvent.eventId=?2 and allUser.userId=?3") 
	  void updateEventCustomerbyId(int statusId, int eventId, int eventCustomerMapId);
	  
	  @Modifying
	  @Query("update EventCustomerMapping set counterBidFlag=?1,eventCustomerStatusId=?2 where allEvent.eventId=?3 and allUser.userId=?4") 
	  void acceptCounterBid(String status,int statusId, int eventId, int eventCustomerMapId);
	  
	  @Query("Select a from EventCustomerMapping a where  a.allUser.userId=?1") 
	  List<EventCustomerMapping> getEventCustomerByUserId(int customerId);
	  
	  @Query("Select a from EventCustomerMapping a where  a.allUser.userId=?1 and a.eventCustomerStatusId=15") 
	  List<EventCustomerMapping> getEventCustomerEarnings(int customerId);
	  
	  @Modifying
	  @Transactional
	  @Query("delete from EventCustomerMapping where allEvent.eventId in (?1)") 
	  void deleteByEventId(int eventId);
}