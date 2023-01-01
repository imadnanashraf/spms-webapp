package com.adnan.icode.fun.spms.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class EmailModel {
	
	@NotNull(message = "is required")
	@Email(message = "invalid email")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
