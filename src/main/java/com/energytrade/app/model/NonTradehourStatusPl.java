package com.energytrade.app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the non_tradehour_status_pl database table.
 * 
 */
@Entity
@Table(name="non_tradehour_status_pl")
@NamedQuery(name="NonTradehourStatusPl.findAll", query="SELECT n FROM NonTradehourStatusPl n")
public class NonTradehourStatusPl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="non_tradehour_status_id")
	private int nonTradehourStatusId;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_ts")
	private Date createdTs;

	@Column(name="non_tradehour_status_desc")
	private String nonTradehourStatusDesc;

	@Column(name="non_tradehour_status_name")
	private String nonTradehourStatusName;

	private byte softdeleteflag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sync_ts")
	private Date syncTs;

	@Column(name="updated_by")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_ts")
	private Date updatedTs;

	//bi-directional many-to-one association to NonTradeHour
	@OneToMany(mappedBy="nonTradehourStatusPl")
	private List<NonTradeHour> nonTradeHours;

	public NonTradehourStatusPl() {
	}

	public int getNonTradehourStatusId() {
		return this.nonTradehourStatusId;
	}

	public void setNonTradehourStatusId(int nonTradehourStatusId) {
		this.nonTradehourStatusId = nonTradehourStatusId;
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

	public String getNonTradehourStatusDesc() {
		return this.nonTradehourStatusDesc;
	}

	public void setNonTradehourStatusDesc(String nonTradehourStatusDesc) {
		this.nonTradehourStatusDesc = nonTradehourStatusDesc;
	}

	public String getNonTradehourStatusName() {
		return this.nonTradehourStatusName;
	}

	public void setNonTradehourStatusName(String nonTradehourStatusName) {
		this.nonTradehourStatusName = nonTradehourStatusName;
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

	public List<NonTradeHour> getNonTradeHours() {
		return this.nonTradeHours;
	}

	public void setNonTradeHours(List<NonTradeHour> nonTradeHours) {
		this.nonTradeHours = nonTradeHours;
	}

	public NonTradeHour addNonTradeHour(NonTradeHour nonTradeHour) {
		getNonTradeHours().add(nonTradeHour);
		nonTradeHour.setNonTradehourStatusPl(this);

		return nonTradeHour;
	}

	public NonTradeHour removeNonTradeHour(NonTradeHour nonTradeHour) {
		getNonTradeHours().remove(nonTradeHour);
		nonTradeHour.setNonTradehourStatusPl(null);

		return nonTradeHour;
	}

}