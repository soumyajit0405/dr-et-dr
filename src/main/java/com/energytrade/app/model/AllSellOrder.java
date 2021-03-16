package com.energytrade.app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the all_sell_orders database table.
 * 
 */
@Entity
@Table(name="all_sell_orders")
@NamedQuery(name="AllSellOrder.findAll", query="SELECT a FROM AllSellOrder a")
public class AllSellOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="sell_order_id")
	private int sellOrderId;

	@Column(name="active_status")
	private byte activeStatus;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_ts")
	private Date createdTs;

	@Column(name="power_to_sell")
	private BigDecimal powerToSell;
	
	@Column(name="energy")
	private BigDecimal energy;

	public BigDecimal getEnergy() {
		return energy;
	}

	public void setEnergy(BigDecimal energy) {
		this.energy = energy;
	}

	@Column(name="rate_per_unit")
	private BigDecimal ratePerUnit;

	private byte softdeleteflag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sync_ts")
	private Date syncTs;

	@Column(name="total_amount")
	private BigDecimal totalAmount;

	@Column(name="transfer_end_ts")
	private Timestamp transferEndTs;

	@Column(name="transfer_start_ts")
	private Timestamp transferStartTs;

	@Column(name="updated_by")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_ts")
	private Date updatedTs;

	//bi-directional many-to-one association to AllContract
	@OneToMany(mappedBy="allSellOrder")
	private List<AllContract> allContracts;

	//bi-directional many-to-one association to AllUser
	@ManyToOne
	@JoinColumn(name="seller_id")
	private AllUser allUser;
	
	@ManyToOne
	@JoinColumn(name="user_device_id")
	private UserDevice userDevice;
	
	@Column(name="cancel_reason")
	private String cancelReason;

	@Column(name="is_fine_applicable")
	private String isFineApplicable;
	
	public String getIsFineApplicable() {
		return isFineApplicable;
	}

	public void setIsFineApplicable(String isFineApplicable) {
		this.isFineApplicable = isFineApplicable;
	}


	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public UserDevice getUserDevice() {
		return userDevice;
	}

	public void setUserDevice(UserDevice userDevice) {
		this.userDevice = userDevice;
	}

	//bi-directional many-to-one association to DevicePl
	@ManyToOne
	@JoinColumn(name="device_type_id")
	private DevicePl devicePl;

	//bi-directional many-to-one association to OrderStatusPl
	@ManyToOne
	@JoinColumn(name="order_status_id")
	private OrderStatusPl orderStatusPl;

	public AllSellOrder() {
	}

	public int getSellOrderId() {
		return this.sellOrderId;
	}

	public void setSellOrderId(int sellOrderId) {
		this.sellOrderId = sellOrderId;
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

	public BigDecimal getPowerToSell() {
		return this.powerToSell;
	}

	public void setPowerToSell(BigDecimal powerToSell) {
		this.powerToSell = powerToSell;
	}

	public BigDecimal getRatePerUnit() {
		return this.ratePerUnit;
	}

	public void setRatePerUnit(BigDecimal ratePerUnit) {
		this.ratePerUnit = ratePerUnit;
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

	public BigDecimal getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Timestamp getTransferEndTs() {
		return this.transferEndTs;
	}

	public void setTransferEndTs(Timestamp transferEndTs) {
		this.transferEndTs = transferEndTs;
	}

	public Timestamp getTransferStartTs() {
		return this.transferStartTs;
	}

	public void setTransferStartTs(Timestamp transferStartTs) {
		this.transferStartTs = transferStartTs;
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
		allContract.setAllSellOrder(this);

		return allContract;
	}

	public AllContract removeAllContract(AllContract allContract) {
		getAllContracts().remove(allContract);
		allContract.setAllSellOrder(null);

		return allContract;
	}

	public AllUser getAllUser() {
		return this.allUser;
	}

	public void setAllUser(AllUser allUser) {
		this.allUser = allUser;
	}

	public DevicePl getDevicePl() {
		return this.devicePl;
	}

	public void setDevicePl(DevicePl devicePl) {
		this.devicePl = devicePl;
	}

	public OrderStatusPl getOrderStatusPl() {
		return this.orderStatusPl;
	}

	public void setOrderStatusPl(OrderStatusPl orderStatusPl) {
		this.orderStatusPl = orderStatusPl;
	}

}