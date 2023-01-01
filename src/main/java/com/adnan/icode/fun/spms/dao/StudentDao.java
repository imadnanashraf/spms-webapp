package com.adnan.icode.fun.spms.dao;

import java.util.List;

import javax.validation.Valid;

import com.adnan.icode.fun.spms.entity.Student;
import com.adnan.icode.fun.spms.entity.StudentPerSubject;
import com.adnan.icode.fun.spms.entity.StudentSemData;
import com.adnan.icode.fun.spms.entity.StudentVerificationToken;
import com.adnan.icode.fun.spms.models.PerSubjectModel;
import com.adnan.icode.fun.spms.models.SortedSemDataModel;
import com.adnan.icode.fun.spms.models.StudentFilterModel;

public interface StudentDao {
	
	public void registerStudent(Student tempStudent);

	public Student findStudentByEmail(String email);

	public StudentVerificationToken loadStudentToken(String token);

	public void deleteAllTokens(Student tempStudent);

	public List<StudentSemData> findStudentSemData(String currentEmail);

	public List<StudentSemData> findStudentSemData(@Valid StudentFilterModel filterModel);

	public Student findStudentByEnroll(long enroll);

	public StudentPerSubject findPerSubjectData(String studentEmail, int studentSemester, String subjectName);
	
	public List<StudentPerSubject> findPerSubjectDataList(String email, int semester);
	
	public void deleteSemester(StudentSemData deleteSem);

	public StudentSemData findStudentOneSemData(String email, int semester);

	public List<StudentSemData> findStudentSemData(@Valid SortedSemDataModel semData);

	public void perSubjectAssessmentDelete(int batch, int semester, String dept, String subjectName);

	

	


}
