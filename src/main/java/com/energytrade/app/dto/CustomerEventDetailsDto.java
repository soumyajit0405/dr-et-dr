package com.energytrade.app.dto;

import java.util.Date;
import java.util.List;
import com.energytrade.app.model.UserDRDevices;

public class CustomerEventDetailsDto extends AbstractBaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int eventId;
	private String eventName;
	private String eventStatus;
	private Date eventStartTime;
	private Date eventEndTime;
	private Double plannedPower;
	private Double commitedPower;
	private Double actualPower;
	private Double expectedPrice;
	private Date createdTs;
	private CustomerEventMappingDetailsDto eventCustomerDetails;
	private Double buyerFine;
	private String isFineApplicable;
	private int eventTypeId;
	public int getEventTypeId() {
		return eventTypeId;
	}
	public void setEventTypeId(int eventTypeId) {
		this.eventTypeId = eventTypeId;
	}
	private String eventTypeName;
	
	
	public String getEventTypeName() {
		return eventTypeName;
	}
	public void setEventTypeName(String eventTypeName) {
		this.eventTypeName = eventTypeName;
	}
	
	
	
	public Double getBuyerFine() {
		return buyerFine;
	}
	public void setBuyerFine(Double buyerFine) {
		this.buyerFine = buyerFine;
	}
	public String getIsFineApplicable() {
		return isFineApplicable;
	}
	public void setIsFineApplicable(String isFineApplicable) {
		this.isFineApplicable = isFineApplicable;
	}


	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}

	public Date getEventStartTime() {
		return eventStartTime;
	}

	public void setEventStartTime(Date eventStartTime) {
		this.eventStartTime = eventStartTime;
	}

	public Date getEventEndTime() {
		return eventEndTime;
	}

	public void setEventEndTime(Date eventEndTime) {
		this.eventEndTime = eventEndTime;
	}

	public Double getPlannedPower() {
		return plannedPower;
	}

	public void setPlannedPower(Double plannedPower) {
		this.plannedPower = plannedPower;
	}

	public Double getCommitedPower() {
		return commitedPower;
	}

	public void setCommitedPower(Double commitedPower) {
		this.commitedPower = commitedPower;
	}

	public Double getActualPower() {
		return actualPower;
	}

	public void setActualPower(Double actualPower) {
		this.actualPower = actualPower;
	}

	public Double getExpectedPrice() {
		return expectedPrice;
	}

	public void setExpectedPrice(Double expectedPrice) {
		this.expectedPrice = expectedPrice;
	}

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	public CustomerEventMappingDetailsDto getEventCustomerDetails() {
		return eventCustomerDetails;
	}

	public void setEventCustomerDetails(CustomerEventMappingDetailsDto eventCustomerDetails) {
		this.eventCustomerDetails = eventCustomerDetails;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

}
