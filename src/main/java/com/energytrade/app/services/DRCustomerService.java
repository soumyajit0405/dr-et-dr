package com.energytrade.app.services;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.energytrade.app.dao.DRCustomerDao;
import com.energytrade.app.dao.EventCustomerMappingDao;

@Service("eventCustomerMappingService")
public class DRCustomerService extends AbstractBaseService {

	@Autowired
	private EventCustomerMappingDao eventCustomerMappingDao;

	@Autowired
	private DRCustomerDao drCustomerDao;

	public HashMap<String, Object> participateInEvent(HashMap<String, Object> inputDetails) {
		return this.eventCustomerMappingDao.participateInEvent(inputDetails);
	}
	public HashMap<String, Object> counterbidInEvent(HashMap<String, Object> inputDetails) {
		return this.eventCustomerMappingDao.counterbidInEvent(inputDetails);
	}

	public HashMap<String, Object> withdrawFromEvent(int userId, int eventId) {
		return this.eventCustomerMappingDao.withdrawFromEvent(userId, eventId);
	}

	public HashMap<String, Object> updateEventCommitments(HashMap<String, Object> inputDetails) {
		return this.eventCustomerMappingDao.updateEventCommitments(inputDetails);
	}

	public HashMap<String, Object> getBusinessContractDetails(String contractId) {
		return this.drCustomerDao.getBusinessContractDetails(contractId);
	}

	public HashMap<String, Object> updateDrUserDetails(String phone, String fullName, String drContractNumber) {
		return this.drCustomerDao.updateDrUserDetails(phone, fullName, drContractNumber);
	}

	public HashMap<String, Object> getEventSetsForCustomer(int customerId) {
		return this.drCustomerDao.getEventSetsForCustomer(customerId);
	}

	public HashMap<String, Object> getEventsForCustomerAndEventSet(int customerId, int eventSetId) {
		return this.drCustomerDao.getEventsForCustomerAndEventSet(customerId, eventSetId);
	}
	
	public HashMap<String, Object> getDRCustomerProfile(int customerId) {
		return this.drCustomerDao.getDRCustomerProfile(customerId);
	}
	
	public HashMap<String, Object> updateDRCustomerDevice(HashMap<String,Object> inputDetails) {
		return this.drCustomerDao.updateDRCustomerDevice(inputDetails);
	}
	
	public HashMap<String, Object> deleteDrDevice(int drDeviceId) {
		return this.drCustomerDao.deleteDrDevice(drDeviceId);
	}
	
	public HashMap<String,Object> addDrDevice(HashMap<String,Object> deviceDetails) {
		return this.drCustomerDao.addDrDevice(deviceDetails);
	}
	
	public List<HashMap<String, Object>> getAllCustomers() {
		return this.drCustomerDao.getAllCustomers();
	}
	public HashMap<String,Object> getEventCounts(int userId) {
		return this.drCustomerDao.getEventCounts(userId);
	}

}
