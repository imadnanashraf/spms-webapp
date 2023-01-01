package com.adnan.icode.fun.spms.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.adnan.icode.fun.spms.entity.Faculty;
import com.adnan.icode.fun.spms.entity.FacultyRole;
import com.adnan.icode.fun.spms.entity.Notification;
import com.adnan.icode.fun.spms.entity.Student;
import com.adnan.icode.fun.spms.entity.SubjectsList;
import com.adnan.icode.fun.spms.helper.MentorMessageChecker;
import com.adnan.icode.fun.spms.models.AddSubjectModel;
import com.adnan.icode.fun.spms.models.AllotStudentToMentorModel;
import com.adnan.icode.fun.spms.models.ListSubjectModel;
import com.adnan.icode.fun.spms.models.PushNotificationModel;
import com.adnan.icode.fun.spms.models.SubjectListSimplifiedModel;
import com.adnan.icode.fun.spms.service.SpmsService;

@Controller
@RequestMapping("/faculty/coordinator")
public class CodController {
	
	@Autowired
	private SpmsService spmsService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor trimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, trimmerEditor);
	}
	
	@GetMapping("/subjects")
	public String subjects(Model theModel) {
		
		// if faculty is not coordinator
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (  auth != null &&   !( auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_COORDINATOR"))  )   ) {
				    
			 return "redirect:/faculty/facultyPage";
		}

				
		//basic if coordinator
		Faculty currentFaculty = new Faculty();
				
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
					   
			String currentEmail = authentication.getName();
					
			currentFaculty = spmsService.loadFacultyByEmail(currentEmail);
					
		}
		
		// for checking new message===============================================================
		// only if faculty is mentor==============================================================
		Authentication mentorAuth = SecurityContextHolder.getContext().getAuthentication();
		if (  mentorAuth != null &&   ( mentorAuth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MENTOR"))  )   ) {
			
			MentorMessageChecker messageChecker = new MentorMessageChecker(currentFaculty, spmsService);
			boolean newMessage = messageChecker.check();
					
			theModel.addAttribute("newMessage", newMessage);
			 
		}
		
		//=========================================================================================


		
		AddSubjectModel addSubjectModel = new AddSubjectModel();
		ListSubjectModel listSubjectModel = new ListSubjectModel();
		
		
		theModel.addAttribute("currentFaculty", currentFaculty);
		theModel.addAttribute("addSubjectModel", addSubjectModel);
		theModel.addAttribute("listSubjectModel", listSubjectModel);
		
		return "faculty/coordinator/add-subjects";
	}
	
	@GetMapping("/addSubject")
	public String addSubject(@Valid 
							 @ModelAttribute("addSubjectModel") AddSubjectModel addSubjectModel,
							 BindingResult theBindingResult,
							 @ModelAttribute("listSubjectModel") ListSubjectModel listSubjectModel,
							 RedirectAttributes redirectAttributes,
							 Model theModel) {
		
		// if faculty is not coordinator
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (  auth != null &&   !( auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_COORDINATOR"))  )   ) {
				    
			 return "redirect:/faculty/facultyPage";
		}
				
		//basic if coordinator
		Faculty currentFaculty = new Faculty();
				
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
					   
			String currentEmail = authentication.getName();
					
			currentFaculty = spmsService.loadFacultyByEmail(currentEmail);
					
		}
		
		// for checking new message===============================================================
		// only if faculty is mentor==============================================================
		Authentication mentorAuth = SecurityContextHolder.getContext().getAuthentication();
		if (  mentorAuth != null &&   ( mentorAuth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MENTOR"))  )   ) {
			
			MentorMessageChecker messageChecker = new MentorMessageChecker(currentFaculty, spmsService);
			boolean newMessage = messageChecker.check();
					
			theModel.addAttribute("newMessage", newMessage);
			 
		}
		
		//=========================================================================================

		
		theModel.addAttribute("currentFaculty", currentFaculty);
		
		if(theBindingResult.hasErrors()) {
			
			
			theModel.addAttribute("addSubjectModel", addSubjectModel);
			theModel.addAttribute("listSubjectModel", listSubjectModel);
			
			return "faculty/coordinator/add-subjects";
		}
		
		Faculty checkFaculty = spmsService.loadFacultyByEmail(addSubjectModel.getEmail());
		
		if(checkFaculty == null) {
			
			theModel.addAttribute("emailExist", "email doesn't exist");
			theModel.addAttribute("addSubjectModel", addSubjectModel);
			theModel.addAttribute("listSubjectModel", listSubjectModel);
			
			return "faculty/coordinator/add-subjects";
		}
		
		
		SubjectsList subject = new SubjectsList();
		
		subject.setSubjectName(addSubjectModel.getSubjects().toUpperCase());
		subject.setBatch(Integer.parseInt( addSubjectModel.getBatch() ));
		subject.setSemester(Integer.parseInt( addSubjectModel.getSemester() ));
		subject.setFacultyEmail(addSubjectModel.getEmail());
		subject.setDept(currentFaculty.getDept());
		
		spmsService.addSubjectList(subject);
		
		listSubjectModel.setBatch( String.valueOf(subject.getBatch()) );
		listSubjectModel.setSemester( String.valueOf(subject.getSemester()));
		
		redirectAttributes.addFlashAttribute("listSubjectModel", listSubjectModel);
		
		
		return "redirect:/faculty/coordinator/listSubjects";
	}
	
	@GetMapping("/listSubjects")
	public String listSubjects(@Valid 
							 @ModelAttribute("listSubjectModel") ListSubjectModel listSubjectModel,
							 BindingResult theBindingResult,
							 @ModelAttribute("addSubjectModel") AddSubjectModel addSubjectModel,
							 Model theModel) {
		
		// if faculty is not coordinator
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (  auth != null &&   !( auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_COORDINATOR"))  )   ) {
				    
			 return "redirect:/faculty/facultyPage";
		}
				
		//basic if coordinator
		Faculty currentFaculty = new Faculty();
				
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
					   
			String currentEmail = authentication.getName();
					
			currentFaculty = spmsService.loadFacultyByEmail(currentEmail);
					
		}
		
		// for checking new message===============================================================
		// only if faculty is mentor==============================================================
		Authentication mentorAuth = SecurityContextHolder.getContext().getAuthentication();
		if (  mentorAuth != null &&   ( mentorAuth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MENTOR"))  )   ) {
			
			MentorMessageChecker messageChecker = new MentorMessageChecker(currentFaculty, spmsService);
			boolean newMessage = messageChecker.check();
					
			theModel.addAttribute("newMessage", newMessage);
			 
		}
		
		//=========================================================================================

		
		theModel.addAttribute("currentFaculty", currentFaculty);
		
		if(theBindingResult.hasErrors()) {
			
			
			theModel.addAttribute("addSubjectModel", addSubjectModel);
			theModel.addAttribute("listSubjectModel", listSubjectModel);
			
			return "faculty/coordinator/add-subjects";
		}
		
		List<SubjectsList> originalSubjectsList = new ArrayList<SubjectsList>();
		
		originalSubjectsList = spmsService.loadSubjectList(Integer.parseInt( listSubjectModel.getBatch() ) , Integer.parseInt( listSubjectModel.getSemester() ) , currentFaculty.getDept());
		
		List<SubjectListSimplifiedModel> subjectsList  = new ArrayList<SubjectListSimplifiedModel>();
		
		if( !(originalSubjectsList.isEmpty()) ) {
			
			for(SubjectsList tempSubject: originalSubjectsList) {
				SubjectListSimplifiedModel tempSubjectSimplified = new SubjectListSimplifiedModel();
				Faculty tempFaculty = new Faculty();
				
				tempFaculty = spmsService.loadFacultyByEmail(tempSubject.getFacultyEmail());
				
				tempSubjectSimplified.setSubjectId(tempSubject.getId());
				tempSubjectSimplified.setSubject(tempSubject.getSubjectName());
				tempSubjectSimplified.setBatch(tempSubject.getBatch());
				tempSubjectSimplified.setSemester(tempSubject.getSemester());
				tempSubjectSimplified.setFacultyEmail(tempSubject.getFacultyEmail());
				tempSubjectSimplified.setProfilePic(tempFaculty.getProfilePic());
				tempSubjectSimplified.setFacultyName(tempFaculty.getFirstName()+" "+tempFaculty.getLastName());
				tempSubjectSimplified.setFacultyDept(tempFaculty.getDept());
				
				subjectsList.add(tempSubjectSimplified);
				
			}
			
		}
		
		theModel.addAttribute("currentFaculty", currentFaculty);
		theModel.addAttribute("addSubjectModel", addSubjectModel);
		theModel.addAttribute("listSubjectModel", listSubjectModel);
		
		theModel.addAttribute("subjectsList", subjectsList);
		
		return "faculty/coordinator/add-subjects";
	}
	
	@GetMapping("/deleteSubject")
	public String deleteSubject(@RequestParam("subjectId") int subjectId,
			 					RedirectAttributes redirectAttributes){
		
		// if faculty is not coordinator
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (  auth != null &&   !( auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_COORDINATOR"))  )   ) {
						    
			return "redirect:/faculty/facultyPage";
		}
						
		//basic if coordinator
		Faculty currentFaculty = new Faculty();
						
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
							   
			String currentEmail = authentication.getName();
							
			currentFaculty = spmsService.loadFacultyByEmail(currentEmail);
							
		}
		
		
		SubjectsList oneSubject = new SubjectsList();
		
		oneSubject = spmsService.loadOneSubjectList(subjectId);
		
		if(  (oneSubject == null) || !( oneSubject.getDept().equals( currentFaculty.getDept() ) )   ){
			
			return "redirect:/faculty/facultyPage";
			
		}
		
		spmsService.deleteSubject(oneSubject);
		spmsService.perSubjectAssessmentDelete(oneSubject.getBatch(), oneSubject.getSemester(), oneSubject.getDept(), oneSubject.getSubjectName());
		
		
		ListSubjectModel listSubjectModel = new ListSubjectModel();
		
		listSubjectModel.setBatch( String.valueOf(oneSubject.getBatch()) );
		listSubjectModel.setSemester( String.valueOf(oneSubject.getSemester()));
		
		redirectAttributes.addFlashAttribute("listSubjectModel", listSubjectModel);
		
		return "redirect:/faculty/coordinator/listSubjects";
	}
	
	@GetMapping("/viewMentors")
	public String viewMentors(Model theModel) {
		
		// if faculty is not coordinator
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (  auth != null &&   !( auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_COORDINATOR"))  )   ) {
						    
			 return "redirect:/faculty/facultyPage";
		}
						
		//basic if coordinator
		Faculty currentFaculty = new Faculty();
						
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
							   
			String currentEmail = authentication.getName();
							
			currentFaculty = spmsService.loadFacultyByEmail(currentEmail);
							
		}
		// for checking new message===============================================================
		// only if faculty is mentor==============================================================
		Authentication mentorAuth = SecurityContextHolder.getContext().getAuthentication();
		if (  mentorAuth != null &&   ( mentorAuth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MENTOR"))  )   ) {
			
			MentorMessageChecker messageChecker = new MentorMessageChecker(currentFaculty, spmsService);
			boolean newMessage = messageChecker.check();
					
			theModel.addAttribute("newMessage", newMessage);
			 
		}
		
		//=========================================================================================

		
		List<Faculty> mentors = new ArrayList<Faculty>();
		List<Faculty> deptFacultyAllRoles = new ArrayList<Faculty>();
		
		deptFacultyAllRoles = spmsService.loadFacultyByDeptWithRoles(currentFaculty.getDept());
		
		if( !(deptFacultyAllRoles.isEmpty()) ) {
		
			for(Faculty tempFaculty : deptFacultyAllRoles) {
			
				for(FacultyRole roles : tempFaculty.getRoles()) {
					
					if(roles.getRole().equals("ROLE_MENTOR")) {
						
						mentors.add(tempFaculty);
						
					}
					
				}
		
			}
		}
		
		if( mentors.isEmpty() ) {
			
			mentors = null;
			
		}	
		
		theModel.addAttribute("currentFaculty", currentFaculty);
		theModel.addAttribute("mentors", mentors);
		
		return "faculty/coordinator/view-mentors";
	}
	
	@GetMapping("/allotStudentsToMentor")
	public String allotStudentsToMentor(@RequestParam("facultyEmail") String facultyEmail,
										Model theModel) {
		
		// if faculty is not coordinator
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (  auth != null &&   !( auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_COORDINATOR"))  )   ) {
								    
			 return "redirect:/faculty/facultyPage";
		}
								
		//basic if coordinator
		Faculty currentFaculty = new Faculty();
								
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
									   
			String currentEmail = authentication.getName();
									
			currentFaculty = spmsService.loadFacultyByEmail(currentEmail);
									
		}
		
		// for checking new message===============================================================
		// only if faculty is mentor==============================================================
		Authentication mentorAuth = SecurityContextHolder.getContext().getAuthentication();
		if (  mentorAuth != null &&   ( mentorAuth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MENTOR"))  )   ) {
			
			MentorMessageChecker messageChecker = new MentorMessageChecker(currentFaculty, spmsService);
			boolean newMessage = messageChecker.check();
					
			theModel.addAttribute("newMessage", newMessage);
			 
		}
		
		//=========================================================================================

		
		// for verifying purpose
		
		Faculty allotStudentFaculty = new Faculty();
		
		allotStudentFaculty = spmsService.loadFacultyByEmailWithRoles(facultyEmail);

		if( allotStudentFaculty == null || !(currentFaculty.getDept().equals(allotStudentFaculty.getDept())) ) {
			
			return "redirect:/faculty/facultyPage";
		}
		
		boolean isMentor=false;
		
		for(FacultyRole tempRole : allotStudentFaculty.getRoles()) {
			
			if( tempRole.getRole().equals("ROLE_MENTOR") ) {
				isMentor = true;
			}
			
		}
		
		if(isMentor == false) {
			return "redirect:/faculty/facultyPage";
		}
		
		// for displaying alloted students
		Faculty allotedFaculty = new Faculty();
		
		allotedFaculty = spmsService.loadFacultyByEmailWithStudents(facultyEmail);
		
		AllotStudentToMentorModel allotStudentModel =  new AllotStudentToMentorModel();
		allotStudentModel.setEmail(facultyEmail);
		
		theModel.addAttribute("currentFaculty", currentFaculty);
		theModel.addAttribute("allotStudentModel", allotStudentModel);
		theModel.addAttribute("mentorData", allotedFaculty.getAllotStudent());
		
		return "faculty/coordinator/allot-students";
	}
	
	@GetMapping("/addProcessStudents")
	public String addProcessStudents(@Valid 
									 @ModelAttribute("allotStudentModel") AllotStudentToMentorModel allotStudentModel,
									 BindingResult theBindingResult,
									 RedirectAttributes redirectAttributes,
									 Model theModel) {
		
		// if faculty is not coordinator
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (  auth != null &&   !( auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_COORDINATOR"))  )   ) {
										    
			 return "redirect:/faculty/facultyPage";
		}
										
		//basic if coordinator
		Faculty currentFaculty = new Faculty();
										
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
											   
			String currentEmail = authentication.getName();
											
			currentFaculty = spmsService.loadFacultyByEmail(currentEmail);
											
		}
		
		// for checking new message===============================================================
		// only if faculty is mentor==============================================================
		Authentication mentorAuth = SecurityContextHolder.getContext().getAuthentication();
		if (  mentorAuth != null &&   ( mentorAuth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MENTOR"))  )   ) {
			
			MentorMessageChecker messageChecker = new MentorMessageChecker(currentFaculty, spmsService);
			boolean newMessage = messageChecker.check();
					
			theModel.addAttribute("newMessage", newMessage);
			 
		}
		
		//=========================================================================================

		
		theModel.addAttribute("currentFaculty", currentFaculty);

		// for displaying alloted students
		Faculty allotedFaculty = new Faculty();
		
		
		if(theBindingResult.hasErrors()) {
			
			allotedFaculty = spmsService.loadFacultyByEmailWithStudents(allotStudentModel.getEmail());
			
			theModel.addAttribute("mentorData", allotedFaculty.getAllotStudent());
		
			return "faculty/coordinator/allot-students";
		}
		
		spmsService.saveAllotStudentsToMentors(allotStudentModel);
		
		allotedFaculty = spmsService.loadFacultyByEmailWithStudents(allotStudentModel.getEmail());
		
		theModel.addAttribute("mentorData", allotedFaculty.getAllotStudent());
		
		return "faculty/coordinator/allot-students";
		
	}
	
	@GetMapping("/notification")
	public String notification(Model theModel) {
		
		// if faculty is not coordinator
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (  auth != null &&   !( auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_COORDINATOR"))  )   ) {
										    
			 return "redirect:/faculty/facultyPage";
		}
		
		//basic if coordinator
		Faculty currentFaculty = new Faculty();
												
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
													   
			String currentEmail = authentication.getName();
													
			currentFaculty = spmsService.loadFacultyByEmail(currentEmail);
													
		}
		
		// for checking new message===============================================================
		// only if faculty is mentor==============================================================
		Authentication mentorAuth = SecurityContextHolder.getContext().getAuthentication();
		if (  mentorAuth != null &&   ( mentorAuth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MENTOR"))  )   ) {
			
			MentorMessageChecker messageChecker = new MentorMessageChecker(currentFaculty, spmsService);
			boolean newMessage = messageChecker.check();
					
			theModel.addAttribute("newMessage", newMessage);
			 
		}
		
		//=========================================================================================

				
		theModel.addAttribute("currentFaculty", currentFaculty);
				
		List<Notification> notification = spmsService.loadNotificationByDept(currentFaculty.getDept());
				
		PushNotificationModel pushNotification = new PushNotificationModel();
				
		theModel.addAttribute("pushNotification", pushNotification);
		theModel.addAttribute("notificationList", notification);
		
		return "faculty/coordinator/notification-faculty";
	}
	
	@GetMapping("/postNotification")
	public String postNotification(
								   @Valid 
								   @ModelAttribute("pushNotification") PushNotificationModel notificationModel,
								   BindingResult theBindingResult,
								   Model theModel) {
		
		
		
		// if faculty is not coordinator
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (  auth != null &&   !( auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_COORDINATOR"))  )   ) {
												    
			 return "redirect:/faculty/facultyPage";
		}
		
		//basic if coordinator
		Faculty currentFaculty = new Faculty();
														
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
															   
			String currentEmail = authentication.getName();
															
			currentFaculty = spmsService.loadFacultyByEmail(currentEmail);
															
		}
		
		// for checking new message===============================================================
		// only if faculty is mentor==============================================================
		Authentication mentorAuth = SecurityContextHolder.getContext().getAuthentication();
		if (  mentorAuth != null &&   ( mentorAuth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MENTOR"))  )   ) {
			
			MentorMessageChecker messageChecker = new MentorMessageChecker(currentFaculty, spmsService);
			boolean newMessage = messageChecker.check();
					
			theModel.addAttribute("newMessage", newMessage);
			 
		}
		
		//=========================================================================================
						
		theModel.addAttribute("currentFaculty", currentFaculty);
		
		if(theBindingResult.hasErrors()) {
		
			List<Notification> notification = spmsService.loadNotificationByDept(currentFaculty.getDept());
			theModel.addAttribute("notificationList", notification);
			
			return "faculty/coordinator/notification-faculty";
		}
		
		List<Notification> notificationSizeCheck = spmsService.loadNotificationByDept(currentFaculty.getDept());
		
		if( !(notificationSizeCheck.isEmpty()) ) {
			
			if(notificationSizeCheck.size() == 40) {
				int id = notificationSizeCheck.get( notificationSizeCheck.size()-1 ).getId();

				
				spmsService.deleteLastNotificationByDeptAndId(id, currentFaculty.getDept() );
			
			}
			
		}
		
		Notification postNotification = new Notification();
		
		postNotification.setBatch( Integer.parseInt(notificationModel.getBatch()) );
		postNotification.setDept(currentFaculty.getDept());
		postNotification.setNotification( notificationModel.getNotification() );
		
		spmsService.uploadNotification(postNotification);
		
		List<Notification> notification = spmsService.loadNotificationByDept(currentFaculty.getDept());
		theModel.addAttribute("notificationList", notification);
		
		return "faculty/coordinator/notification-faculty";
	}

}
