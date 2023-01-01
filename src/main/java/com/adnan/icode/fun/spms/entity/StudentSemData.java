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
@Table(name = "student_sem_data")
public class StudentSemData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "semester")
	private int semester;
	
	@Column(name = "overall_subject_assessment")
	private float overallSubjectAssessment;
	
	@Column(name = "overall_previous_result")
	private float overallPreviousAssessment;
	
	@Column(name = "overall_internal_result")
	private float overallInternalAssessment;
	
	@Column(name = "overall_evaluation_result")
	private float overallEvaluation;
	
	@Column(name = "learner_type")
	private String learnerType;
	
	@Column(name = "internal_result_url")
	private String internalPic;
	
	@Column(name = "previous_result_url")
	private String externalPic;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, 
						 CascadeType.PERSIST, CascadeType.REFRESH},
			fetch = FetchType.EAGER)
	@JoinColumn(name = "student_id")
	private Student student;

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

	public float getOverallSubjectAssessment() {
		return overallSubjectAssessment;
	}

	public void setOverallSubjectAssessment(float overallSubjectAssessment) {
		this.overallSubjectAssessment = overallSubjectAssessment;
	}

	public float getOverallPreviousAssessment() {
		return overallPreviousAssessment;
	}

	public void setOverallPreviousAssessment(float overallPreviousAssessment) {
		this.overallPreviousAssessment = overallPreviousAssessment;
	}

	public float getOverallInternalAssessment() {
		return overallInternalAssessment;
	}

	public void setOverallInternalAssessment(float overallInternalAssessment) {
		this.overallInternalAssessment = overallInternalAssessment;
	}
	
	
	public float getOverallEvaluation() {
		return overallEvaluation;
	}

	public void setOverallEvaluation(float overallEvaluation) {
		this.overallEvaluation = overallEvaluation;
	}

	public String getLearnerType() {
		return learnerType;
	}

	public void setLearnerType(String learnerType) {
		this.learnerType = learnerType;
	}

	public String getInternalPic() {
		return internalPic;
	}

	public void setInternalPic(String internalPic) {
		this.internalPic = internalPic;
	}

	public String getExternalPic() {
		return externalPic;
	}

	public void setExternalPic(String externalPic) {
		this.externalPic = externalPic;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	
	
	
	
}
