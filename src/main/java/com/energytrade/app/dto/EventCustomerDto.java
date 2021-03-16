package com.energytrade.app.dto;

public class EventCustomerDto extends AbstractBaseDto{

	private int userId;
	private String userName;
	private int eventId;
	private String isSelected;
	private String participationStatus;
	private double actualPower;
	private String counterBidFlag;
	private double couterBidAmount;
	private int status;
	private double customerFine;
	private double earnings;
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
	private String isFineApplicable;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCounterBidFlag() {
		return counterBidFlag;
	}
	public void setCounterBidFlag(String counterBidFlag) {
		this.counterBidFlag = counterBidFlag;
	}
	public double getCouterBidAmount() {
		return couterBidAmount;
	}
	public void setCouterBidAmount(double couterBidAmount) {
		this.couterBidAmount = couterBidAmount;
	}
	public String getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}
	public String getParticipationStatus() {
		return participationStatus;
	}
	public double getActualPower() {
		return actualPower;
	}
	public void setActualPower(double actualPower) {
		this.actualPower = actualPower;
	}
	public void setParticipationStatus(String participationStatus) {
		this.participationStatus = participationStatus;
	}
	public double getCommitments() {
		return commitments;
	}
	public void setCommitments(double commitments) {
		this.commitments = commitments;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	private double commitments;
	private double price;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	}
