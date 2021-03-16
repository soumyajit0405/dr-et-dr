package com.energytrade.app.model;

import java.util.Date;

import com.energytrade.app.dto.AbstractBaseDto;

public class EventSetEventDto extends AbstractBaseDto {

	private int eventId;
	private String eventCustomerMappingStatus;
	private String plannedPower;
	private String plannedPrice;
	private String eventName;
	private Date eventStartTime;
	private String counterBidFlag;
	private String earnings;
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
	public String getEarnings() {
		return earnings;
	}

	public void setEarnings(String earnings) {
		this.earnings = earnings;
	}

	public String getIsFineApplicable() {
		return isFineApplicable;
	}

	public void setIsFineApplicable(String isFineApplicable) {
		this.isFineApplicable = isFineApplicable;
	}

	public String getCounterBidFlag() {
		return counterBidFlag;
	}

	public void setCounterBidFlag(String counterBidFlag) {
		this.counterBidFlag = counterBidFlag;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
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

	public double getCommittedPower() {
		return committedPower;
	}

	public void setCommittedPower(double committedPower) {
		this.committedPower = committedPower;
	}

	public double getActualPower() {
		return actualPower;
	}

	public void setActualPower(double actualPower) {
		this.actualPower = actualPower;
	}

	public double getBidprice() {
		return bidprice;
	}

	public void setBidprice(double bidprice) {
		this.bidprice = bidprice;
	}

	public double getCounterBidAmount() {
		return counterBidAmount;
	}

	public void setCounterBidAmount(double counterBidAmount) {
		this.counterBidAmount = counterBidAmount;
	}

	public double getCustomerFine() {
		return customerFine;
	}

	public void setCustomerFine(double customerFine) {
		this.customerFine = customerFine;
	}

	private Date eventEndTime;
	private double committedPower;
	private double actualPower;
	private double bidprice;
	private double counterBidAmount;
	private double customerFine;
	

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	

	public String getEventCustomerMappingStatus() {
		return eventCustomerMappingStatus;
	}

	public void setEventCustomerMappingStatus(String eventCustomerMappingStatus) {
		this.eventCustomerMappingStatus = eventCustomerMappingStatus;
	}

	public String getPlannedPower() {
		return plannedPower;
	}

	public void setPlannedPower(String plannedPower) {
		this.plannedPower = plannedPower;
	}

	public String getPlannedPrice() {
		return plannedPrice;
	}

	public void setPlannedPrice(String plannedPrice) {
		this.plannedPrice = plannedPrice;
	}

	
}
