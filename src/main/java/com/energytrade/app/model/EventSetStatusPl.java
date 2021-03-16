package com.energytrade.app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the event_set_status_pl database table.
 * 
 */
@Entity
@Table(name="event_set_status_pl")
@NamedQuery(name="EventSetStatusPl.findAll", query="SELECT e FROM EventSetStatusPl e")
public class EventSetStatusPl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="event_set_status_id")
	private int eventSetStatusId;

	@Column(name="active_status")
	private byte activeStatus;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_ts")
	private Date createdTs;

	private String description;

	private byte softdeleteflag;

	@Column(name="status_name")
	private String statusName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sync_ts")
	private Date syncTs;

	@Column(name="updated_by")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_ts")
	private Date updatedTs;

	//bi-directional many-to-one association to AllEventSet
	@OneToMany(mappedBy="eventSetStatusPl")
	private List<AllEventSet> allEventSets;

	public EventSetStatusPl() {
	}

	public int getEventSetStatusId() {
		return this.eventSetStatusId;
	}

	public void setEventSetStatusId(int eventSetStatusId) {
		this.eventSetStatusId = eventSetStatusId;
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

	public byte getSoftdeleteflag() {
		return this.softdeleteflag;
	}

	public void setSoftdeleteflag(byte softdeleteflag) {
		this.softdeleteflag = softdeleteflag;
	}

	public String getStatusName() {
		return this.statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
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

	public List<AllEventSet> getAllEventSets() {
		return this.allEventSets;
	}

	public void setAllEventSets(List<AllEventSet> allEventSets) {
		this.allEventSets = allEventSets;
	}

	public AllEventSet addAllEventSet(AllEventSet allEventSet) {
		getAllEventSets().add(allEventSet);
		allEventSet.setEventSetStatusPl(this);

		return allEventSet;
	}

	public AllEventSet removeAllEventSet(AllEventSet allEventSet) {
		getAllEventSets().remove(allEventSet);
		allEventSet.setEventSetStatusPl(null);

		return allEventSet;
	}

}