package com.energytrade.app.model;

public class NotificationResponseDto {

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getRecipients() {
		return recipients;
	}
	public void setRecipients(long recipients) {
		this.recipients = recipients;
	}
	public String getExternal_id() {
		return external_id;
	}
	public void setExternal_id(String external_id) {
		this.external_id = external_id;
	}
	private String id;
	private long recipients;
	private String external_id;
}
