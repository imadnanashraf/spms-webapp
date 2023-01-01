package com.adnan.icode.fun.spms.helper;

import java.util.List;

import com.adnan.icode.fun.spms.entity.Faculty;
import com.adnan.icode.fun.spms.entity.MessageCenter;
import com.adnan.icode.fun.spms.entity.ReadFacultyMessage;
import com.adnan.icode.fun.spms.entity.ReadStudentMessage;
import com.adnan.icode.fun.spms.entity.Student;
import com.adnan.icode.fun.spms.service.SpmsService;

public class MentorMessageChecker {
	
	private Faculty currentFaculty;
	
	private SpmsService spmsService;
	
	public MentorMessageChecker(Faculty currentFaculty, SpmsService spmsService) {
		
		this.currentFaculty = currentFaculty;
		this.spmsService = spmsService;
	}
	
	public boolean check() {
		
		ReadFacultyMessage newReadMessage = new ReadFacultyMessage();
		
		int latestMessageId = 0;
		int readMessageId = 0;
		
		if(currentFaculty.getReadFacultyMessage() != null) {
			
		newReadMessage = currentFaculty.getReadFacultyMessage();
		
		readMessageId = newReadMessage.getMessageId();
		
		}
		
		List<MessageCenter> allInboxMessage = spmsService.loadInboxByEmail(currentFaculty.getEmail());
		
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
