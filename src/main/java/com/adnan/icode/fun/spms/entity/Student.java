package com.adnan.icode.fun.spms.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.adnan.icode.fun.spms.helper.DateAdder;

@Entity
@Table(name = "student")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
		
	@Column(name = "first_name")
	private String firstName;
		
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "contact_no")
	private String contactNo;
	
	@Column(name = "fathers_name")
	private String fatherName;
	
	@Column(name = "guardian_contact_no")
	private String guardianContactNo;

	@Column(name = "email")
	private String email;
		
	@Column(name = "password")
	private String password;
		
	@Column(name = "dept")
	private String dept;
		
	@Column(name = "enabled")
	private boolean enabled;
	
	@Column(name = "profile_url")
	private String profilePic;
	
	@Column(name = "batch")
	private int batch;
	
	@Column(name = "university_enroll")
	private long universityEnroll;
	
		
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "student")
	private List<StudentRole> roles;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "student")
	private List<StudentVerificationToken> tokens;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "student")
	private List<StudentSemData> semData;
	
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "student")
	private List<StudentPerSubject> studentPerSubjects;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "student" )
	private ReadNotification readNotification;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "student" )
	private ReadStudentMessage readStudentMessage;
	

	public int getBatch() {
		return batch;
	}

	public void setBatch(int batch) {
		this.batch = batch;
	}

	public long getUniversityEnroll() {
		return universityEnroll;
	}

	public void setUniversityEnroll(long universityEnroll) {
		this.universityEnroll = universityEnroll;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public List<StudentVerificationToken> getTokens() {
		return tokens;
	}

	public void setTokens(List<StudentVerificationToken> tokens) {
		this.tokens = tokens;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<StudentRole> getRoles() {
		return roles;
	}

	public void setRoles(List<StudentRole> roles) {
		this.roles = roles;
	}
	
	
	public List<StudentSemData> getSemData() {
		return semData;
	}

	public void setSemData(List<StudentSemData> semData) {
		this.semData = semData;
	}
	

	public List<StudentPerSubject> getStudentPerSubjects() {
		return studentPerSubjects;
	}

	public void setStudentPerSubjects(List<StudentPerSubject> studentPerSubjects) {
		this.studentPerSubjects = studentPerSubjects;
	}

	public ReadNotification getReadNotification() {
		return readNotification;
	}

	public void setReadNotification(ReadNotification readNotification) {
		this.readNotification = readNotification;
	}
	
	
	
	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getGuardianContactNo() {
		return guardianContactNo;
	}

	public void setGuardianContactNo(String guardianContactNo) {
		this.guardianContactNo = guardianContactNo;
	}
	
	

	public ReadStudentMessage getReadStudentMessage() {
		return readStudentMessage;
	}

	public void setReadStudentMessage(ReadStudentMessage readStudentMessage) {
		this.readStudentMessage = readStudentMessage;
	}

	// convenience method
	public void addRoles(StudentRole tempRole) {
		
		if(roles == null) {
			roles = new ArrayList<StudentRole>();
		}
		
		roles.add(tempRole);
		tempRole.setStudent(this);
	}
	// convenience method to link token and tokens to student
	
	public void addVerificationToken(StudentVerificationToken tempToken) {
		
		// created date and expiry date setting of token
		DateAdder dateAdder = new DateAdder();
		Date currentDate = new Date();
		Date expiryDate = dateAdder.addHoursToDate(currentDate, 30);
		
		// adding data to token
		tempToken.setCreatedDate( currentDate);
		tempToken.setExpiryDate(expiryDate);
		tempToken.setToken(UUID.randomUUID().toString());
		
		if(tokens == null) {
			tokens = new ArrayList<StudentVerificationToken>();
		}
		
		tokens.add(tempToken);
		
		tempToken.setStudent(this);
		
	}
	
	public void addSemData(StudentSemData tempSemData) {
		
		if(semData == null) {
			semData = new ArrayList<StudentSemData>();
		}
		
		semData.add(tempSemData);
		tempSemData.setStudent(this);
		
	}

	public void addStudentSubjectData(StudentPerSubject tempSubjectData) {
		
		if(studentPerSubjects == null) {
			studentPerSubjects = new ArrayList<StudentPerSubject>();
		}
		
		studentPerSubjects.add(tempSubjectData);
		tempSubjectData.setStudent(this);
		
	}

	public void addReadNotification(ReadNotification tempReadNotification) {
		
		if(readNotification == null) {
			
			readNotification = new ReadNotification();
			
		}
		
		readNotification = tempReadNotification;
		tempReadNotification.setStudent(this);
		
	}
	
	public void addReadStudentMessage(ReadStudentMessage tempReadStudentMessage) {
		
		if(readStudentMessage == null) {
			
			readStudentMessage = new ReadStudentMessage();
			
		}
		
		readStudentMessage = tempReadStudentMessage;
		tempReadStudentMessage.setStudent(this);
		
	}
	
	


}
