package com.adnan.icode.fun.spms.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.adnan.icode.fun.spms.entity.Faculty;
import com.adnan.icode.fun.spms.entity.FacultyRole;
import com.adnan.icode.fun.spms.entity.FacultyVerificationToken;
import com.adnan.icode.fun.spms.entity.MentorAllotStudents;
import com.adnan.icode.fun.spms.entity.FacultyVerificationToken;


@Repository
public class FacultyDaoImpl implements FacultyDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void registerFaculty(Faculty tempFaculty) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.saveOrUpdate(tempFaculty);
		

	}

	@Override
	public Faculty findFacultyByEmail(String email) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Faculty> theQuery = 
					currentSession.createQuery("from Faculty where email=:tempEmail", Faculty.class);
		theQuery.setParameter("tempEmail", email);
		Faculty theFaculty = null;
		
		try {
			theFaculty = theQuery.getSingleResult();
			System.out.println(theFaculty.getFirstName());
			
		}catch (Exception e) {
			theFaculty = null;
		}
		
		
		return theFaculty;
	}
	
	@Override
	public List<Faculty> findFacultyByDept(String dept) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Faculty> theQuery = 
					currentSession.createQuery("from Faculty where dept=:tempDept", Faculty.class);
		
		theQuery.setParameter("tempDept", dept);
		List<Faculty> theFaculty = null;
		
		try {
			theFaculty = theQuery.getResultList();
			
		}catch (Exception e) {
			theFaculty = null;
		}
		
		
		return theFaculty;
	}
	
	@Override
	public Faculty findFacultyByEmailWithStudents(String facultyEmail) {
		
		Session currentSession = sessionFactory.getCurrentSession();

		
		Query<Faculty> theQuery = currentSession.createQuery("from Faculty tokens where email=:tempEmail",Faculty.class);
		
		theQuery.setParameter("tempEmail", facultyEmail);
		Faculty theFaculty = null;
		
		try {
			theFaculty = theQuery.getSingleResult();
			
		}catch (Exception e) {
			theFaculty = null;
		}
		

		if(theFaculty != null) {
			
			for(MentorAllotStudents allotedStudents:theFaculty.getAllotStudent() ) {
						
			}
			
		}
			
		
		return theFaculty;
	}
	
	@Override
	public Faculty findFacultyByEmailWithRoles(String facultyEmail) {
		
		Session currentSession = sessionFactory.getCurrentSession();

		
		Query<Faculty> theQuery = currentSession.createQuery("from Faculty tokens where email=:tempEmail",Faculty.class);
		
		theQuery.setParameter("tempEmail", facultyEmail);
		Faculty theFaculty = null;
		
		try {
			theFaculty = theQuery.getSingleResult();
			
		}catch (Exception e) {
			theFaculty = null;
		}
		

		if(theFaculty != null) {
			
			for(FacultyRole tempRole:theFaculty.getRoles() ) {
						
			}
			
		}
			
		
		return theFaculty;
	}
	
	@Override
	public List<Faculty> findFacultyByDeptWithRoles(String dept) {
	    
		Session currentSession = sessionFactory.getCurrentSession();

		
		Query<Faculty> theQuery = currentSession.createQuery("from Faculty tokens where dept=:tempDept",Faculty.class);
		
		theQuery.setParameter("tempDept", dept);
		List<Faculty> theFaculty = null;
		
		try {
			theFaculty = theQuery.getResultList();
			
		}catch (Exception e) {
			theFaculty = null;
		}
		
		List<Faculty> newVerifiedFaculty = new ArrayList<Faculty>();
		List<Faculty> newTempFaculty = new ArrayList<Faculty>();
		
		if(theFaculty != null) {
			
			// adding only those to list with zero verification tokens
			for(Faculty tempFaculty: theFaculty) {	
				
				if(tempFaculty.getTokens().isEmpty()) {
					newVerifiedFaculty.add(tempFaculty);
				}	
			}
			
			// revisiting roles to load roles in verified faculty
			for(Faculty tempFaculty: newVerifiedFaculty) {	
				
				for(FacultyRole tempRole:tempFaculty.getRoles()) {
						
				}
				
				newTempFaculty.add(tempFaculty);
				
			}
			
			
		}
		
		
		return newTempFaculty;
	}

	@Override
	public FacultyVerificationToken loadFacultytoken(String token) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<FacultyVerificationToken> theQuery = 
				currentSession.createQuery("from FacultyVerificationToken where token=:tempToken",
											FacultyVerificationToken.class);
		theQuery.setParameter("tempToken", token);

		FacultyVerificationToken tempStudentToken = null;
		try {
		tempStudentToken = theQuery.getSingleResult();
		}catch (Exception e) {
			
			tempStudentToken = null;
		}
		
		
		return tempStudentToken;
	}

	@Override
	public void deleteAllTokens(Faculty tempFaculty) {
		Session currentSession = sessionFactory.getCurrentSession();
	
		
		Query<Faculty> theQuery = currentSession.
				createQuery("select i from Faculty i "
						  + "JOIN FETCH i.tokens "
						  + "where i.id=:tempId", Faculty.class);
		
		theQuery.setParameter("tempId", tempFaculty.getId());
		
		Faculty fetchedFacultyWithTokens = null;
			
		try {
			fetchedFacultyWithTokens = theQuery.getSingleResult();
		}catch (Exception e) {
			fetchedFacultyWithTokens = null;
		}
		
		if(fetchedFacultyWithTokens != null) {
			List<FacultyVerificationToken> tokens = fetchedFacultyWithTokens.getTokens();
			
			for(FacultyVerificationToken tempToken: tokens) {
				
				currentSession.detach(tempToken);
				currentSession.delete(tempToken);
			}
		}
		
	}

	@Override
	public void deleteRole(FacultyRole deleteRole) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.detach(deleteRole);
		currentSession.delete(deleteRole);
		
	}

	@Override
	public void deleteAllotedStudents(List<MentorAllotStudents> allotedStudents) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		for(MentorAllotStudents tempAllotedStudents : allotedStudents) {
			
			currentSession.detach(tempAllotedStudents);
			currentSession.delete(tempAllotedStudents);
		}
		
	}

	




}
