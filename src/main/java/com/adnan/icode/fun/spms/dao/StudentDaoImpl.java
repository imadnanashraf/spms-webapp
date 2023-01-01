package com.adnan.icode.fun.spms.dao;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.adnan.icode.fun.spms.entity.Faculty;
import com.adnan.icode.fun.spms.entity.FacultyVerificationToken;
import com.adnan.icode.fun.spms.entity.Student;
import com.adnan.icode.fun.spms.entity.StudentPerSubject;
import com.adnan.icode.fun.spms.entity.StudentSemData;
import com.adnan.icode.fun.spms.entity.StudentVerificationToken;
import com.adnan.icode.fun.spms.models.PerSubjectModel;
import com.adnan.icode.fun.spms.models.SortedSemDataModel;
import com.adnan.icode.fun.spms.models.StudentFilterModel;

@Repository
public class StudentDaoImpl implements StudentDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public void registerStudent(Student tempStudent) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.saveOrUpdate(tempStudent);
		
	}


	@Override
	public Student findStudentByEmail(String email) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Student> theQuery = 
				currentSession.createQuery("from Student where email=:tempEmail", Student.class);

		theQuery.setParameter("tempEmail", email);
		
		Student theStudent= null;
		
		try {
		theStudent = theQuery.getSingleResult();
		
		}catch (Exception e) {
			theStudent = null;
		}
		
		return theStudent;
	}
	
	@Override
	public Student findStudentByEnroll(long enroll) {

		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Student> theQuery = 
				currentSession.createQuery("from Student where universityEnroll=:enroll", Student.class);

		theQuery.setParameter("enroll", enroll);
		
		Student theStudent= null;
		
		try {
		theStudent = theQuery.getSingleResult();
		
		}catch (Exception e) {
			theStudent = null;
		}
		
		
		return theStudent;
	}



	@Override
	public StudentVerificationToken loadStudentToken(String token) {
		Session currentSession = sessionFactory.getCurrentSession();


		Query<StudentVerificationToken> theQuery = 
				currentSession.createQuery("from StudentVerificationToken where token=:tempToken",
											StudentVerificationToken.class);
		theQuery.setParameter("tempToken", token);

		StudentVerificationToken tempStudentToken = null;
		try {
		tempStudentToken = theQuery.getSingleResult();
		}catch (Exception e) {
			
			tempStudentToken = null;
		}
		
		return tempStudentToken;
	}


	@Override
	public void deleteAllTokens(Student tempStudent) {
		Session currentSession = sessionFactory.getCurrentSession();
	
		Query<Student> theQuery = currentSession.
				createQuery("select i from Student i "
						  + "JOIN FETCH i.tokens "
						  + "where i.id=:tempId", Student.class);
		
		theQuery.setParameter("tempId", tempStudent.getId());
		
		Student fetchedStudentWithTokens = null;
			
		try {
			fetchedStudentWithTokens = theQuery.getSingleResult();
		}catch (Exception e) {
			fetchedStudentWithTokens = null;
		}
		
		if(fetchedStudentWithTokens != null) {
			List<StudentVerificationToken> tokens = fetchedStudentWithTokens.getTokens();
			
			for(StudentVerificationToken tempToken: tokens) {
				
				currentSession.detach(tempToken);
				currentSession.delete(tempToken);
		}

		}
		
	}
	
	@Override
	public StudentSemData findStudentOneSemData(String email, int semester) {
		Session currentSession =  sessionFactory.getCurrentSession();
		
		Query<StudentSemData> theQuery = currentSession.createQuery("select i from StudentSemData i "
															+"JOIN FETCH i.student where "
															+"i.semester=:tempSemester and email=:tempEmail",StudentSemData.class);
		
		theQuery.setParameter("tempEmail", email);
		theQuery.setParameter("tempSemester", semester);
		
		StudentSemData semData = null;
		
		try {
			semData = theQuery.getSingleResult();
		}catch (Exception exc) {
			
			semData = null;
		}
		
		
		return semData;
	}


	@Override
	public List<StudentSemData> findStudentSemData(String currentEmail) {
		
		Session currentSession =  sessionFactory.getCurrentSession();
		
		Query<Student> theQuery = currentSession.createQuery("select i from Student i "
															+"JOIN FETCH i.semData where "
															+"i.email=:tempEmail order by semester",Student.class);
		
		theQuery.setParameter("tempEmail", currentEmail);
		
		Student theStudent= null;
		List<StudentSemData> semData = null;
		
		try {
		theStudent = theQuery.getSingleResult();
		}catch (Exception exc) {
			
			theStudent = null;
		}
		
		if(theStudent != null) {
			try {
				semData = theStudent.getSemData();
			}catch(NullPointerException exc) {
				System.out.println("Empty Marks List");
			}
		}
		
		
		
		return semData;
	}


	@Override
	public List<StudentSemData> findStudentSemData(@Valid StudentFilterModel filterModel) {

// 		one way to do the same but in for loop for every students semdata hibernate query is executed
//		Session currentSession = sessionFactory.getCurrentSession();
//		
//		Query<Student> theQuery = 
//				currentSession.createQuery("from Student where dept=:tempDept and batch=:tempBatch order by universityEnroll", Student.class);
//				
//		theQuery.setParameter("tempDept", filterModel.getDept());
//		theQuery.setParameter("tempBatch", Integer.parseInt(filterModel.getBatch()));
//		
//		
//		List<Student> theStudent = null;
//		List<StudentSemData> semData = new ArrayList<StudentSemData>();
//		
//		
//		try {
//		
//			theStudent = theQuery.getResultList();
//		
//		}catch(Exception e) {
//			
//			theStudent = null;
//		}
//		
//		if(theStudent != null) {
//			
//			for(Student tempStudent : theStudent) {
//				
//				if(tempStudent.getSemData() != null) {
//					
//					for(StudentSemData tempSemData : tempStudent.getSemData()) {
//						
//						if(tempSemData.getSemester() == Integer.parseInt(filterModel.getSemester())) {
//							semData.add(tempSemData);
//						}
//					}
//					
//				}
//			}
//			
//		}
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Student> theQuery = 
				currentSession.createQuery("select i from Student i JOIN FETCH i.semData "
										  +"where i.dept=:tempDept and i.batch=:tempBatch "
										  +"and semester=:tempSem order by i.universityEnroll", Student.class);
				
		theQuery.setParameter("tempDept", filterModel.getDept());
		theQuery.setParameter("tempBatch", Integer.parseInt(filterModel.getBatch()));
		theQuery.setParameter("tempSem", Integer.parseInt(filterModel.getSemester()));
		
		List<Student> theStudent = null;
		List<StudentSemData> semData = new ArrayList<StudentSemData>();
		
		
		try {
		
			theStudent = theQuery.getResultList();
		
		}catch(Exception e) {
			
			theStudent = null;
		}
		
		if(theStudent != null) {
			for(Student tempStudent : theStudent) {
				
				for(StudentSemData tempSemData : tempStudent.getSemData()) {
					
						semData.add(tempSemData);
					}
				
			}
		}
		
		
		return semData;
	}
	
	@Override
	public List<StudentSemData> findStudentSemData(@Valid SortedSemDataModel semData) {

		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Student> theQuery = null;
		List<Student> ratedStudents = null;
		List<Student> signedInStudents = null;
		List<Student> theStudent = null;
		List<StudentSemData> semDataList = new ArrayList<StudentSemData>();
		
		// IF CHECKING FOR NORMAL RATED STUDENT
		if( (semData.getLearner().equals("FAST")) || (semData.getLearner().equals("SLOW"))) {
		
			
		theQuery = 
				currentSession.createQuery("select i from Student i JOIN FETCH i.semData "
										  +"where i.dept=:tempDept and i.batch=:tempBatch "
										  +"and semester=:tempSem and learner_type=:tempLearner "
										  +"order by i.universityEnroll", Student.class);
		
		theQuery.setParameter("tempDept", semData.getDept());
		theQuery.setParameter("tempBatch", Integer.parseInt(semData.getBatch()) );
		theQuery.setParameter("tempSem", Integer.parseInt(semData.getSemester()) );
		theQuery.setParameter("tempLearner", semData.getLearner());
		
		// checking for not completely assessed students
		}else if( semData.getLearner().equals("NOASSESS") ) {
			
		theQuery = 
				currentSession.createQuery("select i from Student i JOIN FETCH i.semData "
											  +"where i.dept=:tempDept and i.batch=:tempBatch "
											  +"and semester=:tempSem and learner_type=NULL "
											  +"order by i.universityEnroll", Student.class);
		
		theQuery.setParameter("tempDept", semData.getDept());
		theQuery.setParameter("tempBatch", Integer.parseInt(semData.getBatch()) );
		theQuery.setParameter("tempSem", Integer.parseInt(semData.getSemester()) );
		
		//checking for students that has signed in but have'nt uploaded the marks
		}else if( semData.getLearner().equals("MARKS") ) {
			
		Query<Student> ratedStudentQuery = 
				currentSession.createQuery("select i from Student i JOIN FETCH i.semData "
											  +"where i.dept=:tempDept and i.batch=:tempBatch "
											  +"and semester=:tempSem "
											  +"order by i.universityEnroll", Student.class);
			
		ratedStudentQuery.setParameter("tempDept", semData.getDept());
		ratedStudentQuery.setParameter("tempBatch", Integer.parseInt(semData.getBatch()) );
		ratedStudentQuery.setParameter("tempSem", Integer.parseInt(semData.getSemester()) );
		
		try {
			ratedStudents = ratedStudentQuery.getResultList();
			
		}catch(Exception e) {
			ratedStudents = null;
		}
		
		
		Query<Student> signedInStudentQuery = 
				currentSession.createQuery("from Student where dept=:tempDept and batch=:tempBatch "
										   +"order by universityEnroll", Student.class);
			
		signedInStudentQuery.setParameter("tempDept", semData.getDept());
		signedInStudentQuery.setParameter("tempBatch", Integer.parseInt(semData.getBatch()) );
		

		try {
			signedInStudents = signedInStudentQuery.getResultList();
		}catch(Exception e) {
			signedInStudents = null;
		}
		
			
			
		}
		
		if( semData.getLearner().equals("MARKS") ) {
			
			
			
			if( !(ratedStudents.isEmpty()) ) {
				
				for(Student tempRatedStudent: ratedStudents) {
					
					if(signedInStudents != null) {
						List<Student> newTempStudents = new ArrayList<Student>();
					
						for(Student tempSignedInStudents :signedInStudents ) {
							System.out.println("rated"+tempRatedStudent.getEmail());
							System.out.println("all"+tempSignedInStudents.getEmail());
							
							if(  !( tempSignedInStudents.getEmail() == tempRatedStudent.getEmail() )  ) {
								
								newTempStudents.add(tempSignedInStudents);
								
							}
						}
						
						signedInStudents = newTempStudents;
						
					}
					
				}
				
				for(Student tempsignedInStudents :signedInStudents ) {
					
					StudentSemData tempSemData = new StudentSemData();
					tempSemData.setSemester(Integer.parseInt(semData.getSemester()));
					tempSemData.setStudent(tempsignedInStudents);
					
					semDataList.add(tempSemData);
				}
				
				
			}else if( !(signedInStudents.isEmpty()) ) {
				
				for(Student tempsignedInStudents :signedInStudents ) {
					
						StudentSemData tempSemData = new StudentSemData();
						tempSemData.setSemester(Integer.parseInt(semData.getSemester()));
						tempSemData.setStudent(tempsignedInStudents);
						
						semDataList.add(tempSemData);
				}
				
				
			}
			
			
		}else {
		
		
		
		try {
			
			theStudent = theQuery.getResultList();
			System.out.println("+==========+++=============+");
			System.out.println(theStudent.get(0).getEmail());
		}catch(Exception e) {
			
			theStudent = null;
		}
		
		if(theStudent != null) {
			for(Student tempStudent : theStudent) {
				
				for(StudentSemData tempSemData : tempStudent.getSemData()) {
					
					semDataList.add(tempSemData);
					}
				
			}
		}
		

		
		}
		
		// if everything is successfull
		return semDataList;
		
	}


	@Override
	public StudentPerSubject findPerSubjectData(String studentEmail, int studentSemester, String subjectName) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<StudentPerSubject> theQuery = 
					currentSession.
					createQuery("select i from StudentPerSubject i JOIN FETCH i.student "
							   +"where i.semester=:tempSemester and i.subjectName=:tempsubjectName "
							   +"and email=:tempEmail", StudentPerSubject.class);
		
		theQuery.setParameter("tempSemester", studentSemester);
		theQuery.setParameter("tempsubjectName", subjectName);
		theQuery.setParameter("tempEmail", studentEmail);
		
		StudentPerSubject perSubject =  null;
		
		try {
			
			perSubject = theQuery.getSingleResult();
			
		}catch(Exception e){
			
			perSubject = null;
		}
		
		return perSubject;
	}
	
	@Override
	public List<StudentPerSubject> findPerSubjectDataList(String email, int semester) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<StudentPerSubject> theQuery = 
					currentSession.
					createQuery("select i from StudentPerSubject i JOIN FETCH i.student "
							   +"where i.semester=:tempSemester "
							   +"and email=:tempEmail", StudentPerSubject.class);
		
		theQuery.setParameter("tempSemester", semester);
		theQuery.setParameter("tempEmail", email);
		
		List<StudentPerSubject> filledSubjectList =  null;
		
		try {
			
			filledSubjectList = theQuery.getResultList();
			
		}catch(Exception e){
			
			filledSubjectList = null;
		}
		
		return filledSubjectList;
	}


	@Override
	public void deleteSemester(StudentSemData deleteSem) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.detach(deleteSem);
		currentSession.delete(deleteSem);
		
	}


	@Override
	public void perSubjectAssessmentDelete(int batch, int semester, String dept, String subjectName) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<StudentPerSubject> theQuery = 
				currentSession.
				createQuery("select i from StudentPerSubject i JOIN FETCH i.student "
						   +"where i.semester=:tempSemester and i.subjectName=:tempSubjectName "
						   +"and batch=:tempBatch and dept=:tempDept", StudentPerSubject.class);
	
		theQuery.setParameter("tempSemester", semester);
		theQuery.setParameter("tempSubjectName", subjectName);
		theQuery.setParameter("tempBatch", batch);
		theQuery.setParameter("tempDept", dept);
		
		List<StudentPerSubject> studentPerSubjectList = null;
		
		try {
			studentPerSubjectList = theQuery.getResultList();
		}catch (Exception e) {
			studentPerSubjectList = null;
		}
		
		if( !(studentPerSubjectList.isEmpty()) ) {
		
			for(StudentPerSubject tempPerSubject: studentPerSubjectList) {
				
				currentSession.detach(tempPerSubject);
				currentSession.delete(tempPerSubject);
		}

		}
		
		
		
	}





	






}