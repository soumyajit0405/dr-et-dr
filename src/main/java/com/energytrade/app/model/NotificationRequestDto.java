package com.energytrade.app.model;

import java.io.Serializable;
public class NotificationRequestDto implements Serializable{

	
	private String app_id;
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String[] getInclude_external_user_ids() {
		return include_external_user_ids;
	}
	public void setInclude_external_user_ids(String[] include_external_user_ids) {
		this.include_external_user_ids = include_external_user_ids;
	}
	public RequestData getData() {
		return data;
	}
	public void setData(RequestData data) {
		this.data = data;
	}
	public Contents getEn() {
		return en;
	}
	public void setEn(Contents en) {
		this.en = en;
	}
	public String getToStartOtp() {
		return toStartOtp;
	}
	public void setToStartOtp(String toStartOtp) {
		this.toStartOtp = toStartOtp;
	}
	private String [] include_external_user_ids;
	private RequestData data;
	private Contents en;
	private String toStartOtp;
	
}
