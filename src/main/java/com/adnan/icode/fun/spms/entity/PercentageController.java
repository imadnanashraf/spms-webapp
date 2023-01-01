package com.adnan.icode.fun.spms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "percentage_controller")
public class PercentageController {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "internal_result_threshold")
	private int internalThreshold;
	
	@Column(name = "previous_result_threshold")
	private int previousThreshold;
	
	@Column(name = "class_result_threshold")
	private int classThreshold;
	
	@Column(name = "overall_result_threshold")
	private int overallThreshold;
	
	@Column(name = "dept")
	private String dept;

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

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}
	
	

}
