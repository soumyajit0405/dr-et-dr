package com.energytrade.app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the device_pl database table.
 * 
 */
@Entity
@Table(name="contract_type_pl")
@NamedQuery(name="ContractTypePl.findAll", query="SELECT d FROM ContractTypePl d")
public class ContractTypePl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="contract_type_pl_id")
	private int contractTypePlId;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_ts")
	private Date createdTs;

	@Column(name="contract_type_pl_name")
	private String contractTypePlName;

	@Column(name="contract_type_pl_desc")
	private String contractTypePlDesc;

	
	private byte softdeleteflag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sync_ts")
	private Date syncTs;

	@Column(name="updated_by")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_ts")
	private Date updatedTs;

	//bi-directional many-to-one association to UserDevice
	@OneToMany(mappedBy="contractType")
	private List<DRContracts> drContracts;

	public int getContractTypePlId() {
		return contractTypePlId;
	}


	public void setContractTypePlId(int contractTypePlId) {
		this.contractTypePlId = contractTypePlId;
	}


	public String getContractTypePlName() {
		return contractTypePlName;
	}


	public void setContractTypePlName(String contractTypePlName) {
		this.contractTypePlName = contractTypePlName;
	}


	public String getContractTypePlDesc() {
		return contractTypePlDesc;
	}


	public void setContractTypePlDesc(String contractTypePlDesc) {
		this.contractTypePlDesc = contractTypePlDesc;
	}


	public List<DRContracts> getDrContracts() {
		return drContracts;
	}


	public void setDrContracts(List<DRContracts> drContracts) {
		this.drContracts = drContracts;
	}


	public ContractTypePl() {
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