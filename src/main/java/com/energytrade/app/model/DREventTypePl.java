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
@Table(name="dr_event_types_pl")
@NamedQuery(name="DrEventTypePl.findAll", query="SELECT e FROM DREventTypePl e")
public class DREventTypePl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="dr_event_types_id")
	private int drEventTypeId;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_ts")
	private Date createdTs;

	private byte softdeleteflag;


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sync_ts")
	private Date syncTs;

	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="dr_event_types_name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_ts")
	private Date updatedTs;

	//bi-directional many-to-one association to AllEventSet
	@OneToMany(mappedBy="eventTypeId")
	private List<AllEvent> allEventSets;

	public DREventTypePl() {
	}

	public int getDrEventTypeId() {
		return drEventTypeId;
	}

	public void setDrEventTypeId(int drEventTypeId) {
		this.drEventTypeId = drEventTypeId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	public byte getSoftdeleteflag() {
		return softdeleteflag;
	}

	public void setSoftdeleteflag(byte softdeleteflag) {
		this.softdeleteflag = softdeleteflag;
	}


	public Date getSyncTs() {
		return syncTs;
	}

	public void setSyncTs(Date syncTs) {
		this.syncTs = syncTs;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedTs() {
		return updatedTs;
	}

	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}

	public List<AllEvent> getAllEventSets() {
		return allEventSets;
	}

	public void setAllEventSets(List<AllEvent> allEventSets) {
		this.allEventSets = allEventSets;
	}

	
}