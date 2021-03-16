package com.energytrade.app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the all_transactions database table.
 * 
 */
@Entity
@Table(name="all_power_transactions")
@NamedQuery(name="AllTransaction.findAll", query="SELECT a FROM AllTransaction a")
public class AllTransaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="txn_id")
	private int txnId;

	@Column(name="active_status")
	private byte activeStatus;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_ts")
	private Date createdTs;

	@Column(name="power_reading_end")
	private BigDecimal powerReadingEnd;

	@Column(name="power_reading_start")
	private BigDecimal powerReadingStart;

	private byte softdeleteflag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sync_ts")
	private Date syncTs;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="txn_end_ts")
	private Date txnEndTs;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="txn_start_ts")
	private Date txnStartTs;

	@Column(name="updated_by")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_ts")
	private Date updatedTs;

	//bi-directional many-to-one association to AllContract
	@ManyToOne
	@JoinColumn(name="contract_id")
	private AllContract allContract;

	//bi-directional many-to-one association to TxnStatusPl
	@ManyToOne
	@JoinColumn(name="txn_status_id")
	private TxnStatusPl txnStatusPl;

	@Column(name="cancel_reason")
	private String cancelReason;


	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public AllTransaction() {
	}

	public int getTxnId() {
		return this.txnId;
	}

	public void setTxnId(int txnId) {
		this.txnId = txnId;
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

	public BigDecimal getPowerReadingEnd() {
		return this.powerReadingEnd;
	}

	public void setPowerReadingEnd(BigDecimal powerReadingEnd) {
		this.powerReadingEnd = powerReadingEnd;
	}

	public BigDecimal getPowerReadingStart() {
		return this.powerReadingStart;
	}

	public void setPowerReadingStart(BigDecimal powerReadingStart) {
		this.powerReadingStart = powerReadingStart;
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

	public Date getTxnEndTs() {
		return this.txnEndTs;
	}

	public void setTxnEndTs(Date txnEndTs) {
		this.txnEndTs = txnEndTs;
	}

	public Date getTxnStartTs() {
		return this.txnStartTs;
	}

	public void setTxnStartTs(Date txnStartTs) {
		this.txnStartTs = txnStartTs;
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

	public AllContract getAllContract() {
		return this.allContract;
	}

	public void setAllContract(AllContract allContract) {
		this.allContract = allContract;
	}

	public TxnStatusPl getTxnStatusPl() {
		return this.txnStatusPl;
	}

	public void setTxnStatusPl(TxnStatusPl txnStatusPl) {
		this.txnStatusPl = txnStatusPl;
	}

}