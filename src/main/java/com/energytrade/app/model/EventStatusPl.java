package com.energytrade.app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the event_status_pl database table.
 * 
 */
@Entity
@Table(name="event_status_pl")
@NamedQuery(name="EventStatusPl.findAll", query="SELECT e FROM EventStatusPl e")
public class EventStatusPl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="event_status_id")
	private int eventStatusId;

	@Column(name="active_status")
	private byte activeStatus;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_ts")
	private Date createdTs;

	private String description;

	private String name;

	private byte softdeleteflag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sync_ts")
	private Date syncTs;

	@Column(name="updated_by")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_ts")
	private Date updatedTs;

	//bi-directional many-to-one association to AllEvent
	@OneToMany(mappedBy="eventStatusPl")
	private List<AllEvent> allEvents;

	public EventStatusPl() {
	}

	public int getEventStatusId() {
		return this.eventStatusId;
	}

	public void setEventStatusId(int eventStatusId) {
		this.eventStatusId = eventStatusId;
	}

	public byte getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(byte activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTs() {
		return this.createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getSoftdeleteflag() {
		return this.softdeleteflag;
	}

	public void setSoftdeleteflag(byte softdeleteflag) {
		this.softdeleteflag = softdeleteflag;
	}

	public Date getSyncTs() {
		return this.syncTs;
	}

	public void setSyncTs(Date syncTs) {
		this.syncTs = syncTs;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedTs() {
		return this.updatedTs;
	}

	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}

	public List<AllEvent> getAllEvents() {
		return this.allEvents;
	}

	public void setAllEvents(List<AllEvent> allEvents) {
		this.allEvents = allEvents;
	}

	public AllEvent addAllEvent(AllEvent allEvent) {
		getAllEvents().add(allEvent);
		allEvent.setEventStatusPl(this);

		return allEvent;
	}

	public AllEvent removeAllEvent(AllEvent allEvent) {
		getAllEvents().remove(allEvent);
		allEvent.setEventStatusPl(null);

		return allEvent;
	}

}