package com.adnan.icode.fun.spms.models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

public class PercentageControllerVModel {
	
	@NotNull
	private int id;
	
	@NotNull
	private int internalThreshold;
	
	@NotNull
	private int previousThreshold;
	
	@NotNull
	private int classThreshold;
	
	@NotNull
	private int overallThreshold;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public int getInternalThreshold() {
		return internalThreshold;
	}

	public void setInternalThreshold(int internalThreshold) {
		this.internalThreshold = internalThreshold;
	}

	public int getPreviousThreshold() {
		return previousThreshold;
	}

	public void setPreviousThreshold(int previousThreshold) {
		this.previousThreshold = previousThreshold;
	}

	public int getClassThreshold() {
		return classThreshold;
	}

	public void setClassThreshold(int classThreshold) {
		this.classThreshold = classThreshold;
	}

	public int getOverallThreshold() {
		return overallThreshold;
	}

	public void setOverallThreshold(int overallThreshold) {
		this.overallThreshold = overallThreshold;
	}
	
	

}
