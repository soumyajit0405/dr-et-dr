package com.energytrade.app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the kiot_user_mappings database table.
 * 
 */
@Entity
@Table(name="kiot_user_mappings")
@NamedQuery(name="KiotUserMapping.findAll", query="SELECT k FROM KiotUserMapping k")
public class KiotUserMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="kiot_user_mapping_id")
	private int kiotUserMappingId;

	@Column(name="bearer_token")
	private String bearerToken;

	@Column(name="contract_number")
	private String contractNumber;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_ts")
	private Date createdTs;

	@Column(name="kiot_user_id")
	private String kiotUserId;

	private byte softdeleteflag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sync_ts")
	private Date syncTs;

	@Column(name="updated_by")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_ts")
	private Date updatedTs;

	//bi-directional many-to-one association to AllKiotSwitch
	@OneToMany(mappedBy="kiotUserMapping")
	private List<AllKiotSwitch> allKiotSwitches;

	public KiotUserMapping() {
	}

	public int getKiotUserMappingId() {
		return this.kiotUserMappingId;
	}

	public void setKiotUserMappingId(int kiotUserMappingId) {
		this.kiotUserMappingId = kiotUserMappingId;
	}

	public String getBearerToken() {
		return this.bearerToken;
	}

	public void setBearerToken(String bearerToken) {
		this.bearerToken = bearerToken;
	}

	public String getContractNumber() {
		return this.contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
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

	public String getKiotUserId() {
		return this.kiotUserId;
	}

	public void setKiotUserId(String kiotUserId) {
		this.kiotUserId = kiotUserId;
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

	public List<AllKiotSwitch> getAllKiotSwitches() {
		return this.allKiotSwitches;
	}

	public void setAllKiotSwitches(List<AllKiotSwitch> allKiotSwitches) {
		this.allKiotSwitches = allKiotSwitches;
	}

	public AllKiotSwitch addAllKiotSwitch(AllKiotSwitch allKiotSwitch) {
		getAllKiotSwitches().add(allKiotSwitch);
		allKiotSwitch.setKiotUserMapping(this);

		return allKiotSwitch;
	}

	public AllKiotSwitch removeAllKiotSwitch(AllKiotSwitch allKiotSwitch) {
		getAllKiotSwitches().remove(allKiotSwitch);
		allKiotSwitch.setKiotUserMapping(null);

		return allKiotSwitch;
	}

}