package com.adnan.icode.fun.spms.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AllotStudentToMentorModel {
	
	private String email;
	
	@NotNull(message = "is required")
	@Pattern(regexp = "^[2][0-9]{3}", message = "batch must be in 2000's")
	private String batch;
	
	@NotNull(message = "is required")
	@Pattern(regexp = "[1-8]{1}", message = "semester must be between 1 to 8")
	private String semester;
	
	@NotNull(message = "is required")
	@Pattern(regexp = "FAST|SLOW", message = "invalid learner")
	private String learner;

	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getLearner() {
		return learner;
	}

	public void setLearner(String learner) {
		this.learner = learner;
	}
	
	

}
