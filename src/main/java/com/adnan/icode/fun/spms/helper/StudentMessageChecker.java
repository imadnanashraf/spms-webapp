package com.adnan.icode.fun.spms.helper;

import java.util.List;

import com.adnan.icode.fun.spms.entity.MessageCenter;
import com.adnan.icode.fun.spms.entity.ReadStudentMessage;
import com.adnan.icode.fun.spms.entity.Student;
import com.adnan.icode.fun.spms.service.SpmsService;

public class StudentMessageChecker {
	
	private Student currentStudent;
	
	private SpmsService spmsService;
	
	public StudentMessageChecker(Student currentStudent, SpmsService spmsService) {
		
		this.currentStudent = currentStudent;
		this.spmsService = spmsService;
	}
	
	public boolean check() {
		
		ReadStudentMessage newReadMessage = new ReadStudentMessage();
		
		int latestMessageId = 0;
		int readMessageId = 0;
		
		if(currentStudent.getReadStudentMessage() != null) {
			
		newReadMessage = currentStudent.getReadStudentMessage();
		
		readMessageId = newReadMessage.getMessageId();
		
		}
		
		List<MessageCenter> allInboxMessage = spmsService.loadInboxByEmail(currentStudent.getEmail());
		
		if( !(allInboxMessage.isEmpty()) ) {
			
			latestMessageId = allInboxMessage.get(0).getId();
		}
		
		if(readMessageId != latestMessageId) {
			
			return true;
			
		}else {
			return false;
		}
		
		
	}

}
