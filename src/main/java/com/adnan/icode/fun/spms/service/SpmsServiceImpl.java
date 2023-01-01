package com.adnan.icode.fun.spms.service;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adnan.icode.fun.spms.dao.FacultyDao;
import com.adnan.icode.fun.spms.dao.GeneralDao;
import com.adnan.icode.fun.spms.dao.StudentDao;
import com.adnan.icode.fun.spms.entity.Faculty;
import com.adnan.icode.fun.spms.entity.FacultyRole;
import com.adnan.icode.fun.spms.entity.FacultyVerificationToken;
import com.adnan.icode.fun.spms.entity.MentorAllotStudents;
import com.adnan.icode.fun.spms.entity.MessageCenter;
import com.adnan.icode.fun.spms.entity.Notification;
import com.adnan.icode.fun.spms.entity.PercentageController;
import com.adnan.icode.fun.spms.entity.ReadNotification;
import com.adnan.icode.fun.spms.entity.Student;
import com.adnan.icode.fun.spms.entity.StudentPerSubject;
import com.adnan.icode.fun.spms.entity.StudentRole;
import com.adnan.icode.fun.spms.entity.StudentSemData;
import com.adnan.icode.fun.spms.entity.StudentVerificationToken;
import com.adnan.icode.fun.spms.entity.SubjectsList;
import com.adnan.icode.fun.spms.models.AllotStudentToMentorModel;
import com.adnan.icode.fun.spms.models.PerSubjectModel;
import com.adnan.icode.fun.spms.models.SortedSemDataModel;
import com.adnan.icode.fun.spms.models.StudentFilterModel;
import com.adnan.icode.fun.spms.models.TempFormPasswordModel;
import com.adnan.icode.fun.spms.models.TempPasswordModel;
import com.adnan.icode.fun.spms.models.TempRegisterModel;
import com.adnan.icode.fun.spms.models.TempStudentProfileModel;
@Service
@PropertySource({"classpath:email-config.properties"})
public class SpmsServiceImpl implements SpmsService {
	
	@Autowired
	private FacultyDao facultyDao;
	
	@Autowired
	private StudentDao studentDao;
	
	@Autowired
	private GeneralDao generalDao;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	@Value("${link.redirect}")
	private String linkRedirect;
	
	@Value("${mail.username}")
	private String sender;
	
	
	@Override
	@Transactional
	public void registrationNewUser(TempRegisterModel tempModel, 
									HttpServletRequest request) {
		
		String registerType = tempModel.getBasicRole();
		
		if(registerType.equals("student")) {
			// registration for student
			
			//creating objects of entity
			Student tempStudent = new Student();
			StudentRole tempStudentRole = new StudentRole();
			StudentVerificationToken tempStudentVerificationToken = new StudentVerificationToken();
			
			//exchanging data from model to entity
			tempStudentRole.setRole("ROLE_STUDENT");
			
			tempStudent.setFirstName(tempModel.getFirstName().toUpperCase());
			tempStudent.setLastName(tempModel.getLastName().toUpperCase());
			tempStudent.setEmail(tempModel.getEmail().toLowerCase());
			tempStudent.setPassword(bCryptPasswordEncoder.encode(tempModel.getPassword()));
			tempStudent.setEnabled(false);
			tempStudent.setDept(tempModel.getDept().toUpperCase());
			tempStudent.setProfilePic("defaultprofile.jpg");
			tempStudent.setBatch(0);
			tempStudent.setUniversityEnroll(0);
			
			//assigning roles to student using convenience method
			tempStudent.addRoles(tempStudentRole);
			
			//assigning token to student with time created
			tempStudent.addVerificationToken(tempStudentVerificationToken);
			
			//saving using student dao
			studentDao.registerStudent(tempStudent);
			 
			//sending mail to verify email
			String url = linkRedirect+request.getContextPath()+"/start/studentConfirmation";
			String renewUrl = linkRedirect+request.getContextPath()+"/start/renewEmail";
			String token = tempStudent.getTokens().get(tempStudent.getTokens().size()-1).getToken();
			
			String mailContent = "<p>Dear "+tempStudent.getFirstName()+" Please confirm your account, please click here :</p>"+
								 "<p>Link Expires in 30 minutes </P"+
								 "<h3><a href="+url+"?token="+token+">VERIFY ACCOUNT</a></h3>"+
								 "<p>If link expires please click here, </p>"+
								 "<p><a href="+renewUrl+"?email="+tempStudent.getEmail()+"&account=student>Renew</a></p>"+
								 "<p>Thank You</p>";
			
			emailService.sendEmail(tempStudent.getEmail(), mailContent, sender);
			
			
			
		}else if(registerType.equals("faculty") ) {
			//registration for faculty
			
			//creating objects of entity
			Faculty tempFaculty = new Faculty();
			FacultyRole tempFacultyRole = new FacultyRole();
			FacultyVerificationToken tempFacultyVerificationToken = new FacultyVerificationToken();
			
			//exchanging data from model to entity
			tempFacultyRole.setRole("ROLE_FACULTY");
			
			tempFaculty.setFirstName(tempModel.getFirstName().toUpperCase());
			tempFaculty.setLastName(tempModel.getLastName().toUpperCase());
			tempFaculty.setEmail(tempModel.getEmail().toLowerCase());
			tempFaculty.setPassword(bCryptPasswordEncoder.encode(tempModel.getPassword()));
			tempFaculty.setEnabled(false);
			tempFaculty.setDept(tempModel.getDept().toUpperCase());
			tempFaculty.setProfilePic("defaultprofile.jpg");
			
			//assigning roles to student using convenience method
			tempFaculty.addRoles(tempFacultyRole);
			
			//assigning token to student with time created
			tempFaculty.addVerificationToken(tempFacultyVerificationToken);
			
			//saving using faculty dao
			facultyDao.registerFaculty(tempFaculty);
			
			//sending mail to verify email
			String url = linkRedirect+request.getContextPath()+"/start/facultyConfirmation";
			String renewUrl = linkRedirect+request.getContextPath()+"/start/renewEmail";
			String token = tempFaculty.getTokens().get(tempFaculty.getTokens().size()-1).getToken();
			
			String mailContent = "<p>Dear "+tempFaculty.getFirstName()+" Please confirm your account, please click here :</p>"+
								 "<p> Link Expires in 1 month </P"+
								 "<h3><a href="+url+"?token="+token+">VERIFY ACCOUNT</a></h3>"+
								 "<p>If link expires please click here, </p>"+
								 "<p><a href="+renewUrl+"?email="+tempFaculty.getEmail()+"&account=faculty>Renew</a></p>"+
								 "<p>Thank You</p>";
			
			emailService.sendEmail(tempFaculty.getEmail(), mailContent, sender);
			
			
		}

	}
	
	@Override
	@Transactional
	public Student loadStudentByEmail(String email) {
		
		return studentDao.findStudentByEmail(email);
	}

	@Override
	@Transactional
	public Faculty loadFacultyByEmail(String email) {
		
		return facultyDao.findFacultyByEmail(email);
	}
	
	@Override
	@Transactional
	public List<Faculty> loadFacultyByDept(String dept) {
		
		return facultyDao.findFacultyByDept(dept);
	}
	
	@Override
	@Transactional
	public Faculty loadFacultyByEmailWithRoles(String facultyEmail) {
		
		return facultyDao.findFacultyByEmailWithRoles(facultyEmail);
	}
	
	@Override
	@Transactional
	public List<Faculty> loadFacultyByDeptWithRoles(String dept) {
		
		return facultyDao.findFacultyByDeptWithRoles(dept);
	}
	
	@Override
	@Transactional
	public Faculty loadFacultyByEmailWithStudents(String facultyEmail) {
		
		return facultyDao.findFacultyByEmailWithStudents(facultyEmail);
	}

	
	@Override
	@Transactional
	public Student loadStudentByEnroll(long enroll) {
		
		return studentDao.findStudentByEnroll(enroll);
	}

	@Override
	@Transactional
	public StudentVerificationToken findStudentToken(String token) {
		
		return studentDao.loadStudentToken(token);
	}

	@Override
	@Transactional
	public FacultyVerificationToken findFacultyToken(String token) {
		
		return facultyDao.loadFacultytoken(token);
	}
	
	
	@Override
	@Transactional
	public void updateStudent(Student tempStudent) {
		
		studentDao.registerStudent(tempStudent);
	}
	
	@Override
	@Transactional
	public void updateFaculty(Faculty tempFaculty) {
		
		facultyDao.registerFaculty(tempFaculty);
	}

	
	@Override
	@Transactional
	public void deleteAllFacultyTokens(Faculty tempFaculty) {
		
		facultyDao.deleteAllTokens(tempFaculty);
		
	}
	
	@Override
	@Transactional
	public void addFacultyRole(String email, String permitRole) {
		
		Faculty currentFaculty = new Faculty();
		FacultyRole addRole = new FacultyRole();
		
		currentFaculty = facultyDao.findFacultyByEmail(email);
		addRole.setRole(permitRole);
		
		currentFaculty.addRoles(addRole);
		
		facultyDao.registerFaculty(currentFaculty);
		
	}

	
	
	@Override
	@Transactional
	public void deleteFacultyRole(String email, String revokeRole) {
		
		Faculty currentFaculty = new Faculty();
		FacultyRole deleteRole = new FacultyRole();
		
		currentFaculty = facultyDao.findFacultyByEmail(email);
		
		for(FacultyRole tempFacultyRole: currentFaculty.getRoles()) {
			
			if(tempFacultyRole.getRole().equals(revokeRole) ) {
				deleteRole = tempFacultyRole;
			}
		}
		
		facultyDao.deleteRole(deleteRole);
	}

	
	@Override
	@Transactional
	public void deleteAllStudentTokens(Student tempStudent) {
		
		studentDao.deleteAllTokens(tempStudent);
		
	}

	@Override
	@Transactional
	public void sendPasswordToken(TempPasswordModel thePasswordModel, HttpServletRequest request) {
		
		String forgetType = thePasswordModel.getRole();
		
		if(forgetType.equals("student")) {
			
			StudentVerificationToken tempToken = new StudentVerificationToken();
			
			Student tempStudent = studentDao.findStudentByEmail(thePasswordModel.getEmail().toLowerCase());
			
			tempStudent.addVerificationToken(tempToken);
			
			studentDao.registerStudent(tempStudent);
			
			String url = linkRedirect+request.getContextPath()+"/password/studentConfirmation";
			
			String token = tempStudent.getTokens().get(tempStudent.getTokens().size()-1).getToken();
			
			String mailContent = "<p>Dear "+tempStudent.getFirstName()+" To Change Your Password, please click here :</p>"+
								 "<p>Link Expires in 30 minutes </P"+
								 "<h3><a href="+url+"?token="+token+">CHANGE PASSWORD</a></h3>"+
								 "<p>Thank You</p>";
			
			emailService.sendEmail(tempStudent.getEmail(), mailContent, sender);
			
		
			
			
		}else if(forgetType.equals("faculty") ) {
			
			FacultyVerificationToken tempToken = new FacultyVerificationToken();
			
			Faculty tempFaculty = facultyDao.findFacultyByEmail(thePasswordModel.getEmail().toLowerCase());
			
			tempFaculty.addVerificationToken(tempToken);
			
			facultyDao.registerFaculty(tempFaculty);
			
			String url = linkRedirect+request.getContextPath()+"/password/facultyConfirmation";
			
			String token = tempFaculty.getTokens().get(tempFaculty.getTokens().size()-1).getToken();
			
			String mailContent = "<p>Dear "+tempFaculty.getFirstName()+" To Change Your Password, please click here :</p>"+
								 "<p>Link Expires in 1 month </P"+
								 "<h3><a href="+url+"?token="+token+">CHANGE PASSWORD</a></h3>"+
								 "<p>Thank You</p>";
			
			emailService.sendEmail(tempFaculty.getEmail(), mailContent, sender);
			
			
			
			
			
		}
		
	}

	@Override
	@Transactional
	public void savePassword(TempFormPasswordModel passwordModel, String email, String changeType) {
		
		if(changeType.equals("student")) {
		
			Student theStudent  = studentDao.findStudentByEmail(email);
			theStudent.setPassword(bCryptPasswordEncoder.encode(passwordModel.getPassword()));
			studentDao.registerStudent(theStudent);
			studentDao.deleteAllTokens(theStudent);
			
		}else if(changeType.equals("faculty") ) {
			Faculty theFaculty = facultyDao.findFacultyByEmail(email);
			theFaculty.setPassword(bCryptPasswordEncoder.encode(passwordModel.getPassword()));
			facultyDao.registerFaculty(theFaculty);
			facultyDao.deleteAllTokens(theFaculty);
	}


	}
	// new student email token sending to users mail
	@Override
	@Transactional
	public void newEmailToken(String email, String account, HttpServletRequest request) {
		
		if(account.equals("student")) {
		
		StudentVerificationToken tempToken = new StudentVerificationToken();
		
		Student tempStudent = studentDao.findStudentByEmail(email);
		
		tempStudent.addVerificationToken(tempToken);
		
		studentDao.registerStudent(tempStudent);
		
		//sending mail to verify email
		String url = linkRedirect+request.getContextPath()+"/start/studentConfirmation";
		String renewUrl = linkRedirect+request.getContextPath()+"/start/renewEmail";
		String token = tempStudent.getTokens().get(tempStudent.getTokens().size()-1).getToken();
		
		String mailContent = "<p>Dear "+tempStudent.getFirstName()+" Please confirm your account, please click here :</p>"+
							 "<p>Link Expires in 30 minutes </P"+
							 "<h3><a href="+url+"?token="+token+">VERIFY ACCOUNT</a></h3>"+
							 "<p>If link expires please click here, </p>"+
							 "<p><a href="+renewUrl+"?email="+tempStudent.getEmail()+"&account=student>Renew</a></p>"+
							 "<p>Thank You</p>";
		
		emailService.sendEmail(tempStudent.getEmail(), mailContent, sender);
		
		}else if(account.equals("faculty")) {
			
			
			FacultyVerificationToken tempToken = new FacultyVerificationToken();
			
			Faculty tempFaculty = facultyDao.findFacultyByEmail(email);
	
			tempFaculty.addVerificationToken(tempToken);
			
			facultyDao.registerFaculty(tempFaculty);
			
			//sending mail to verify email
			String url = linkRedirect+request.getContextPath()+"/start/facultyConfirmation";
			String renewUrl = linkRedirect+request.getContextPath()+"/start/renewEmail";
			String token = tempFaculty.getTokens().get(tempFaculty.getTokens().size()-1).getToken();
			
			String mailContent = "<p>Dear "+tempFaculty.getFirstName()+" Please confirm your account, please click here :</p>"+
								 "<p> Link Expires in 1 month </P"+
								 "<h3><a href="+url+"?token="+token+">VERIFY ACCOUNT</a></h3>"+
								 "<p>If link expires please click here, </p>"+
								 "<p><a href="+renewUrl+"?email="+tempFaculty.getEmail()+"&account=faculty>Renew</a></p>"+
								 "<p>Thank You</p>";
			
			emailService.sendEmail(tempFaculty.getEmail(), mailContent, sender);

			
		}
		
	}
	
	@Override
	@Transactional
	public StudentSemData loadStudentOneSemData(String email, int semester) {
		
		return studentDao.findStudentOneSemData(email, semester);
	}

	@Override
	@Transactional
	public List<StudentSemData> loadStudentSemData(String currentEmail) {
		
	
		return studentDao.findStudentSemData(currentEmail);
	}
	
	@Override
	@Transactional
	public List<StudentSemData> loadStudentSemDataByFilter(@Valid StudentFilterModel filterModel) {
		
		return studentDao.findStudentSemData(filterModel);
	}
	
	@Override
	@Transactional
	public List<StudentSemData> loadStudentSemDataByLearner(@Valid SortedSemDataModel semData) {
		// TODO Auto-generated method stub
		return studentDao.findStudentSemData(semData);
	}

	@Override
	@Transactional
	public void uploadMarks(StudentSemData tempSemData, String email) {
		
		//loading student
		Student tempStudent = studentDao.findStudentByEmail(email);
		
		//adding sem Data
		tempStudent.addSemData(tempSemData);
		
		//updating student
		studentDao.registerStudent(tempStudent);
		
	}

	@Override
	@Transactional
	public PercentageController loadPercentageControllerByDept(String dept) {
		
		return generalDao.getPercentageController(dept);
	}
	
	@Override
	@Transactional
	public void addSubjectList(SubjectsList subject) {
		
		generalDao.addSubjectList(subject);
	}
	
	@Override
	@Transactional
	public void deleteSubject(SubjectsList oneSubject) {
		
		generalDao.deleteSubject(oneSubject);
	}
	
	@Override
	@Transactional
	public SubjectsList loadOneSubjectList(int subjectId) {
		
		return generalDao.findOneSubjectList(subjectId);
	}



	@Override
	@Transactional
	public List<SubjectsList> loadSubjectList(int studentBatch, int studentSemester, String studentDept,
			String email) {
		
		return generalDao.findSubjectList(studentBatch, studentSemester, studentDept, email);
	}
	
	@Override
	@Transactional
	public List<SubjectsList> loadSubjectList(int batch, int semester, String dept) {
		
		return generalDao.findSubjectList(batch, semester, dept);
	}

	@Override
	@Transactional
	public StudentPerSubject loadPerSubjectData(String studentEmail, int studentSemester, String subjectName) {
		
		return studentDao.findPerSubjectData(studentEmail, studentSemester, subjectName);
	}
	
	@Override
	@Transactional
	public List<StudentPerSubject> loadPerSubjectDataList(String email, int semester) {
		
		return studentDao.findPerSubjectDataList(email, semester);
	}

	@Override
	@Transactional
	public void saveOrUpdateSubjectData(StudentPerSubject perSubject, String email) {
		
		// loading current student
		Student currentStudent = studentDao.findStudentByEmail(email);
		
		// loading object
		StudentPerSubject tempSubject = new StudentPerSubject();
		// instantiating subject data
		tempSubject = perSubject;
		
		//adding it to student
		currentStudent.addStudentSubjectData(tempSubject);
		
		studentDao.registerStudent(currentStudent);
	}

	@Override
	@Transactional
	public Student deleteStudentAllSemData(String studentEmail, int studentSemester) {
		
		Student currentStudent = new Student();
		
		List<StudentSemData> semData = new ArrayList<StudentSemData>();
		
		StudentSemData deleteSem = new StudentSemData();
		
		currentStudent = studentDao.findStudentByEmail(studentEmail);
		
		semData = currentStudent.getSemData();
		
		for(StudentSemData tempData: semData) {
			
			if(tempData.getSemester() == studentSemester) {
				deleteSem = tempData;
			}
		
		}
		
		studentDao.deleteSemester(deleteSem);
		
		return currentStudent;
	}

	@Override
	@Transactional
	public void saveOrPercentageController(PercentageController pController) {
		
		generalDao.saveOrPercentageController(pController);
		
	}

	@Override
	@Transactional
	public void perSubjectAssessmentDelete(int batch, int semester, String dept, String subjectName) {
		
		studentDao.perSubjectAssessmentDelete( batch, semester, dept, subjectName);
		
	}
	
	@Override
	@Transactional
	public List<MentorAllotStudents> loadAllotStudentsToMentors(Faculty currentFaculty) {
		
		Faculty currentFacultyMentor = new Faculty();
		
		currentFacultyMentor = facultyDao.findFacultyByEmail(currentFaculty.getEmail());
		
		List<MentorAllotStudents> allotStudent = new ArrayList<MentorAllotStudents>();
		
		allotStudent = currentFacultyMentor.getAllotStudent();
		
		for(MentorAllotStudents tempAllotedStudent : allotStudent) {
			
		}
		return allotStudent;
	}

	@Override
	@Transactional
	public void saveAllotStudentsToMentors(@Valid AllotStudentToMentorModel allotStudentModel) {
		
		Faculty currentFacultyMentor = new Faculty();
		
		currentFacultyMentor = facultyDao.findFacultyByEmail(allotStudentModel.getEmail());
		
		MentorAllotStudents allotStudent = new MentorAllotStudents();
		
		allotStudent.setSemester(Integer.parseInt( allotStudentModel.getSemester() ));
		allotStudent.setBatch(Integer.parseInt( allotStudentModel.getBatch() ));
		allotStudent.setDept(currentFacultyMentor.getDept());
		allotStudent.setLearnerType(allotStudentModel.getLearner());
		
		currentFacultyMentor.allotStudentsToMentor(allotStudent);
		
		
	}

	@Override
	@Transactional
	public void deleteAllotedStudentsByEmail(String email) {
		
		Faculty currentFaculty = new Faculty();
		
		currentFaculty = facultyDao.findFacultyByEmail(email);
		
		List<MentorAllotStudents> allotedStudents = new ArrayList<MentorAllotStudents>();
		
		
		allotedStudents = currentFaculty.getAllotStudent();
		
		if( !(allotedStudents.isEmpty()) ) {
			
			facultyDao.deleteAllotedStudents(allotedStudents);
		}
		
	}

	@Override
	@Transactional
	public List<Notification> loadNotificationByDept(String dept) {
		
		
		return generalDao.findNotificationByDept(dept);
	}
	
	@Override
	@Transactional
	public List<Notification> loadNotificationByBatchAndDept(int batch, String dept) {
		
		
		return generalDao.findNotificationByBatchAndDept( batch, dept);
	}


	@Override
	@Transactional
	public void uploadNotification(Notification postNotification) {
		
		generalDao.uploadNotification( postNotification);
		
	}


	@Override
	@Transactional
	public void deleteLastNotificationByDeptAndId(int id, String dept) {
		
		generalDao.deleteLastNotificationByDept(id, dept);
		
	}

	@Override
	@Transactional
	public List<MessageCenter> loadInboxByEmail(String email) {
		
		return generalDao.findInboxByEmail(email);
	}

	@Override
	@Transactional
	public List<MessageCenter> loadOutboxByEmail(String email) {
		
		return generalDao.findOutboxByEmail(email);
	}

	@Override
	@Transactional
	public void saveOrUpdateMessage(MessageCenter newMessage) {
		
		generalDao.registerMessage(newMessage);
		
	}

	

	
	



}
