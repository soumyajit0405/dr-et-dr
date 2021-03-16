package com.energytrade.app.util;

public enum ApplicationConstant {
	
	ORDER_CANCEL_STATUS("Hello! your order has been cancelled."),	
	
	APP_ID("9b0a5ec6-e306-4aa7-9713-722d8ee1f47c");
	
	
	public String responseStatus;
	
	ApplicationConstant(String responseStatus){
		this.responseStatus= responseStatus;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}
	

}
