package com.energytrade.app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the non_trade_hours database table.
 * 
 */
@Entity
@Table(name="non_trade_hours")
@NamedQuery(name="NonTradeHour.findAll", query="SELECT n FROM NonTradeHour n")
public class NonTradeHour implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="non_trade_hour_id")
	private int nonTradeHourId;

	@Column(name="active_status")
	private byte activeStatus;

	@Column(name="cancellation_ts")
	private Timestamp cancellationTs;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_ts")
	private Timestamp createdTs;

	@Column(name="end_ts")
	private Timestamp endTs;

	private String location;

	@Column(name="non_trade_reason")
	private String nonTradeReason;

	private byte softdeleteflag;

	@Column(name="start_ts")
	private Timestamp startTs;

	@Column(name="sync_ts")
	private Timestamp syncTs;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_ts")
	private Timestamp updatedTs;

	//bi-directional many-to-one association to AllUser
	@ManyToOne
	@JoinColumn(name="aggregator_id")
	private AllUser allUser1;

	//bi-directional many-to-one association to AllUser
	@ManyToOne
	@JoinColumn(name="cancelled_by")
	private AllUser allUser2;

	//bi-directional many-to-one association to AllUser
	@ManyToOne
	@JoinColumn(name="locality_id")
	private LocalityPl locality;

	public LocalityPl getLocality() {
		return locality;
	}

	public void setLocality(LocalityPl locality) {
		this.locality = locality;
	}

	//bi-directional many-to-one association to NonTradehourStatusPl
	@ManyToOne
	@JoinColumn(name="status_id")
	private NonTradehourStatusPl nonTradehourStatusPl;

	public NonTradeHour() {
	}

	public int getNonTradeHourId() {
		return this.nonTradeHourId;
	}

	public void setNonTradeHourId(int nonTradeHourId) {
		this.nonTradeHourId = nonTradeHourId;
	}

	public byte getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(byte activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Timestamp getCancellationTs() {
		return this.cancellationTs;
	}

	public void setCancellationTs(Timestamp cancellationTs) {
		this.cancellationTs = cancellationTs;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedTs() {
		return this.createdTs;
	}

	public void setCreatedTs(Timestamp createdTs) {
		this.createdTs = createdTs;
	}

	public Timestamp getEndTs() {
		return this.endTs;
	}

	public void setEndTs(Timestamp endTs) {
		this.endTs = endTs;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNonTradeReason() {
		return this.nonTradeReason;
	}

	public void setNonTradeReason(String nonTradeReason) {
		this.nonTradeReason = nonTradeReason;
	}

	public byte getSoftdeleteflag() {
		return this.softdeleteflag;
	}

	public void setSoftdeleteflag(byte softdeleteflag) {
		this.softdeleteflag = softdeleteflag;
	}

	public Timestamp getStartTs() {
		return this.startTs;
	}

	public void setStartTs(Timestamp startTs) {
		this.startTs = startTs;
	}

	public Timestamp getSyncTs() {
		return this.syncTs;
	}

	public void setSyncTs(Timestamp syncTs) {
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

	public void setUpdatedTs(Timestamp updatedTs) {
		this.updatedTs = updatedTs;
	}

	public AllUser getAllUser1() {
		return this.allUser1;
	}

	public void setAllUser1(AllUser allUser1) {
		this.allUser1 = allUser1;
	}

	public AllUser getAllUser2() {
		return this.allUser2;
	}

	public void setAllUser2(AllUser allUser2) {
		this.allUser2 = allUser2;
	}

	public NonTradehourStatusPl getNonTradehourStatusPl() {
		return this.nonTradehourStatusPl;
	}

	public void setNonTradehourStatusPl(NonTradehourStatusPl nonTradehourStatusPl) {
		this.nonTradehourStatusPl = nonTradehourStatusPl;
	}

}