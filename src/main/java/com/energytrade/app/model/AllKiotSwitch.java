package com.energytrade.app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the all_kiot_switches database table.
 * 
 */
@Entity
@Table(name="all_kiot_switches")
@NamedQuery(name="AllKiotSwitch.findAll", query="SELECT a FROM AllKiotSwitch a")
public class AllKiotSwitch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_ts")
	private Date createdTs;

	@Column(name="kiot_device_id")
	private String kiotDeviceId;

	private byte softdeleteflag;

	@Column(name="custom_data")
	private String customData;

	public String getCustomData() {
		return customData;
	}

	public void setCustomData(String customData) {
		this.customData = customData;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sync_ts")
	private Date syncTs;

	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="device_custom_name")
	private String deviceCustomName;

	public String getDeviceCustomName() {
		return deviceCustomName;
	}

	public void setDeviceCustomName(String deviceCustomName) {
		this.deviceCustomName = deviceCustomName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_ts")
	private Date updatedTs;

	@Column(name="used_flag")
	private String usedFlag;

	//bi-directional many-to-one association to KiotUserMapping
	@ManyToOne
	@JoinColumn(name="kiot_user_mapping_id")
	private KiotUserMapping kiotUserMapping;

	public AllKiotSwitch() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getKiotDeviceId() {
		return this.kiotDeviceId;
	}

	public void setKiotDeviceId(String kiotDeviceId) {
		this.kiotDeviceId = kiotDeviceId;
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

	public String getUsedFlag() {
		return this.usedFlag;
	}

	public void setUsedFlag(String usedFlag) {
		this.usedFlag = usedFlag;
	}

	public KiotUserMapping getKiotUserMapping() {
		return this.kiotUserMapping;
	}

	public void setKiotUserMapping(KiotUserMapping kiotUserMapping) {
		this.kiotUserMapping = kiotUserMapping;
	}

}