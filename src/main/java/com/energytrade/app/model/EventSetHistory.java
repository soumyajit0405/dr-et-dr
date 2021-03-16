package com.energytrade.app.model;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the all_events database table.
 * 
 */
@Entity
@Table(name="event_set_version_history")
@NamedQuery(name="EventSetHistory.findAll", query="SELECT a FROM EventSetHistory a")
public class EventSetHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private int id;

	//bi-directional many-to-one association to AllEvent
		@ManyToOne
		@JoinColumn(name="event_id")
		private AllEvent allEvent;

		
	@Column(name="version")
	private int version;

	@Column(name="order_id")
	private int orderId;

	@Column(name="uploaded_file")
	private Blob uploadedFile;

	@Column(name="created_by")
	private String createdBy;
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public AllEvent getAllEvent() {
		return allEvent;
	}


	public void setAllEvent(AllEvent allEvent) {
		this.allEvent = allEvent;
	}


	public int getVersion() {
		return version;
	}


	public void setVersion(int version) {
		this.version = version;
	}


	public int getOrderId() {
		return orderId;
	}


	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}


	public Blob getUploadedFile() {
		return uploadedFile;
	}


	public void setUploadedFile(Blob uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_ts")
	private Date createdTs;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="event_end_time")
	private Date eventEndTime;

	@Column(name="event_name")
	private String eventName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="event_start_time")
	private Date eventStartTime;

	private byte softdeleteflag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sync_ts")
	private Date syncTs;

	@Column(name="updated_by")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_ts")
	private Date updatedTs;

	
	
	public EventSetHistory() {
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

	public Date getEventEndTime() {
		return this.eventEndTime;
	}

	public void setEventEndTime(Date eventEndTime) {
		this.eventEndTime = eventEndTime;
	}

	public String getEventName() {
		return this.eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Date getEventStartTime() {
		return this.eventStartTime;
	}

	public void setEventStartTime(Date eventStartTime) {
		this.eventStartTime = eventStartTime;
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


}