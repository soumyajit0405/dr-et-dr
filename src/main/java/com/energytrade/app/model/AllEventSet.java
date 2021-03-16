package com.energytrade.app.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the all_event_sets database table.
 * 
 */
@Entity
@Table(name="all_event_sets")
@NamedQuery(name="AllEventSet.findAll", query="SELECT a FROM AllEventSet a")
public class AllEventSet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="event_set_id")
	private int eventSetId;

	@Column(name="active_status")
	private byte activeStatus;

	@Column(name="actual_power")
	private double actualPower;

	@Column(name="commited_power")
	private double commitedPower;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_ts")
	private Date createdTs;

	@Temporal(TemporalType.DATE)
	private Date date;

	private String name;
	
	private String divison;

	public String getDivison() {
		return divison;
	}

	public void setDivison(String divison) {
		this.divison = divison;
	}

	@Column(name="planned_power")
	private double plannedPower;

	private byte softdeleteflag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sync_ts")
	private Date syncTs;

	@Column(name="total_price")
	private double totalPrice;

	@Column(name="updated_by")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_ts")
	private Date updatedTs;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="upload_time")
	private Date uploadTime;

	private int version;
	
	@Column(name="active_version")
	private int activeVersion;

	public int getActiveVersion() {
		return activeVersion;
	}

	public void setActiveVersion(int activeVersion) {
		this.activeVersion = activeVersion;
	}

	//bi-directional many-to-one association to EventSetStatusPl
	@ManyToOne
	@JoinColumn(name="status_id")
	private EventSetStatusPl eventSetStatusPl;

	@JsonIgnore
	//bi-directional many-to-one association to AllEvent
	@OneToMany(mappedBy="allEventSet")
	private List<AllEvent> allEvents;

	@JsonIgnore
	//bi-directional many-to-one association to AllUser
	@ManyToOne
	@JoinColumn(name="dso_id")
	private AllUser allUser;

	public AllEventSet() {
	}

	public int getEventSetId() {
		return this.eventSetId;
	}

	public void setEventSetId(int eventSetId) {
		this.eventSetId = eventSetId;
	}

	public byte getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(byte activeStatus) {
		this.activeStatus = activeStatus;
	}

	public double getActualPower() {
		return this.actualPower;
	}

	public void setActualPower(double actualPower) {
		this.actualPower = actualPower;
	}

	public double getCommitedPower() {
		return this.commitedPower;
	}

	public void setCommitedPower(double commitedPower) {
		this.commitedPower = commitedPower;
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

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPlannedPower() {
		return this.plannedPower;
	}

	public void setPlannedPower(double plannedPower) {
		this.plannedPower = plannedPower;
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

	public double getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
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

	public Date getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public EventSetStatusPl getEventSetStatusPl() {
		return this.eventSetStatusPl;
	}

	public void setEventSetStatusPl(EventSetStatusPl eventSetStatusPl) {
		this.eventSetStatusPl = eventSetStatusPl;
	}

	public List<AllEvent> getAllEvents() {
		return this.allEvents;
	}

	public void setAllEvents(List<AllEvent> allEvents) {
		this.allEvents = allEvents;
	}

	public AllEvent addAllEvent(AllEvent allEvent) {
		getAllEvents().add(allEvent);
		allEvent.setAllEventSet(this);

		return allEvent;
	}

	public AllEvent removeAllEvent(AllEvent allEvent) {
		getAllEvents().remove(allEvent);
		allEvent.setAllEventSet(null);

		return allEvent;
	}

	public AllUser getAllUser() {
		return this.allUser;
	}

	public void setAllUser(AllUser allUser) {
		this.allUser = allUser;
	}

}