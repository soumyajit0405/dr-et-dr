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
@Table(name="cancelled_by_pl")
@NamedQuery(name="CancelledByPl.findAll", query="SELECT c FROM CancelledByPl c")
public class CancelledByPl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cancelled_by_id")
	private int cancelledById;

	@Column(name="cancelled_by_desc")
	private String cancelledByDesc;

	@Column(name="cancelled_by_name")
	private String cancelledByName;

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

	public CancelledByPl() {
	}

	public int getCancelledById() {
		return this.cancelledById;
	}

	public void setCancelledById(int cancelledById) {
		this.cancelledById = cancelledById;
	}

	public String getCancelledByDesc() {
		return this.cancelledByDesc;
	}

	public void setCancelledByDesc(String cancelledByDesc) {
		this.cancelledByDesc = cancelledByDesc;
	}

	public String getCancelledByName() {
		return this.cancelledByName;
	}

	public void setCancelledByName(String cancelledByName) {
		this.cancelledByName = cancelledByName;
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
		allContract.setCancelledByPl(this);

		return allContract;
	}

	public AllContract removeAllContract(AllContract allContract) {
		getAllContracts().remove(allContract);
		allContract.setCancelledByPl(null);

		return allContract;
	}

}