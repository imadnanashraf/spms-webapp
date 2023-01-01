package com.adnan.icode.fun.spms.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PushNotificationModel {
	
	@NotNull(message = "is required")
	@Pattern(regexp = "^[2][0-9]{3}", message = "batch must be in 2000's")
	private String batch;
	
	@NotNull(message = "is required")
	private String notification;

	

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

	
	
}
