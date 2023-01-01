package com.adnan.icode.fun.spms.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.adnan.icode.fun.spms.validaion.FieldMatch;

//Not Necessary
//@FieldMatch.List({
//	@FieldMatch(first = "password", second = "matchingPassword", message = "password doesn't match")
//})
@FieldMatch(first = "password", second = "matchingPassword", message = "password doesn't match")
public class TempRegisterModel {
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	@Pattern(regexp = "^[a-zA-Z ]*", message = "enter valid characters")
	private String firstName;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	@Pattern(regexp = "^[a-zA-Z ]*", message = "enter valid characters")
	private String lastName;
	
	@NotNull(message = "is required")
	@Email(message = "invalid email")
	private String email;
	
	@NotNull(message = "is required")
	@Size(min = 6, message = "password must contains six digits")
	private String password;
	
	@NotNull(message = "is required")
	@Size(min = 6, message = "password must contains six digits")
	private String matchingPassword;
	
	@NotNull(message = "is required")
	private String dept;
	
	@NotNull(message = "is required")
	private String basicRole;

	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getBasicRole() {
		return basicRole;
	}

	public void setBasicRole(String basicRole) {
		this.basicRole = basicRole;
	}
	
	
	

}
