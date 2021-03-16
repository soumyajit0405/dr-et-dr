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

/**
 * The persistent class for the event_customer_devices database table.
 * 
 */
@Entity
@Table(name = "event_customer_devices")
@NamedQuery(name = "EventCustomerDevices.findAll", query = "SELECT a FROM EventCustomerDevices a")
public class EventCustomerDevices {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "event_customer_devices_id")
	private int eventCustomerDevicesId;

	
	// bi-directional many-to-one association to EventCustomerMapping
	@ManyToOne
	@JoinColumn(name = "event_customer_mapping")
	private EventCustomerMapping eventCustomerMapping;
	
	// bi-directional many-to-one association to UserDrDevice
	@ManyToOne
	@JoinColumn(name = "user_dr_device")
	private UserDRDevices userDrDevice;

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
	
	private byte softdeleteflag;

	public int getEventCustomerDevicesId() {
		return eventCustomerDevicesId;
	}

	public void setEventCustomerDevicesId(int eventCustomerDevicesId) {
		this.eventCustomerDevicesId = eventCustomerDevicesId;
	}

	public EventCustomerMapping getEventCustomerMapping() {
		return eventCustomerMapping;
	}

	public void setEventCustomerMapping(EventCustomerMapping eventCustomerMapping) {
		this.eventCustomerMapping = eventCustomerMapping;
	}

	
	public UserDRDevices getUserDrDevice() {
		return userDrDevice;
	}

	public void setUserDrDevice(UserDRDevices userDrDevice) {
		this.userDrDevice = userDrDevice;
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
}
