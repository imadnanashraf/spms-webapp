package com.adnan.icode.fun.spms.models;

import javax.validation.constraints.NotNull;

public class DepartmentOnlyModel {
	
	@NotNull(message = "is required")
	private String dept;

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}
	
	

}
