package com.adnan.icode.fun.spms.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class TempPasswordModel {
	
	@NotNull(message = "is required")
	@Email(message = "invalid email")
	private String email;
	
	@NotNull(message = "is required")
	private String role;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	

}
