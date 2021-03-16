package com.energytrade.app.dto;

import java.util.Date;
import java.util.List;

import com.energytrade.app.model.EventSetEventDto;

public class EventSetDetailsDto extends AbstractBaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int eventSetId;
	private String eventSetStatus;
	private String eventSetName;
	private List<EventSetEventDto> events;
	private Date createdTs;
	private Date eventSetDate;

	public Date getEventSetDate() {
		return eventSetDate;
	}

	public void setEventSetDate(Date eventSetDate) {
		this.eventSetDate = eventSetDate;
	}

	public int getEventSetId() {
		return eventSetId;
	}

	public void setEventSetId(int eventSetId) {
		this.eventSetId = eventSetId;
	}

	public String getEventSetStatus() {
		return eventSetStatus;
	}

	public void setEventSetStatus(String eventSetStatus) {
		this.eventSetStatus = eventSetStatus;
	}

	public String getEventSetName() {
		return eventSetName;
	}

	public void setEventSetName(String eventSetName) {
		this.eventSetName = eventSetName;
	}

	public List<EventSetEventDto> getEvents() {
		return events;
	}

	public void setEvents(List<EventSetEventDto> events) {
		this.events = events;
	}

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

}
