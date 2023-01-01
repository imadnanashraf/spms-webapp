package com.adnan.icode.fun.spms.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.adnan.icode.fun.spms.entity.Faculty;
import com.adnan.icode.fun.spms.entity.FacultyVerificationToken;
import com.adnan.icode.fun.spms.entity.MentorAllotStudents;
import com.adnan.icode.fun.spms.entity.MessageCenter;
import com.adnan.icode.fun.spms.entity.Notification;
import com.adnan.icode.fun.spms.entity.PercentageController;
import com.adnan.icode.fun.spms.entity.Student;
import com.adnan.icode.fun.spms.entity.StudentPerSubject;
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

public interface SpmsService {
	
	public void registrationNewUser(TempRegisterModel tempModel, HttpServletRequest request);
	
	public Student loadStudentByEmail(String email);
	
	public Faculty loadFacultyByEmail(String email);

	public StudentVerificationToken findStudentToken(String token);

	public void updateStudent(Student tempStudent);

	public FacultyVerificationToken findFacultyToken(String token);

	public void deleteAllFacultyTokens(Faculty tempFaculty);
	
	public void deleteAllStudentTokens(Student tempStudent);

	public void sendPasswordToken(TempPasswordModel thePasswordModel, HttpServletRequest request);

	public void savePassword(TempFormPasswordModel passwordModel, String email, String changeType);

	public void newEmailToken(String email, String account,HttpServletRequest request);

	public List<StudentSemData> loadStudentSemData(String currentEmail);

	public void uploadMarks(StudentSemData tempSemData, String email);

	public PercentageController loadPercentageControllerByDept(String dept);

	public void updateFaculty(Faculty currentFaculty);

	public List<StudentSemData> loadStudentSemDataByFilter(@Valid StudentFilterModel filterModel);

	public Student loadStudentByEnroll(long enroll);

	public List<SubjectsList> loadSubjectList(int studentBatch, int studentSemester, String studentDept,
			String email);

	public StudentPerSubject loadPerSubjectData(String studentEmail, int studentSemester, String subjectName);

	public void saveOrUpdateSubjectData(StudentPerSubject perSubject, String email);

	public Student deleteStudentAllSemData(String studentEmail, int studentSemester);

	public List<SubjectsList> loadSubjectList(int batch, int semester, String dept);

	public List<StudentPerSubject> loadPerSubjectDataList(String email, int semester);

	public StudentSemData loadStudentOneSemData(String email, int semester);

	public List<StudentSemData> loadStudentSemDataByLearner(@Valid SortedSemDataModel semData);

	public List<Faculty> loadFacultyByDept(String dept);

	public List<Faculty> loadFacultyByDeptWithRoles(String dept);

	public void deleteFacultyRole(String email, String revokeRole);

	public void addFacultyRole(String email, String permitRole);

	public void saveOrPercentageController(PercentageController pController);

	public void addSubjectList(SubjectsList subject);

	public SubjectsList loadOneSubjectList(int subjectId);

	public void deleteSubject(SubjectsList oneSubject);

	public void perSubjectAssessmentDelete(int batch, int semester, String dept, String subjectName);

	public Faculty loadFacultyByEmailWithRoles(String facultyEmail);

	public Faculty loadFacultyByEmailWithStudents(String facultyEmail);

	public void saveAllotStudentsToMentors(@Valid AllotStudentToMentorModel allotStudentModel);

	public void deleteAllotedStudentsByEmail(String email);

	public List<Notification> loadNotificationByDept(String dept);

	public void uploadNotification(Notification postNotification);

	public void deleteLastNotificationByDeptAndId(int id, String dept);

	public List<Notification> loadNotificationByBatchAndDept(int batch, String dept);

	public List<MessageCenter> loadInboxByEmail(String email);

	public List<MessageCenter> loadOutboxByEmail(String email);

	public List<MentorAllotStudents> loadAllotStudentsToMentors(Faculty currentFaculty);

	public void saveOrUpdateMessage(MessageCenter newMessage);


	

}
