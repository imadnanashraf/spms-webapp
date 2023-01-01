package com.adnan.icode.fun.spms.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

public class StudentAddMarksModel {
	
	@NotNull(message = "is required")
	@Pattern(regexp = "[1-8]{1}", message = "semester must be between 1 to 8")
	private String semester;
	
	@NotNull(message = "is required")
	@Range(min = 0, max = 100, message = "percentage must be between 0 to 100")
	private String overallInternalAssessment;
	
	@NotNull(message = "is required")
	@Range(min = 0, max = 100, message = "percentage must be between 0 to 100")
	private String overallPreviousAssessment;
	
	

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getOverallInternalAssessment() {
		return overallInternalAssessment;
	}

	public void setOverallInternalAssessment(String overallInternalAssessment) {
		this.overallInternalAssessment = overallInternalAssessment;
	}

	public String getOverallPreviousAssessment() {
		return overallPreviousAssessment;
	}

	public void setOverallPreviousAssessment(String overallPreviousAssessment) {
		this.overallPreviousAssessment = overallPreviousAssessment;
	}

	

}
