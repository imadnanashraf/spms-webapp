package com.adnan.icode.fun.spms.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AddSubjectModel {
	
	@NotNull(message = "is required")
	private String subject;
	
	@NotNull(message = "is required")
	@Pattern(regexp = "^[2][0-9]{3}", message = "batch must be in 2000's")
	private String batch;
	
	@NotNull(message = "is required")
	@Pattern(regexp = "[1-8]{1}", message = "semester must be between 1 to 8")
	private String semester;
	
	@NotNull(message = "is required")
	@Email(message = "invalid email")
	private String email;

	
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubjects() {
		return subject;
	}

	public void setSubjects(String subjects) {
		this.subject = subjects;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
