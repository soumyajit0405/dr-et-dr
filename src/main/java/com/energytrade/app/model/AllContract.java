package com.energytrade.app.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the all_contracts database table.
 * 
 */
@Entity
@Table(name="all_contracts")
@NamedQuery(name="AllContract.findAll", query="SELECT a FROM AllContract a")
public class AllContract implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="contract_id")
	private int contractId;

	@Column(name="active_status")
	private byte activeStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="cancelled_ts")
	private Date cancelledTs;

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

	//bi-directional many-to-one association to AllUser
	@ManyToOne
	@JoinColumn(name="buyer_id")
	private AllUser allUser;

	//bi-directional many-to-one association to AllSellOrder
	@ManyToOne
	@JoinColumn(name="sell_order_id")
	private AllSellOrder allSellOrder;

	//bi-directional many-to-one association to CancelledByPl
	@ManyToOne
	@JoinColumn(name="cancelled_by_id")
	private CancelledByPl cancelledByPl;

	//bi-directional many-to-one association to ContractStatusPl
	@ManyToOne
	@JoinColumn(name="contract_status_id")
	private ContractStatusPl contractStatusPl;

	//bi-directional many-to-one association to AllTransaction
	@OneToMany(mappedBy="allContract")
	private List<AllTransaction> allTransactions;
	
	@Column(name="cancel_reason")
	private String cancelReason;
	
	@Column(name="energy")
	private BigDecimal energy;
	
	@Column(name="is_fine_applicable")
	private String isFineApplicable;
	
	public String getIsFineApplicable() {
		return isFineApplicable;
	}

	public void setIsFineApplicable(String isFineApplicable) {
		this.isFineApplicable = isFineApplicable;
	}


	public BigDecimal getEnergy() {
		return energy;
	}

	public void setEnergy(BigDecimal energy) {
		this.energy = energy;
	}


	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public AllContract() {
	}

	public int getContractId() {
		return this.contractId;
	}

	public void setContractId(int contractId) {
		this.contractId = contractId;
	}

	public byte getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(byte activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Date getCancelledTs() {
		return this.cancelledTs;
	}

	public void setCancelledTs(Date cancelledTs) {
		this.cancelledTs = cancelledTs;
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

	public AllUser getAllUser() {
		return this.allUser;
	}

	public void setAllUser(AllUser allUser) {
		this.allUser = allUser;
	}

	public AllSellOrder getAllSellOrder() {
		return this.allSellOrder;
	}

	public void setAllSellOrder(AllSellOrder allSellOrder) {
		this.allSellOrder = allSellOrder;
	}

	public CancelledByPl getCancelledByPl() {
		return this.cancelledByPl;
	}

	public void setCancelledByPl(CancelledByPl cancelledByPl) {
		this.cancelledByPl = cancelledByPl;
	}

	public ContractStatusPl getContractStatusPl() {
		return this.contractStatusPl;
	}

	public void setContractStatusPl(ContractStatusPl contractStatusPl) {
		this.contractStatusPl = contractStatusPl;
	}

	public List<AllTransaction> getAllTransactions() {
		return this.allTransactions;
	}

	public void setAllTransactions(List<AllTransaction> allTransactions) {
		this.allTransactions = allTransactions;
	}

	public AllTransaction addAllTransaction(AllTransaction allTransaction) {
		getAllTransactions().add(allTransaction);
		allTransaction.setAllContract(this);

		return allTransaction;
	}

	public AllTransaction removeAllTransaction(AllTransaction allTransaction) {
		getAllTransactions().remove(allTransaction);
		allTransaction.setAllContract(null);

		return allTransaction;
	}

}