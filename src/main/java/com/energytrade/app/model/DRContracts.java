package com.energytrade.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "dr_contracts")
@NamedQuery(name = "DRContracts.findAll", query = "SELECT a FROM DRContracts a")
public class DRContracts {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dr_contract_id")
	private int drContractId;

	@Column(name = "contract_number")
	private String contractNumber;

	@Column(name = "compnay_name")
	private String compnayName;

	@Column(name = "company_type")
	private String companyType;

	@Column(name = "division")
	private String division;

	@Column(name = "meter_number")
	private String meterNumber;

	@Column(name = "sl")
	private int sl;

	@Column(name = "billed_mus")
	private Double billedMus;

	@Column(name = "company_address")
	private String companyAddress;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "contact_number")
	private String contactNumber;

	@Column(name = "email_address")
	private String emailAddress;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_ts")
	private Date createdTs;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_ts")
	private Date updatedTs;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sync_ts")
	private Date syncTs;
	
	//bi-directional many-to-one association to AllUser
		@ManyToOne
		@JoinColumn(name="contract_type_id")
		private ContractTypePl contractType;

	private byte softdeleteflag;

	public int getDrContractId() {
		return drContractId;
	}

	public void setDrContractId(int drContractId) {
		this.drContractId = drContractId;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getCompnayName() {
		return compnayName;
	}

	public void setCompnayName(String compnayName) {
		this.compnayName = compnayName;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getMeterNumber() {
		return meterNumber;
	}

	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}

	public int getSl() {
		return sl;
	}

	public void setSl(int sl) {
		this.sl = sl;
	}

	public Double getBilledMus() {
		return billedMus;
	}

	public void setBilledMus(Double billedMus) {
		this.billedMus = billedMus;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	public Date getUpdatedTs() {
		return updatedTs;
	}

	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}

	public Date getSyncTs() {
		return syncTs;
	}

	public void setSyncTs(Date syncTs) {
		this.syncTs = syncTs;
	}

	public byte getSoftdeleteflag() {
		return softdeleteflag;
	}

	public void setSoftdeleteflag(byte softdeleteflag) {
		this.softdeleteflag = softdeleteflag;
	}

}
