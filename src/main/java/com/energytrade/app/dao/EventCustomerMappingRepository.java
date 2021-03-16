package com.energytrade.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.energytrade.app.model.DevicePl;
import com.energytrade.app.model.EventCustomerMapping;

@Repository
public interface EventCustomerMappingRepository extends JpaRepository<EventCustomerMapping, Long> {

	@Modifying
	@Query("update EventCustomerMapping e set e.eventCustomerStatusId=3, e.commitedPower=?3 where e.allEvent.eventId=?2 and e.allUser.userId=?1")
	void participateInEvent(int userId, int eventId, double commitedPower);

	@Modifying
	@Query("update EventCustomerMapping e set e.eventCustomerStatusId=4, e.commitedPower=?3, e.bidPrice = ?4, e.counterBidFlag = ?5  where e.allEvent.eventId=?2 and e.allUser.userId=?1")
	void counterbidInEvent(int userId, int eventId, double commitedPower, double counterBidAmount, String counterBidFlag);

	@Modifying
	@Query("update EventCustomerMapping e set e.eventCustomerStatusId=2,e.commitedPower=0 where e.allEvent.eventId=?2 and e.allUser.userId=?1")
	void withdrawFromEvent(int userId, int eventId);
	
	@Modifying
	@Query("update EventCustomerMapping e set e.bidPrice=?3 where e.allEvent.eventId=?2 and e.allUser.userId=?1")
	void withdrawFromEventInCounterBid(int userId, int eventId, double bidPrice);

	@Modifying
	@Query("update EventCustomerMapping e set e.commitedPower=?3, e.counterBidAmount =?4 where e.allEvent.eventId=?2 and e.allUser.userId=?1")
	void updateEventCommitments(int userId, int eventId, double updatedCommitedPower, double counteBidAmount);

	
	@Modifying
	@Query("delete from EventCustomerDevices e where e.eventCustomerMapping=?1")
	void deleteEventDevices(EventCustomerMapping eventCustomerMapping);
	
	@Query("Select a from EventCustomerMapping a where a.allEvent.eventId=?1 and a.allUser.userId = ?2")
	EventCustomerMapping getEventCustomerMapping(int eventId, int customerId);

}
