package com.adnan.icode.fun.spms.dao;

import java.util.List;

import com.adnan.icode.fun.spms.entity.MessageCenter;
import com.adnan.icode.fun.spms.entity.Notification;
import com.adnan.icode.fun.spms.entity.PercentageController;
import com.adnan.icode.fun.spms.entity.SubjectsList;

public interface GeneralDao {

	PercentageController getPercentageController(String dept);

	List<SubjectsList> findSubjectList(int studentBatch, int studentSemester, String studentDept, String email);

	List<SubjectsList> findSubjectList(int batch, int semester, String dept);

	void saveOrPercentageController(PercentageController pController);

	void addSubjectList(SubjectsList subject);

	SubjectsList findOneSubjectList(int subjectId);

	void deleteSubject(SubjectsList oneSubject);

	List<Notification> findNotificationByDept(String dept);

	void uploadNotification(Notification postNotification);

	void deleteLastNotificationByDept(int id, String dept);

	List<Notification> findNotificationByBatchAndDept(int batch, String dept);

	List<MessageCenter> findInboxByEmail(String email);

	List<MessageCenter> findOutboxByEmail(String email);

	void registerMessage(MessageCenter newMessage);



}
