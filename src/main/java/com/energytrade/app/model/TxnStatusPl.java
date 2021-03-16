package com.energytrade.app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the txn_status_pl database table.
 * 
 */
@Entity
@Table(name="txn_status_pl")
@NamedQuery(name="TxnStatusPl.findAll", query="SELECT t FROM TxnStatusPl t")
public class TxnStatusPl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="txn_status_id")
	private int txnStatusId;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_ts")
	private Date createdTs;

	private byte softdeleteflag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sync_ts")
	private Date syncTs;

	@Column(name="txn_status_desc")
	private String txnStatusDesc;

	@Column(name="txn_status_name")
	private String txnStatusName;

	@Column(name="updated_by")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_ts")
	private Date updatedTs;

	//bi-directional many-to-one association to AllTransaction
	@OneToMany(mappedBy="txnStatusPl")
	private List<AllTransaction> allTransactions;

	public TxnStatusPl() {
	}

	public int getTxnStatusId() {
		return this.txnStatusId;
	}

	public void setTxnStatusId(int txnStatusId) {
		this.txnStatusId = txnStatusId;
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

	public String getTxnStatusDesc() {
		return this.txnStatusDesc;
	}

	public void setTxnStatusDesc(String txnStatusDesc) {
		this.txnStatusDesc = txnStatusDesc;
	}

	public String getTxnStatusName() {
		return this.txnStatusName;
	}

	public void setTxnStatusName(String txnStatusName) {
		this.txnStatusName = txnStatusName;
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

	public List<AllTransaction> getAllTransactions() {
		return this.allTransactions;
	}

	public void setAllTransactions(List<AllTransaction> allTransactions) {
		this.allTransactions = allTransactions;
	}

	public AllTransaction addAllTransaction(AllTransaction allTransaction) {
		getAllTransactions().add(allTransaction);
		allTransaction.setTxnStatusPl(this);

		return allTransaction;
	}

	public AllTransaction removeAllTransaction(AllTransaction allTransaction) {
		getAllTransactions().remove(allTransaction);
		allTransaction.setTxnStatusPl(null);

		return allTransaction;
	}

}