package com.energytrade.app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the all_dso database table.
 * 
 */
@Entity
@Table(name="all_dso")
@NamedQuery(name="AllDso.findAll", query="SELECT a FROM AllDso a")
public class AllDso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="dso_id")
	private int dsoId;

	private String city;

	@Column(name="company_address")
	private String companyAddress;

	@Column(name="contract_number")
	private String contractNumber;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_ts")
	private Date createdTs;

	@Column(name="dso_logo")
	private String dsoLogo;

	@Column(name="dso_name")
	private String dsoName;

	@Column(name="dso_profile_picture")
	private String dsoProfilePicture;

	@Column(name="email_id")
	private String emailId;

	private String password;

	private String pincode;

	@Column(name="primary_contact_email")
	private String primaryContactEmail;

	@Column(name="primary_contact_name")
	private String primaryContactName;

	@Column(name="primary_contact_phone")
	private String primaryContactPhone;

	private String region;

	@Column(name="secondary_contact_email")
	private String secondaryContactEmail;

	@Column(name="secondary_contact_name")
	private String secondaryContactName;

	@Column(name="secondary_contact_phone")
	private String secondaryContactPhone;

	private byte softdeleteflag;

	private String state;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sync_ts")
	private Date syncTs;

	@Column(name="updated_by")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_ts")
	private Date updatedTs;

	public AllDso() {
	}

	public int getDsoId() {
		return this.dsoId;
	}

	public void setDsoId(int dsoId) {
		this.dsoId = dsoId;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCompanyAddress() {
		return this.companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
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

	public String getDsoLogo() {
		return this.dsoLogo;
	}

	public void setDsoLogo(String dsoLogo) {
		this.dsoLogo = dsoLogo;
	}

	public String getDsoName() {
		return this.dsoName;
	}

	public void setDsoName(String dsoName) {
		this.dsoName = dsoName;
	}

	public String getDsoProfilePicture() {
		return this.dsoProfilePicture;
	}

	public void setDsoProfilePicture(String dsoProfilePicture) {
		this.dsoProfilePicture = dsoProfilePicture;
	}

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPincode() {
		return this.pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getPrimaryContactEmail() {
		return this.primaryContactEmail;
	}

	public void setPrimaryContactEmail(String primaryContactEmail) {
		this.primaryContactEmail = primaryContactEmail;
	}

	public String getPrimaryContactName() {
		return this.primaryContactName;
	}

	public void setPrimaryContactName(String primaryContactName) {
		this.primaryContactName = primaryContactName;
	}

	public String getPrimaryContactPhone() {
		return this.primaryContactPhone;
	}

	public void setPrimaryContactPhone(String primaryContactPhone) {
		this.primaryContactPhone = primaryContactPhone;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSecondaryContactEmail() {
		return this.secondaryContactEmail;
	}

	public void setSecondaryContactEmail(String secondaryContactEmail) {
		this.secondaryContactEmail = secondaryContactEmail;
	}

	public String getSecondaryContactName() {
		return this.secondaryContactName;
	}

	public void setSecondaryContactName(String secondaryContactName) {
		this.secondaryContactName = secondaryContactName;
	}

	public String getSecondaryContactPhone() {
		return this.secondaryContactPhone;
	}

	public void setSecondaryContactPhone(String secondaryContactPhone) {
		this.secondaryContactPhone = secondaryContactPhone;
	}

	public byte getSoftdeleteflag() {
		return this.softdeleteflag;
	}

	public void setSoftdeleteflag(byte softdeleteflag) {
		this.softdeleteflag = softdeleteflag;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
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