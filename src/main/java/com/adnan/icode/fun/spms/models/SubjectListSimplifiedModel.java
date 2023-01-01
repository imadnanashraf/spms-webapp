package com.adnan.icode.fun.spms.models;

public class SubjectListSimplifiedModel {
	
	private int subjectId;
	
	private String subject;
	
	private int batch;
	
	private int semester;
	
	private String facultyEmail;
	
	private String profilePic;
	
	private String facultyName;
	
	private String facultyDept;

	public int getSubjectId() {
		return subjectId;
	}
	
	

	public String getSubject() {
		return subject;
	}



	public void setSubject(String subject) {
		this.subject = subject;
	}



	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public int getBatch() {
		return batch;
	}

	public void setBatch(int batch) {
		this.batch = batch;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	

	public String getFacultyEmail() {
		return facultyEmail;
	}



	public void setFacultyEmail(String facultyEmail) {
		this.facultyEmail = facultyEmail;
	}



	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getFacultyName() {
		return facultyName;
	}

	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}

	public String getFacultyDept() {
		return facultyDept;
	}

	public void setFacultyDept(String facultyDept) {
		this.facultyDept = facultyDept;
	}
	
	
	
	

}
