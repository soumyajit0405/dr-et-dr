package com.energytrade.app.dto;

import java.util.Date;
import java.util.List;

import com.energytrade.app.model.UserDRDevices;

public class CustomerEventMappingDetailsDto extends AbstractBaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int eventCustomerMappingId;
	private Date bidTs;
	private Double bidPrice;
	private Double committedPower;
	private Double actualPower;
	private int eventCustomerStatus;
	private String counterBidFlag;
	private Double counterBidAmount;
	private Date createdTs;
	private List<DRDeviceDto> mappedDevices;
	private double customerFine;
	private double earnings;
	private String isFineApplicable;

	public double getEarnings() {
		return earnings;
	}
	public void setEarnings(double earnings) {
		this.earnings = earnings;
	}
	public double getCustomerFine() {
		return customerFine;
	}
	public void setCustomerFine(double customerFine) {
		this.customerFine = customerFine;
	}
	public String getIsFineApplicable() {
		return isFineApplicable;
	}
	public void setIsFineApplicable(String isFineApplicable) {
		this.isFineApplicable = isFineApplicable;
	}

	public int getEventCustomerMappingId() {
		return eventCustomerMappingId;
	}

	public void setEventCustomerMappingId(int eventCustomerMappingId) {
		this.eventCustomerMappingId = eventCustomerMappingId;
	}

	public Date getBidTs() {
		return bidTs;
	}

	public void setBidTs(Date bidTs) {
		this.bidTs = bidTs;
	}

	public Double getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(Double bidPrice) {
		this.bidPrice = bidPrice;
	}

	public Double getCommittedPower() {
		return committedPower;
	}

	public void setCommittedPower(Double committedPower) {
		this.committedPower = committedPower;
	}

	public Double getActualPower() {
		return actualPower;
	}

	public void setActualPower(Double actualPower) {
		this.actualPower = actualPower;
	}

	public int getEventCustomerStatus() {
		return eventCustomerStatus;
	}

	public void setEventCustomerStatus(int eventCustomerStatus) {
		this.eventCustomerStatus = eventCustomerStatus;
	}

	public String getCounterBidFlag() {
		return counterBidFlag;
	}

	public void setCounterBidFlag(String counterBidFlag) {
		this.counterBidFlag = counterBidFlag;
	}

	public Double getCounterBidAmount() {
		return counterBidAmount;
	}

	public void setCounterBidAmount(Double counterBidAmount) {
		this.counterBidAmount = counterBidAmount;
	}

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	public List<DRDeviceDto> getMappedDevices() {
		return mappedDevices;
	}

	public void setMappedDevices(List<DRDeviceDto> mappedDevices) {
		this.mappedDevices = mappedDevices;
	}

	
}
