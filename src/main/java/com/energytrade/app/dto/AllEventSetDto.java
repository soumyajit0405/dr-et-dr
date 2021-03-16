package com.energytrade.app.dto;

import java.util.Date;
import java.util.List;

import com.energytrade.app.model.EventSetEventDto;

public class AllEventSetDto extends AbstractBaseDto {

	private int eventSetId;
	private String eventSetName;
	private String eventSetStatus;
	private int userId;
	private String userName;
	private List<AllEventDto> allEvents;
	private String dateOfOccurence;
	private String plannedPower;
	private String actualPower;
	private String totalPrice;
	private String committedPower;
	public String getCommittedPower() {
		return committedPower;
	}

	public void setCommittedPower(String committedPower) {
		this.committedPower = committedPower;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	private String publishedEvents;
	private String completedEvents;
	private String cancelledEvents;
	public Date createdTs;
	public List<EventSetEventDto> events;

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	public List<AllEventDto> getAllEvents() {
		return allEvents;
	}

	public void setAllEvents(List<AllEventDto> allEvents) {
		this.allEvents = allEvents;
	}

	public int getEventSetId() {
		return eventSetId;
	}

	public String getDateOfOccurence() {
		return dateOfOccurence;
	}

	public void setDateOfOccurence(String dateOfOccurence) {
		this.dateOfOccurence = dateOfOccurence;
	}

	public String getPlannedPower() {
		return plannedPower;
	}

	public void setPlannedPower(String plannedPower) {
		this.plannedPower = plannedPower;
	}

	public String getActualPower() {
		return actualPower;
	}

	public void setActualPower(String actualPower) {
		this.actualPower = actualPower;
	}

	public String getPublishedEvents() {
		return publishedEvents;
	}

	public void setPublishedEvents(String publishedEvents) {
		this.publishedEvents = publishedEvents;
	}

	public String getCompletedEvents() {
		return completedEvents;
	}

	public void setCompletedEvents(String completedEvents) {
		this.completedEvents = completedEvents;
	}

	public String getCancelledEvents() {
		return cancelledEvents;
	}

	public void setCancelledEvents(String cancelledEvents) {
		this.cancelledEvents = cancelledEvents;
	}

	public void setEventSetId(int eventSetId) {
		this.eventSetId = eventSetId;
	}

	public String getEventSetName() {
		return eventSetName;
	}

	public void setEventSetName(String eventSetName) {
		this.eventSetName = eventSetName;
	}

	public String getEventSetStatus() {
		return eventSetStatus;
	}

	public void setEventSetStatus(String eventSetStatus) {
		this.eventSetStatus = eventSetStatus;
	}

	public List<EventSetEventDto> getEvents() {
		return events;
	}

	public void setEvents(List<EventSetEventDto> events) {
		this.events = events;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
