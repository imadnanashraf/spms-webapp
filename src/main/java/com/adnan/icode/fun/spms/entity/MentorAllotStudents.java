package com.adnan.icode.fun.spms.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "allot_students")
public class MentorAllotStudents {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "semester")
	private int semester;
	
	@Column(name = "batch")
	private int batch;
	
	@Column(name = "dept")
	private String dept;
	
	@Column(name = "learner_type")
	private String learnerType;
	
	@ManyToOne(fetch = FetchType.EAGER, 
			   cascade = {CascadeType.DETACH,CascadeType.MERGE, 
					      CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name = "faculty_id")
	private Faculty faculty;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public int getBatch() {
		return batch;
	}

	public void setBatch(int batch) {
		this.batch = batch;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getLearnerType() {
		return learnerType;
	}

	public void setLearnerType(String learnerType) {
		this.learnerType = learnerType;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}
	
	

}
