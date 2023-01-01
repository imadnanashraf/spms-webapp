package com.adnan.icode.fun.spms.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
import com.adnan.icode.fun.spms.entity.PercentageController;
import com.adnan.icode.fun.spms.entity.Student;
import com.adnan.icode.fun.spms.entity.StudentSemData;
import com.adnan.icode.fun.spms.helper.CookieCheckerRedirector;
import com.adnan.icode.fun.spms.helper.MentorMessageChecker;
import com.adnan.icode.fun.spms.models.EmailModel;
import com.adnan.icode.fun.spms.models.FacultyWithRolesHelperModel;
import com.adnan.icode.fun.spms.models.PerSubjectModel;
import com.adnan.icode.fun.spms.models.PercentageControllerVModel;
import com.adnan.icode.fun.spms.models.StudentFilterModel;
import com.adnan.icode.fun.spms.service.SpmsService;

@Controller
@RequestMapping("/faculty/hod")
public class HodController {
	 

	@Autowired
	private CookieCheckerRedirector checkerRedirector;
	
	@Autowired
	private SpmsService spmsService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor trimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, trimmerEditor);
	}
	
	
	@GetMapping("/deleteStudent")
	public String deleteStudent(@RequestParam("email") String studentEmail,
								@RequestParam("semester") int studentSemester,
								@RequestParam("searchType") String searchType,
								@RequestParam("dept") String dept,
								RedirectAttributes redirectAttributes,
								Model theModel) {
		
		
		// if faculty is not hod
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (  auth != null &&   !( auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_HOD"))  )   ) {
		    
			return "redirect:/faculty/evaluation";
		}
		
		//basic if hod
		Student currentStudent = new Student();
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

		
		currentStudent = spmsService.loadStudentByEmail(studentEmail);
		
		//if hod is not from same dept.
		if(  !(currentFaculty.getDept().equals(currentStudent.getDept()))  ) {
			return "redirect:/faculty/evaluation";
		}
		
			
		currentStudent = spmsService.deleteStudentAllSemData(studentEmail, studentSemester);
		
		
		StudentFilterModel filterModel = new StudentFilterModel();
		EmailModel tempEmail = new EmailModel();
		
		if(searchType.equals("filter")) {
			

			filterModel.setDept(currentStudent.getDept());
			filterModel.setBatch(String.valueOf(currentStudent.getBatch()));
			filterModel.setSemester(String.valueOf(studentSemester));
			redirectAttributes.addFlashAttribute("filterModel", filterModel);
			
			return "redirect:/faculty/studentFilterList";
			
		}
			
		tempEmail.setEmail(currentStudent.getEmail());
		redirectAttributes.addFlashAttribute("emailModel", tempEmail);
			
		return "redirect:/faculty/studentByEmail";
		
	}
	
	@GetMapping("/allotRoles")
	public String allotRoles(Model theModel) {
		
		// if faculty is not hod
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (  auth != null &&   !( auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_HOD"))  )   ) {
			   
			  return "redirect:/faculty/facultyPage";
			}
		
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
		// loading faculty  list  with roles and with no verification tokens 
		List<Faculty> facultyList = spmsService.loadFacultyByDeptWithRoles(currentFaculty.getDept());
		
		//for loading faculty list with smplified roles list
		
		// defining an object to hold simplified values
		List<FacultyWithRolesHelperModel> helperfacultyList = new ArrayList<FacultyWithRolesHelperModel>();
		
		// instantiating helper object
		for(Faculty tempFaculty: facultyList) {
			
			List<String> tempRoles = new ArrayList<String>();
			
			FacultyWithRolesHelperModel currentHelperFaculty = new FacultyWithRolesHelperModel();
			currentHelperFaculty.setProfilePic(tempFaculty.getProfilePic());
			currentHelperFaculty.setName(tempFaculty.getFirstName()+" "+tempFaculty.getLastName());
			currentHelperFaculty.setEmail(tempFaculty.getEmail());
			
			// assigning original roles
			for(FacultyRole roles: tempFaculty.getRoles()) {
				tempRoles.add(roles.getRole());
				
			}
			
			// displaying weather faculty is enabled or not
			if(tempFaculty.getEnabled() == true) {
				tempRoles.add("ROLE_FACULTY_ENABLED");
			}else {
				tempRoles.add("ROLE_FACULTY_DISABLED");
			}
			
			currentHelperFaculty.setRoles(tempRoles);
			
			helperfacultyList.add(currentHelperFaculty);
			
		}
		
		
		theModel.addAttribute("facultyList", helperfacultyList);
		
		return "faculty/hod/allot-roles";
	}
	
	@GetMapping("/allotProcessing")
	public String allotProcessing(@RequestParam("commandType") String commandType,
								  @RequestParam("role") String role,
								  @RequestParam("email") String facultyEmail) {
		
		
		// if faculty is not hod
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (  auth != null &&   !( auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_HOD"))  )   ) {
					   
		    return "redirect:/faculty/facultyPage";
		}
		
		
		Faculty currentFaculty = new Faculty();
		Faculty allotFaculty = new Faculty();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			   
			String currentEmail = authentication.getName();
			
			currentFaculty = spmsService.loadFacultyByEmail(currentEmail);
			
		}
		
		
		allotFaculty = spmsService.loadFacultyByEmail(facultyEmail);
		
		//if hod is not from same dept.
		if(  !(currentFaculty.getDept().equals(allotFaculty.getDept()))  ) {
			return "redirect:/faculty/facultyPage";
		}
			
		
		if(commandType.equals("permit")) {
			
			if(role.equals("HOD")) {
				
				String permitRole = "ROLE_HOD";
				spmsService.addFacultyRole(allotFaculty.getEmail(), permitRole);
				
			}else if(role.equals("COORDINATOR")) {
				
				String permitRole = "ROLE_COORDINATOR";
				spmsService.addFacultyRole(allotFaculty.getEmail(), permitRole);
				
			}else if(role.equals("MENTOR")) {
				
				String permitRole = "ROLE_MENTOR";
				spmsService.addFacultyRole(allotFaculty.getEmail(), permitRole);
				
			}else if(role.equals("FACULTY")) {
				
				allotFaculty.setEnabled(true);
				
				spmsService.updateFaculty(allotFaculty);
				
			}
			
		}else {
			
			if(role.equals("HOD")) {
				
				String revokeRole = "ROLE_HOD";
				spmsService.deleteFacultyRole(allotFaculty.getEmail(), revokeRole);
				
			}else if(role.equals("COORDINATOR")) {
				
				String revokeRole = "ROLE_COORDINATOR";
				spmsService.deleteFacultyRole(allotFaculty.getEmail(), revokeRole);
				
			}else if(role.equals("MENTOR")) {
				
				String revokeRole = "ROLE_MENTOR";
				spmsService.deleteAllotedStudentsByEmail(allotFaculty.getEmail());
				spmsService.deleteFacultyRole(allotFaculty.getEmail(), revokeRole);
				
			}else if(role.equals("FACULTY")) {
				
				allotFaculty.setEnabled(false);
				spmsService.updateFaculty(allotFaculty);
				
			}
			
		}
		
		
		return "redirect:/faculty/hod/allotRoles";
	}
	
	@GetMapping("/setCriteria")
	public String setCriteria(Model theModel) {
		
		Faculty currentFaculty = new Faculty();
		
		// if faculty is not hod
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (  auth != null &&   !( auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_HOD"))  )   ) {
							   
			return "redirect:/faculty/facultyPage";
		}
		
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

		
		PercentageController pController = new PercentageController();
		PercentageControllerVModel controller = new PercentageControllerVModel();
		
		pController = spmsService.loadPercentageControllerByDept(currentFaculty.getDept());
		
		if(pController != null) {
			
			controller.setId(pController.getId());
			controller.setInternalThreshold(pController.getInternalThreshold());
			controller.setPreviousThreshold(pController.getPreviousThreshold());
			controller.setClassThreshold(pController.getClassThreshold());
			controller.setOverallThreshold(pController.getOverallThreshold());
	
		}
		
		theModel.addAttribute("currentFaculty", currentFaculty);
		theModel.addAttribute("controller", controller);
		
		return "faculty/hod/percentage-controller";
	}
	
	@GetMapping("/allotCriteria")
	public String allotCriteria(@Valid 
								@ModelAttribute("controller") PercentageControllerVModel controller,
			                    BindingResult theBindingResult) {
		
		Faculty currentFaculty = new Faculty();
		
		// if faculty is not hod
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (  auth != null &&   !( auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_HOD"))  )   ) {
							   
			return "redirect:/faculty/facultyPage";
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			   
			String currentEmail = authentication.getName();
			
			currentFaculty = spmsService.loadFacultyByEmail(currentEmail);
			
		}
		

			
		if(theBindingResult.hasErrors()) {
			return "redirect:/faculty/facultyPage";
		}
		
		PercentageController pController = new PercentageController();
		
		pController.setId(controller.getId());
		pController.setInternalThreshold(controller.getInternalThreshold());
		pController.setPreviousThreshold(controller.getPreviousThreshold());
		pController.setClassThreshold(controller.getClassThreshold());
		pController.setOverallThreshold(controller.getOverallThreshold());
		pController.setDept(currentFaculty.getDept());
		
		spmsService.saveOrPercentageController(pController);
		
		return "redirect:/faculty/hod/setCriteria";
	}
	

}
