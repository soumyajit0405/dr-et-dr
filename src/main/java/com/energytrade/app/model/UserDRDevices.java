package com.energytrade.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "user_dr_devices")
@NamedQuery(name = "UserDRDevices.findAll", query = "SELECT a FROM UserDRDevices a")
public class UserDRDevices {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "user_dr_device_id")
	private int userDrDeviceId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private AllUser allUser;

	@Column(name = "device_name")
	private String deviceName;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_ts")
	private Date createdTs;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_ts")
	private Date updatedTs;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sync_ts")
	private Date syncTs;
	
	@Column(name = "port_number")
	private String portNumber;
	
	@Column(name = "remote_number")
	private String remoteNumber;
	
	@Column(name = "paired_device")
	private int pairedDevice;

	
	public int getPairedDevice() {
		return pairedDevice;
	}

	public void setPairedDevice(int pairedDevice) {
		this.pairedDevice = pairedDevice;
	}

	@ManyToOne
	@JoinColumn(name = "device_type_id")
	private DrDeviceTypePl deviceTypeId;
	
	public String getRemoteNumber() {
		return remoteNumber;
	}

	public void setRemoteNumber(String remoteNumber) {
		this.remoteNumber = remoteNumber;
	}

	public DrDeviceTypePl getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(DrDeviceTypePl deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public String getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	private byte softdeleteflag;

	@Column(name = "device_capacity")
	private Double device_capacity;

	public int getUserDrDeviceId() {
		return userDrDeviceId;
	}

	public void setUserDrDeviceId(int userDrDeviceId) {
		this.userDrDeviceId = userDrDeviceId;
	}

	public AllUser getAllUser() {
		return allUser;
	}

	public void setAllUser(AllUser allUser) {
		this.allUser = allUser;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	public Date getUpdatedTs() {
		return updatedTs;
	}

	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}

	public Date getSyncTs() {
		return syncTs;
	}

	public void setSyncTs(Date syncTs) {
		this.syncTs = syncTs;
	}

	public byte getSoftdeleteflag() {
		return softdeleteflag;
	}

	public void setSoftdeleteflag(byte softdeleteflag) {
		this.softdeleteflag = softdeleteflag;
	}

	public Double getDevice_capacity() {
		return device_capacity;
	}

	public void setDevice_capacity(Double device_capacity) {
		this.device_capacity = device_capacity;
	}

	
	
}
