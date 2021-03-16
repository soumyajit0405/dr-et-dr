
package com.energytrade.app.dto;

import java.util.List;

public class AllEventDto extends AbstractBaseDto{

	private int eventId;
	private String eventName;
	private List<EventCustomerDto> listOfCustomers;
	private int noResponseCustomers;
	private int invitedCustomers;
	private int participatedCustomers;
	private int counterBidCustomers;
	private String startTime;
	private double buyerFine;
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
	public double getBuyerFine() {
		return buyerFine;
	}
	public void setBuyerFine(double buyerFine) {
		this.buyerFine = buyerFine;
	}
	public String getIsFineApplicable() {
		return isFineApplicable;
	}
	public void setIsFineApplicable(String isFineApplicable) {
		this.isFineApplicable = isFineApplicable;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getPlannedPower() {
		return plannedPower;
	}
	public void setPlannedPower(String plannedPower) {
		this.plannedPower = plannedPower;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getNumberOfCustomers() {
		return numberOfCustomers;
	}
	public void setNumberOfCustomers(String numberOfCustomers) {
		this.numberOfCustomers = numberOfCustomers;
	}
	public String getCommittedPower() {
		return committedPower;
	}
	public void setCommittedPower(String committedPower) {
		this.committedPower = committedPower;
	}
	public String getShortfall() {
		return shortfall;
	}
	public void setShortfall(String shortfall) {
		this.shortfall = shortfall;
	}
	public String getActualPower() {
		return actualPower;
	}
	public void setActualPower(String actualPower) {
		this.actualPower = actualPower;
	}
	private String endTime;
	private String plannedPower;
	private String price;
	private String numberOfCustomers;
	private String committedPower;
	private String shortfall;
	private String actualPower;
	
	public int getNoResponseCustomers() {
		return noResponseCustomers;
	}
	public void setNoResponseCustomers(int noResponseCustomers) {
		this.noResponseCustomers = noResponseCustomers;
	}
	public int getInvitedCustomers() {
		return invitedCustomers;
	}
	public void setInvitedCustomers(int invitedCustomers) {
		this.invitedCustomers = invitedCustomers;
	}
	public int getParticipatedCustomers() {
		return participatedCustomers;
	}
	public void setParticipatedCustomers(int participatedCustomers) {
		this.participatedCustomers = participatedCustomers;
	}
	public int getCounterBidCustomers() {
		return counterBidCustomers;
	}
	public void setCounterBidCustomers(int counterBidCustomers) {
		this.counterBidCustomers = counterBidCustomers;
	}
	public List<EventCustomerDto> getListOfCustomers() {
		return listOfCustomers;
	}
	public void setListOfCustomers(List<EventCustomerDto> listOfCustomers) {
		this.listOfCustomers = listOfCustomers;
	}
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public int getEventSetId() {
		return eventSetId;
	}
	public void setEventSetId(int eventSetId) {
		this.eventSetId = eventSetId;
	}
	public String getEventStatus() {
		return eventStatus;
	}
	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}
	public double getPower() {
		return power;
	}
	public void setPower(double power) {
		this.power = power;
	}
	private int eventSetId;
	private String eventStatus;
	private double power;
}
