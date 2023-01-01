package com.adnan.icode.fun.spms.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

public class TempStudentProfileModel {
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	@Pattern(regexp = "^[a-zA-Z ]*", message = "enter valid characters")
	private String firstName;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	@Pattern(regexp = "^[a-zA-Z ]*", message = "enter valid characters")
	private String lastName;
	
	@NotNull(message = "is required")
	@Pattern(regexp = "^[0-9]{10}", message = "enter valid contact no")
	private String contactNo;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	@Pattern(regexp = "^[a-zA-Z ]*", message = "enter valid characters")
	private String fatherName;
	
	@NotNull(message = "is required")
	@Pattern(regexp = "^[0-9]{10}", message = "enter valid contact no")
	private String guardianContactNo;
	
	@NotNull(message = "is required")
	@Pattern(regexp = "^[1-9][0-9]*", message = "enroll must be greater than 1")
	private String universityEnroll;
	
	@NotNull(message = "is required")
	@Pattern(regexp = "^[2][0-9]{3}", message = "batch must be in 2000's")
	private String batch;

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

	public String getUniversityEnroll() {
		return universityEnroll;
	}

	public void setUniversityEnroll(String universityEnroll) {
		this.universityEnroll = universityEnroll;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}



	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getGuardianContactNo() {
		return guardianContactNo;
	}

	public void setGuardianContactNo(String guardianContactNo) {
		this.guardianContactNo = guardianContactNo;
	}

	
	
	
	
	

}
