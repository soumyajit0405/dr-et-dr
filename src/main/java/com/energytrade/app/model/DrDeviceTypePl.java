package com.energytrade.app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cancelled_by_pl database table.
 * 
 */
@Entity
@Table(name="dr_device_type_pl")
@NamedQuery(name="DrDeviceTypePl.findAll", query="SELECT c FROM DrDeviceTypePl c")
public class DrDeviceTypePl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="dr_device_type_id")
	private int drDeviceTypeId;

	@Column(name="desc")
	private String desc;

	@Column(name="name")
	private String name;

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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_ts")
	private Date updatedTs;

	//bi-directional many-to-one association to AllContract
	@OneToMany(mappedBy="cancelledByPl")
	private List<AllContract> allContracts;

	public DrDeviceTypePl() {
	}


	public int getDrDeviceTypeId() {
		return drDeviceTypeId;
	}


	public void setDrDeviceTypeId(int drDeviceTypeId) {
		this.drDeviceTypeId = drDeviceTypeId;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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