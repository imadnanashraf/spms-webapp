package com.adnan.icode.fun.spms.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.adnan.icode.fun.spms.entity.Faculty;
import com.adnan.icode.fun.spms.entity.MentorAllotStudents;
import com.adnan.icode.fun.spms.entity.MessageCenter;
import com.adnan.icode.fun.spms.entity.ReadFacultyMessage;
import com.adnan.icode.fun.spms.entity.ReadStudentMessage;
import com.adnan.icode.fun.spms.entity.Student;
import com.adnan.icode.fun.spms.entity.StudentSemData;
import com.adnan.icode.fun.spms.helper.MentorMessageChecker;
import com.adnan.icode.fun.spms.models.AddSubjectModel;
import com.adnan.icode.fun.spms.models.AllotStudentToMentorModel;
import com.adnan.icode.fun.spms.models.ListSubjectModel;
import com.adnan.icode.fun.spms.models.SortedSemDataModel;
import com.adnan.icode.fun.spms.service.SpmsService;

@Controller
@RequestMapping("/faculty/mentor")
public class MentorController {
	
	@Autowired
	private SpmsService spmsService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor trimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, trimmerEditor);
	}
	
	@GetMapping("/inbox")
	public String inbox(Model theModel) {
		
		// if faculty is not coordinator
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (  auth != null &&   !( auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MENTOR"))  )   ) {
						    
			return "redirect:/faculty/facultyPage";
			}
						
		//basic if mentor
		Faculty currentFaculty = new Faculty();
						
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
							   
			String currentEmail = authentication.getName();
							
			currentFaculty = spmsService.loadFacultyByEmail(currentEmail);
							
		}
		
		
		
		List<MessageCenter> facultyInbox = new ArrayList<MessageCenter>();
		
		facultyInbox = spmsService.loadInboxByEmail(currentFaculty.getEmail());
		
		//==========================updating read message===========================
		
		if( !(facultyInbox.isEmpty()) ) {
			
			int messageId = facultyInbox.get(0).getId();
			
			ReadFacultyMessage currentlyReadMessage = new ReadFacultyMessage();
			
			if(currentFaculty.getReadFacultyMessage() != null) {
				
				currentlyReadMessage = currentFaculty.getReadFacultyMessage();
			
			}
			
			currentlyReadMessage.setMessageId(messageId);
			
			currentFaculty.addReadFacultytMessage(currentlyReadMessage);
			
			spmsService.updateFaculty(currentFaculty);
		}
		
		//==========================================================================

		
		theModel.addAttribute("currentFaculty", currentFaculty);
		theModel.addAttribute("facultyInbox", facultyInbox);
		
		return "faculty/mentor/inbox";
	}
	
	@GetMapping("/outbox")
	public String outbox(Model theModel) {
		
		// if faculty is not coordinator
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (  auth != null &&   !( auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MENTOR"))  )   ) {
						    
			return "redirect:/faculty/facultyPage";
			}
						
		//basic if mentor
		Faculty currentFaculty = new Faculty();
						
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
							   
			String currentEmail = authentication.getName();
							
			currentFaculty = spmsService.loadFacultyByEmail(currentEmail);
							
		}
		
		// for checking new message===============================================================
		// Will execute if Mentor==============================================================
		
			MentorMessageChecker messageChecker = new MentorMessageChecker(currentFaculty, spmsService);
			boolean newMessage = messageChecker.check();
					
			theModel.addAttribute("newMessage", newMessage);
		//=========================================================================================

		
		theModel.addAttribute("currentFaculty", currentFaculty);
		
		List<MessageCenter> facultyOutbox = new ArrayList<MessageCenter>();
		
		facultyOutbox = spmsService.loadOutboxByEmail(currentFaculty.getEmail());
		
		theModel.addAttribute("facultyOutbox", facultyOutbox);
		
		return "faculty/mentor/outbox";
	}
	
	@GetMapping("/newMessage")
	public String newMessage(Model theModel) {
		
		// if faculty is not coordinator
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (  auth != null &&   !( auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MENTOR"))  )   ) {
						    
			return "redirect:/faculty/facultyPage";
			}
						
		//basic if mentor
		Faculty currentFaculty = new Faculty();
						
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
							   
			String currentEmail = authentication.getName();
							
			currentFaculty = spmsService.loadFacultyByEmail(currentEmail);
							
		}
		
		// for checking new message===============================================================
		// Will execute if Mentor==============================================================
		
			MentorMessageChecker messageChecker = new MentorMessageChecker(currentFaculty, spmsService);
			boolean newMessage = messageChecker.check();
					
			theModel.addAttribute("newMessage", newMessage);
		//=========================================================================================

				
		List<MentorAllotStudents> allotedStudents = spmsService.loadAllotStudentsToMentors(currentFaculty);
		
		theModel.addAttribute("currentFaculty", currentFaculty);
		theModel.addAttribute("allotedStudents", allotedStudents);
		
		return "faculty/mentor/new-message";
		
		
	}
	
	@PostMapping("/loadList")
	public String loadList(@RequestParam("getList") String getList ,Model theModel) {

		// if faculty is not coordinator
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (  auth != null &&   !( auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MENTOR"))  )   ) {
						    
			return "redirect:/faculty/facultyPage";
			}
						
		//basic if mentor
		Faculty currentFaculty = new Faculty();
						
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
							   
			String currentEmail = authentication.getName();
							
			currentFaculty = spmsService.loadFacultyByEmail(currentEmail);
							
		}
		
		// for checking new message===============================================================
		// Will execute if Mentor==============================================================
		
			MentorMessageChecker messageChecker = new MentorMessageChecker(currentFaculty, spmsService);
			boolean newMessage = messageChecker.check();
					
			theModel.addAttribute("newMessage", newMessage);
		//=========================================================================================

		
		if(getList.equals("NULL")) {
			
			return "redirect:/faculty/mentor/newMessage";
		}
		
		int sIndex = 0;
		int lIndex = getList.indexOf(":");
		
		String batch = getList.substring(sIndex, lIndex-1); 	
		
		sIndex = lIndex;
		lIndex = getList.indexOf(":", sIndex+1);
		
		String semester = getList.substring(sIndex+2, lIndex-1);
		
		
		sIndex = lIndex;
	
		String learner = getList.substring(sIndex+2, getList.length());
		
		SortedSemDataModel semData = new SortedSemDataModel();
		
		semData.setBatch(batch);
		semData.setSemester(semester);
		semData.setLearner(learner);
		semData.setDept(currentFaculty.getDept());
		
		List<MentorAllotStudents> allotedStudents = spmsService.loadAllotStudentsToMentors(currentFaculty);
		
		List<StudentSemData> semDataList =  spmsService.loadStudentSemDataByLearner(semData);
		
	
		theModel.addAttribute("currentFaculty", currentFaculty);
		theModel.addAttribute("allotedStudents", allotedStudents);
		theModel.addAttribute("semDataList", semDataList);
		
		return "/faculty/mentor/new-message";
	}
	
	@GetMapping("/writeMessage")
	public String writeMessage(@RequestParam("toEmail") String toEmail,
							   Model theModel) {
		
		// if faculty is not coordinator
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (  auth != null &&   !( auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MENTOR"))  )   ) {
						    
			return "redirect:/faculty/facultyPage";
			}
						
		//basic if mentor
		Faculty currentFaculty = new Faculty();
						
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
							   
			String currentEmail = authentication.getName();
							
			currentFaculty = spmsService.loadFacultyByEmail(currentEmail);
							
		}
		
		// for checking new message===============================================================
		// Will execute if Mentor==============================================================
		
			MentorMessageChecker messageChecker = new MentorMessageChecker(currentFaculty, spmsService);
			boolean newMessage = messageChecker.check();
					
			theModel.addAttribute("newMessage", newMessage);
		//=========================================================================================

		
		
		theModel.addAttribute("currentFaculty", currentFaculty);
		theModel.addAttribute("toEmail", toEmail);
		
		return "/faculty/mentor/write-message";
		
	}
	
	@PostMapping("/sendMessage")
	public String sendMessage(@RequestParam("toEmail") String toEmail,
							  @RequestParam("message") String message) {
		
		// if faculty is not coordinator
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (  auth != null &&   !( auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MENTOR"))  )   ) {
						    
			return "redirect:/faculty/facultyPage";
			}
						
		//basic if mentor
		Faculty currentFaculty = new Faculty();
						
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
							   
			String currentEmail = authentication.getName();
							
			currentFaculty = spmsService.loadFacultyByEmail(currentEmail);
							
		}

		MessageCenter newMessage = new MessageCenter();
		
		newMessage.setFromEmail(currentFaculty.getEmail());
		newMessage.setToEmail(toEmail);
		newMessage.setMessage(message);
		
		spmsService.saveOrUpdateMessage(newMessage);
		
		
		
		return "redirect:/faculty/mentor/inbox";
	}

}
