package com.adnan.icode.fun.spms.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.adnan.icode.fun.spms.validaion.FieldMatch;

@FieldMatch(first = "password", second = "matchingPassword", message = "password doesn't match")
public class TempFormPasswordModel {
	
	@NotNull(message = "is required")
	@Size(min = 6, message = "password must contains six digits")
	private String password;
	
	@NotNull(message = "is required")
	@Size(min = 6, message = "password must contains six digits")
	private String matchingPassword;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	

}
