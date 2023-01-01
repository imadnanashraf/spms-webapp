package com.adnan.icode.fun.spms.entity;

import javax.annotation.Generated;
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
@Table(name = "student_per_subject")
public class StudentPerSubject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id" )
	private int id;
	
	@Column(name = "semester" )
	private int semester;
	
	@Column(name = "subject_name")	
	private String subjectName;
	
	@Column(name = "behaviour")
	private int behaviour;

	@Column(name = "focus")
	private int focus;
	
	@Column(name = "attendance")
	private int attendance;
	
	@Column(name = "per_subject_overall")
	private float subjectOverall; 
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, 
			              CascadeType.PERSIST, CascadeType.REFRESH},
			   fetch = FetchType.LAZY)
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

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public int getBehaviour() {
		return behaviour;
	}

	public void setBehaviour(int behaviour) {
		this.behaviour = behaviour;
	}

	public int getFocus() {
		return focus;
	}

	public void setFocus(int focus) {
		this.focus = focus;
	}

	public int getAttendance() {
		return attendance;
	}

	public void setAttendance(int attendance) {
		this.attendance = attendance;
	}

	public float getSubjectOverall() {
		return subjectOverall;
	}

	public void setSubjectOverall(float subjectOverall) {
		this.subjectOverall = subjectOverall;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
	
			


}
