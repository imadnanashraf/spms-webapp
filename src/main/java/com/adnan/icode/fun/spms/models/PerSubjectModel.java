package com.adnan.icode.fun.spms.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PerSubjectModel {
	
	private int id;
	
	@NotNull
	@Pattern(regexp = "[0-9]|10")
	private String behaviour;
	
	@NotNull
	@Pattern(regexp = "[0-9]|10")
	private String focus;
	
	@NotNull
	@Pattern(regexp = "[0-9]|10")
	private String attendance;
	
	private String overallSubject;
	
	private int semester;
	
	private String subject;
	
	private String email;
	
	private String searchType;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBehaviour() {
		return behaviour;
	}

	public void setBehaviour(String behaviour) {
		this.behaviour = behaviour;
	}

	public String getFocus() {
		return focus;
	}

	public void setFocus(String focus) {
		this.focus = focus;
	}

	public String getAttendance() {
		return attendance;
	}

	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}

	public String getOverallSubject() {
		return overallSubject;
	}

	public void setOverallSubject(String overallSubject) {
		this.overallSubject = overallSubject;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	
	
	

}
