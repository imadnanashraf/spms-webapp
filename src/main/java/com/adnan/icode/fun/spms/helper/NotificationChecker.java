package com.adnan.icode.fun.spms.helper;

import java.util.List;

import com.adnan.icode.fun.spms.entity.Notification;
import com.adnan.icode.fun.spms.entity.ReadNotification;
import com.adnan.icode.fun.spms.entity.Student;
import com.adnan.icode.fun.spms.service.SpmsService;

public class NotificationChecker {
	
	private Student currentStudent;
	
	private SpmsService spmsService;
	
	public NotificationChecker(Student currentStudent, SpmsService spmsService) {
		
		this.currentStudent = currentStudent;
		this.spmsService = spmsService;
	}
	
	public boolean check() {
		
		ReadNotification newReadNotification = new ReadNotification();
		
		int latestNotificationId = 0;
		int readNotificationId = 0;
		
		if(currentStudent.getReadNotification() != null) {
			
		newReadNotification = currentStudent.getReadNotification();
		
		readNotificationId = newReadNotification.getNotification_id();
		
		}
		
		List<Notification> allNotifications = spmsService.loadNotificationByBatchAndDept(currentStudent.getBatch(), currentStudent.getDept());
		
		if( !(allNotifications.isEmpty()) ) {
			
			latestNotificationId = allNotifications.get(0).getId();
		}
		
		if(readNotificationId != latestNotificationId) {
			
			return true;
			
		}else {
			return false;
		}
		
		
	}

}
