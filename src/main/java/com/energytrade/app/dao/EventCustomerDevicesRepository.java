
package com.energytrade.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.energytrade.app.model.EventCustomerDevices;
import com.energytrade.app.model.EventCustomerMapping;


@Repository
public interface EventCustomerDevicesRepository extends JpaRepository<EventCustomerDevices, Long> {
	
	@Modifying
	@Transactional
	@Query("delete from EventCustomerDevices where eventCustomerMapping.eventCustomerMappingId in (select eventCustomerMappingId from "
			+ "EventCustomerMapping where allEvent.eventId in (?1))") 
	void deleteByEventId(int eventId);
}
