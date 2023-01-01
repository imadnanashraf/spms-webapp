package com.adnan.icode.fun.spms.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.adnan.icode.fun.spms.entity.MessageCenter;
import com.adnan.icode.fun.spms.entity.Notification;
import com.adnan.icode.fun.spms.entity.PercentageController;
import com.adnan.icode.fun.spms.entity.SubjectsList;

@Repository
public class GeneralDaoImpl implements GeneralDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public PercentageController getPercentageController(String dept) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<PercentageController> theQuery = 
				currentSession.createQuery("from PercentageController where dept=:tempDept", PercentageController.class);
		
		theQuery.setParameter("tempDept", dept);
		
		PercentageController pController = null;
		
		try {
			
		pController = theQuery.getSingleResult();
		
		}catch(Exception e) {
			pController = null;
		}
		
		
		return pController;
	}
	
	@Override
	public void addSubjectList(SubjectsList subject) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.saveOrUpdate(subject);
		
	}
	
	@Override
	public void deleteSubject(SubjectsList oneSubject) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.delete(oneSubject);
		
	}
	
	@Override
	public SubjectsList findOneSubjectList(int subjectId) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		SubjectsList subject = new SubjectsList();
		
		subject = currentSession.get(SubjectsList.class, subjectId);
		
		return subject;
	}

	@Override
	public List<SubjectsList> findSubjectList(int studentBatch, int studentSemester, String studentDept,
			String email) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<SubjectsList> theQuery = 
						currentSession.createQuery
						("from SubjectsList where "
						+"batch=:tempBatch and "
						+"semester=:tempSemester and "
						+"facultyEmail=:tempFacultyEmail and "
						+"dept=:tempDept", SubjectsList.class);
		
		theQuery.setParameter("tempBatch", studentBatch);
		theQuery.setParameter("tempSemester", studentSemester);
		theQuery.setParameter("tempFacultyEmail", email);
		theQuery.setParameter("tempDept", studentDept);
		
		List<SubjectsList> subjects = new ArrayList<SubjectsList>();
		subjects = null;
		
		try {
			subjects = theQuery.getResultList();
		
		}catch(Exception e) {
			subjects = null;
		}
		
		return subjects;
	}

	@Override
	public List<SubjectsList> findSubjectList(int batch, int semester, String dept) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<SubjectsList> theQuery = 
						currentSession.createQuery
						("from SubjectsList where "
						+"batch=:tempBatch and "
						+"semester=:tempSemester and "
						+"dept=:tempDept", SubjectsList.class);
		
		theQuery.setParameter("tempBatch", batch);
		theQuery.setParameter("tempSemester", semester);
		theQuery.setParameter("tempDept", dept);
		
		List<SubjectsList> subjects = new ArrayList<SubjectsList>();
		subjects = null;
		
		try {
			subjects = theQuery.getResultList();
		
		}catch(Exception e) {
			subjects = null;
		}
		
		return subjects;
	
	}

	@Override
	public void saveOrPercentageController(PercentageController pController) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.saveOrUpdate(pController);
		
	}

	@Override
	public List<Notification> findNotificationByDept(String dept) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Notification> theQuery = currentSession.
									createQuery("from Notification where dept=:tempDept order by id Desc", Notification.class);
		
		theQuery.setParameter("tempDept", dept);
		
		List<Notification> notifications = null;
		
		try {
			
			notifications = theQuery.getResultList();
			
		}catch (Exception e) {
			
			 notifications = null;
			
		}
				

		
		return notifications;
	}
	
	@Override
	public List<Notification> findNotificationByBatchAndDept(int batch, String dept) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Notification> theQuery = currentSession.
									createQuery("from Notification where batch=:tempBatch and dept=:tempDept order by id Desc", Notification.class);
		
		theQuery.setParameter("tempBatch", batch);
		theQuery.setParameter("tempDept", dept);
		
		List<Notification> notifications = null;
		
		try {
			
			notifications = theQuery.getResultList();
			
		}catch (Exception e) {
			
			 notifications = null;
			
		}
				

		
		return notifications;
	}

	@Override
	public void uploadNotification(Notification postNotification) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.saveOrUpdate(postNotification);
		
	}

	@Override
	public void deleteLastNotificationByDept(int id, String dept) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		String query = "delete from Notification where id="+id+" and dept='"+dept+"'";
		
		System.out.println(query);
		
		currentSession.createQuery(query).executeUpdate();
	}

	@Override
	public List<MessageCenter> findInboxByEmail(String email) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<MessageCenter> theQuery = currentSession.
				createQuery("from MessageCenter where toEmail=:tempEmail order by id Desc", MessageCenter.class);

		theQuery.setParameter("tempEmail", email);
		
		List<MessageCenter> messages = null;
		
		try {
			
			messages = theQuery.getResultList();
			
		}catch (Exception e) {
			
			messages = null;
			
		}
		
		return messages;
	}

	@Override
	public List<MessageCenter> findOutboxByEmail(String email) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<MessageCenter> theQuery = currentSession.
				createQuery("from MessageCenter where fromEmail=:tempEmail order by id Desc", MessageCenter.class);

		theQuery.setParameter("tempEmail", email);
		
		List<MessageCenter> messages = null;
		
		try {
			
			messages = theQuery.getResultList();
			
		}catch (Exception e) {
			
			messages = null;
			
		}
		
		return messages;
	}

	@Override
	public void registerMessage(MessageCenter newMessage) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.saveOrUpdate(newMessage);
		
		
	}

	
	

	

}
