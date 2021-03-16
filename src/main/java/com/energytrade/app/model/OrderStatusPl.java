package com.energytrade.app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the order_status_pl database table.
 * 
 */
@Entity
@Table(name="order_status_pl")
@NamedQuery(name="OrderStatusPl.findAll", query="SELECT o FROM OrderStatusPl o")
public class OrderStatusPl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="order_status_id")
	private int orderStatusId;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_ts")
	private Date createdTs;

	@Column(name="order_status_desc")
	private String orderStatusDesc;

	@Column(name="order_status_name")
	private String orderStatusName;

	private byte softdeleteflag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sync_ts")
	private Date syncTs;

	@Column(name="updated_by")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_ts")
	private Date updatedTs;

	//bi-directional many-to-one association to AllSellOrder
	@OneToMany(mappedBy="orderStatusPl")
	private List<AllSellOrder> allSellOrders;

	public OrderStatusPl() {
	}

	public int getOrderStatusId() {
		return this.orderStatusId;
	}

	public void setOrderStatusId(int orderStatusId) {
		this.orderStatusId = orderStatusId;
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

	public String getOrderStatusDesc() {
		return this.orderStatusDesc;
	}

	public void setOrderStatusDesc(String orderStatusDesc) {
		this.orderStatusDesc = orderStatusDesc;
	}

	public String getOrderStatusName() {
		return this.orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
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

	public List<AllSellOrder> getAllSellOrders() {
		return this.allSellOrders;
	}

	public void setAllSellOrders(List<AllSellOrder> allSellOrders) {
		this.allSellOrders = allSellOrders;
	}

	public AllSellOrder addAllSellOrder(AllSellOrder allSellOrder) {
		getAllSellOrders().add(allSellOrder);
		allSellOrder.setOrderStatusPl(this);

		return allSellOrder;
	}

	public AllSellOrder removeAllSellOrder(AllSellOrder allSellOrder) {
		getAllSellOrders().remove(allSellOrder);
		allSellOrder.setOrderStatusPl(null);

		return allSellOrder;
	}

}