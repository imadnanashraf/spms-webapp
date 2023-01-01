package com.adnan.icode.fun.spms.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.annotation.Generated;
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
import org.hibernate.annotations.GeneratorType;
import com.adnan.icode.fun.spms.helper.DateAdder;


@Entity
@Table(name = "faculty")
public class Faculty {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;

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
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "faculty")
	private List<FacultyRole> roles;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "faculty")
	private List<FacultyVerificationToken> tokens;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "faculty")
	private List<MentorAllotStudents> allotStudent;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "faculty" )
	private ReadFacultyMessage readFacultyMessage;
	
	

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public List<FacultyVerificationToken> getTokens() {
		return tokens;
	}

	public void setTokens(List<FacultyVerificationToken> tokens) {
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

	public List<FacultyRole> getRoles() {
		return roles;
	}

	public void setRoles(List<FacultyRole> roles) {
		this.roles = roles;
	}
	
	public List<MentorAllotStudents> getAllotStudent() {
		return allotStudent;
	}

	public void setAllotStudent(List<MentorAllotStudents> allotStudent) {
		this.allotStudent = allotStudent;
	}
	
	

	
	
	//convenience method

	public ReadFacultyMessage getReadFacultyMessage() {
		return readFacultyMessage;
	}

	public void setReadFacultyMessage(ReadFacultyMessage readFacultyMessage) {
		this.readFacultyMessage = readFacultyMessage;
	}

	public void addRoles(FacultyRole tempRole) {
		if (roles == null) {
			roles = new ArrayList<FacultyRole>();
		}
		
		roles.add(tempRole);
		tempRole.setFaculty(this);
	}
	
	//// convenience method to link token and tokens to faculty
	
	public void addVerificationToken(FacultyVerificationToken tempToken) {
		// created date and expiry date setting of token
		DateAdder dateAdder = new DateAdder();
		Date createdDate = new Date();
		Date expiryDate = dateAdder.addHoursToDate(createdDate, 43800);
		
		// adding data to token
		tempToken.setCreatedDate(createdDate);
		tempToken.setExpiryDate(expiryDate);
		tempToken.setToken(UUID.randomUUID().toString());
		
		if(tokens == null) {
			tokens = new ArrayList<FacultyVerificationToken>();
		}
		
		tokens.add(tempToken);
		
		tempToken.setFaculty(this);
		
	}
	
	public void allotStudentsToMentor(MentorAllotStudents tempAllotStudents) {
		
		if(allotStudent == null) {
			
			allotStudent = new ArrayList<MentorAllotStudents>();
		
		}
		
		allotStudent.add(tempAllotStudents);
		
		tempAllotStudents.setFaculty(this);
	}
	
	public void addReadFacultytMessage(ReadFacultyMessage tempReadFacultyMessage) {
		
		if(readFacultyMessage == null) {
			
			readFacultyMessage = new ReadFacultyMessage();
			
		}
		
		readFacultyMessage = tempReadFacultyMessage;
		tempReadFacultyMessage.setFaculty(this);
		
	}
	
	

}
