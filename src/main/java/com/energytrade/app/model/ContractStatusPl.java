package com.energytrade.app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the contract_status_pl database table.
 * 
 */
@Entity
@Table(name="contract_status_pl")
@NamedQuery(name="ContractStatusPl.findAll", query="SELECT c FROM ContractStatusPl c")
public class ContractStatusPl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="contract_status_id")
	private int contractStatusId;

	@Column(name="contract_status_desc")
	private String contractStatusDesc;

	@Column(name="contract_status_name")
	private String contractStatusName;

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
	@OneToMany(mappedBy="contractStatusPl")
	private List<AllContract> allContracts;

	public ContractStatusPl() {
	}

	public int getContractStatusId() {
		return this.contractStatusId;
	}

	public void setContractStatusId(int contractStatusId) {
		this.contractStatusId = contractStatusId;
	}

	public String getContractStatusDesc() {
		return this.contractStatusDesc;
	}

	public void setContractStatusDesc(String contractStatusDesc) {
		this.contractStatusDesc = contractStatusDesc;
	}

	public String getContractStatusName() {
		return this.contractStatusName;
	}

	public void setContractStatusName(String contractStatusName) {
		this.contractStatusName = contractStatusName;
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

	public List<AllContract> getAllContracts() {
		return this.allContracts;
	}

	public void setAllContracts(List<AllContract> allContracts) {
		this.allContracts = allContracts;
	}

	public AllContract addAllContract(AllContract allContract) {
		getAllContracts().add(allContract);
		allContract.setContractStatusPl(this);

		return allContract;
	}

	public AllContract removeAllContract(AllContract allContract) {
		getAllContracts().remove(allContract);
		allContract.setContractStatusPl(null);

		return allContract;
	}

}