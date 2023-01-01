package com.adnan.icode.fun.spms.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.adnan.icode.fun.spms.entity.Faculty;
import com.adnan.icode.fun.spms.entity.PercentageController;
import com.adnan.icode.fun.spms.entity.Student;
import com.adnan.icode.fun.spms.entity.StudentPerSubject;
import com.adnan.icode.fun.spms.entity.StudentSemData;
import com.adnan.icode.fun.spms.entity.SubjectsList;
import com.adnan.icode.fun.spms.exception.FileOutException;
import com.adnan.icode.fun.spms.helper.CookieCheckerRedirector;
import com.adnan.icode.fun.spms.helper.MentorMessageChecker;
import com.adnan.icode.fun.spms.helper.OverallAssessmentCal;
import com.adnan.icode.fun.spms.helper.OverallSubjectsPercentageCal;
import com.adnan.icode.fun.spms.helper.RandomAlphanumericGenerator;
import com.adnan.icode.fun.spms.helper.StudentMessageChecker;
import com.adnan.icode.fun.spms.models.DepartmentOnlyModel;
import com.adnan.icode.fun.spms.models.EmailModel;
import com.adnan.icode.fun.spms.models.PerSubjectModel;
import com.adnan.icode.fun.spms.models.SortedSemDataModel;
import com.adnan.icode.fun.spms.models.StudentAddMarksModel;
import com.adnan.icode.fun.spms.models.StudentFilterModel;
import com.adnan.icode.fun.spms.models.TempFacultyProfileModel;
import com.adnan.icode.fun.spms.models.TempStudentProfileModel;
import com.adnan.icode.fun.spms.service.SpmsService;

@Controller
@RequestMapping("/faculty")
public class FacultyController {
	
	@Autowired
	private CookieCheckerRedirector checkerRedirector;
	
	@Autowired
	private SpmsService spmsService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor trimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, trimmerEditor);
	}
	
	@GetMapping("/facultyLogin")
	public String facultyLogin(HttpServletRequest request, HttpServletResponse response) {
		// redirector if logged in==========================================
		
		String redirect = checkerRedirector.redirect(request, response);
		
		if (redirect != null) {
			return redirect;
		}
	    //====================================================================
		return "faculty/faculty-login";
	}
	 
	@GetMapping("/facultyAccessDenied")
	public String accessDenied() {
		//rendering page if faculty tries to access student page
		return "faculty/faculty-access";
	}
	
	@GetMapping("/facultyPage")
	public String facultyPage(Model theModel) {
		
		Faculty currentFaculty = new Faculty();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			
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
		
		
		return "faculty/faculty-home";
	} 
	
	//student profile page controller
	@GetMapping("/facultyProfile")
	public String facultyProfile(Model theModel,
									@ModelAttribute("message") String message) {
			
			
		Faculty currentFaculty = new Faculty();
			
		//checking for authenticated user
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


			// creating object of profile model and instantiating it with current student
			// data to be displayed and for ensuring validation
			
			TempFacultyProfileModel profileModel = new TempFacultyProfileModel();
			
			profileModel.setFirstName(currentFaculty.getFirstName());
			profileModel.setLastName(currentFaculty.getLastName());

			
			theModel.addAttribute("currentFaculty", currentFaculty);
			theModel.addAttribute("profileModel", profileModel);
			
			
			return "faculty/faculty-profile";
			
		}
	
	@GetMapping("/profileUpdating")
	public String profileUpdating(@Valid 
								  @ModelAttribute("profileModel") TempFacultyProfileModel profileModel,
								  BindingResult theBindingResult,
								  Model theModel) {
		
		Faculty currentFaculty = new Faculty();
		
		//checking for authenticated user
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


		
		if(theBindingResult.hasErrors()) {
			
			theModel.addAttribute("currentFaculty", currentFaculty);
			
			return "faculty/faculty-profile";
		}else {
			
			currentFaculty.setFirstName(profileModel.getFirstName().toUpperCase());
			currentFaculty.setLastName(profileModel.getLastName().toUpperCase());
			
			spmsService.updateFaculty(currentFaculty);
		}
									
		
		return "redirect:/faculty/facultyProfile";
		
	}
	
	@PostMapping("/imageUpdating")
	public String imageUpdating(@RequestParam("image") CommonsMultipartFile file,
								HttpServletRequest request,
								Model theModel) throws FileOutException {
		
		
		Faculty currentFaculty = new Faculty();
		
		//checking for authenticated user
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

		
		
		// controlling type of contents to be uploaded
		if(file.getSize() <= 0 || file.getSize() > 500000 ) {
			theModel.addAttribute("message", "must be less than 500kb");
			return "redirect:/faculty/facultyProfile";
		}
		
		if(!(file.getContentType().equals("image/jpeg"))){
			theModel.addAttribute("message", "file must be jpg");
			return "redirect:/faculty/facultyProfile";
		}
		
		//for appending unique name to images
		RandomAlphanumericGenerator generator = new RandomAlphanumericGenerator();
		String uniqueString = generator.generate();
		
		String newFileName = uniqueString+file.getOriginalFilename();
		
		// path where it is to be stored
		String path = request.getRealPath("/")+"resources"+File.separator+"profile"+File.separator+newFileName;
		
		System.out.println(path);
		
		byte[] image = file.getBytes();
		
		try {
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(image);
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new FileOutException("Error in uploading File");
		}
		
		// updating in database
		currentFaculty.setProfilePic(newFileName);
		spmsService.updateFaculty(currentFaculty);
		
		return "redirect:/faculty/facultyProfile";
	}
	
	@GetMapping("/evaluation")
	public String evaluation(Model theModel,
							@ModelAttribute("emailExist") String emailExist
							) {
		
		Faculty currentFaculty = new Faculty();
		StudentFilterModel filterModel = new StudentFilterModel();
		EmailModel emailModel = new EmailModel();
		//checking for authenticated user
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
	

		
		theModel.addAttribute("filterModel", filterModel);
		theModel.addAttribute("emailModel", emailModel);
		theModel.addAttribute("currentFaculty", currentFaculty);
		
		
		return "faculty/faculty-evaluation";
	}
	
	@GetMapping("/studentFilterList")
	public String studentFilterList(@Valid
									@ModelAttribute("filterModel") StudentFilterModel filterModel,
									BindingResult theBindingResult,
									@ModelAttribute("emailModel") EmailModel tempEmail,
									Model theModel) {
		Faculty currentFaculty = new Faculty();
		
		//checking for authenticated user
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
			
			return "faculty/faculty-evaluation";
		}
		// if success 
		List<StudentSemData> semData = new ArrayList<StudentSemData>();
		
		semData = spmsService.loadStudentSemDataByFilter(filterModel);
		
		theModel.addAttribute("semData", semData);
		theModel.addAttribute("searchType", "filter");
		
		return "faculty/faculty-evaluation";
		
	}
	
	@GetMapping("/studentByEmail")
	public String studentByEmail(@Valid 
								 @ModelAttribute("emailModel") EmailModel tempEmail,
								 BindingResult theBindingResult,
								 @ModelAttribute("filterModel") StudentFilterModel filterModel,
								 Model theModel) {
		
		Faculty currentFaculty = new Faculty();
		List<StudentSemData> semData = new ArrayList<StudentSemData>();
		//checking for authenticated user
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
			
			return "faculty/faculty-evaluation";
		}
		// if success 
		Student tempStudent = spmsService.loadStudentByEmail(tempEmail.getEmail());
		
		if(tempStudent == null) {
			theModel.addAttribute("emailExist", "email does not exist");
			
			return "redirect:/faculty/evaluation";
		}
		semData = spmsService.loadStudentSemData(tempEmail.getEmail());
	
		theModel.addAttribute("semData", semData);
		theModel.addAttribute("searchType", "email");
	
		return "faculty/faculty-evaluation";
	}
	
	@GetMapping("/assessStudent")
	public String assessStudent(@RequestParam("email") String studentEmail,
								@RequestParam("semester") int studentSemester,
								@RequestParam("searchType") String searchType,
								Model theModel) {
		
		Faculty currentFaculty = new Faculty();
		Student assessStudent = new Student();
		StudentFilterModel filterModel = new StudentFilterModel();
		EmailModel emailModel = new EmailModel();
		List<SubjectsList> subjects = new ArrayList<SubjectsList>();
		//checking for authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			   
			String currentEmail = authentication.getName();
			    
			currentFaculty = spmsService.loadFacultyByEmail(currentEmail);
			assessStudent = spmsService.loadStudentByEmail(studentEmail);
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

		
		String studentName = assessStudent.getFirstName()+" "+assessStudent.getLastName();
		String studentContact = assessStudent.getContactNo();
		String studentFather = assessStudent.getFatherName();
		String studentGuardianContact = assessStudent.getGuardianContactNo();
		
		subjects = spmsService.loadSubjectList(assessStudent.getBatch(), studentSemester, assessStudent.getDept(), currentFaculty.getEmail());
		
		theModel.addAttribute("filterModel", filterModel);
		theModel.addAttribute("emailModel", emailModel);
		theModel.addAttribute("currentFaculty", currentFaculty);
		theModel.addAttribute("subjects", subjects);
		theModel.addAttribute("semester", studentSemester);
		theModel.addAttribute("email", studentEmail);
		theModel.addAttribute("studentName", studentName);
		theModel.addAttribute("studentContact", studentContact);
		theModel.addAttribute("studentFather", studentFather);
		theModel.addAttribute("studentGuardianContact", studentGuardianContact);
		theModel.addAttribute("searchType", searchType);
		
		return "faculty/faculty-subject";
	}
	
	@GetMapping("/assessStudentSubSelected")
	public String assessStudentSubSelected(
										   @RequestParam("semester") int studentSemester,
										   @RequestParam("subject") String subjectName,
										   @RequestParam("email") String studentEmail,
										   @RequestParam("searchType") String searchType,
										   Model theModel
											) {
		
		Faculty currentFaculty = new Faculty();
		Student assessStudent = new Student();
		StudentFilterModel filterModel = new StudentFilterModel();
		EmailModel emailModel = new EmailModel();
	
		//checking for authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			   
			String currentEmail = authentication.getName();
			    
			currentFaculty = spmsService.loadFacultyByEmail(currentEmail);
			assessStudent = spmsService.loadStudentByEmail(studentEmail);
			
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


		
		String studentName = assessStudent.getFirstName()+" "+assessStudent.getLastName();
		
		theModel.addAttribute("filterModel", filterModel);
		theModel.addAttribute("emailModel", emailModel);
		theModel.addAttribute("currentFaculty", currentFaculty);
		theModel.addAttribute("studentName", studentName);
		
		// these 3 is needed to find already existing data to update it
		// or create a new data and add it
		theModel.addAttribute("semester", studentSemester);
		theModel.addAttribute("subject", subjectName);
		theModel.addAttribute("email", studentEmail);
		
		//it is needed to reload default page after assessment 
		theModel.addAttribute("searchType", searchType);
		
		//for populating the assessment if exists
		StudentPerSubject perSubject  = new StudentPerSubject();
		PerSubjectModel perSubjectModel = null;
	
		perSubject = spmsService.loadPerSubjectData(studentEmail, studentSemester, subjectName);
		
		
		if(perSubject != null) {
			perSubjectModel = new PerSubjectModel();
			perSubjectModel.setId( perSubject.getId() );
			perSubjectModel.setBehaviour(String.valueOf( perSubject.getBehaviour() ) );
			perSubjectModel.setFocus(String.valueOf( perSubject.getFocus() ) );
			perSubjectModel.setAttendance(String.valueOf( perSubject.getAttendance() ) );
			perSubjectModel.setOverallSubject(String.valueOf( perSubject.getSubjectOverall() ) );

		}else {
			perSubjectModel = new PerSubjectModel();
		}
		
		theModel.addAttribute("perSubject", perSubjectModel);
		
		
		return "faculty/faculty-assessment";
	}
	
	@GetMapping("/assessmentProcessing")
	public String assessmentProcessing(@Valid 
									   @ModelAttribute("perSubject") PerSubjectModel perSubjectModel,
									   BindingResult theBindingResult,
									   @ModelAttribute("filterModel") StudentFilterModel filterModel,
									   @ModelAttribute("emailModel") EmailModel tempEmail,
									   RedirectAttributes redirectAttributes,
									   Model theModel
									   ) {
		
		
		
		if(theBindingResult.hasErrors()) {
			
			return "redirect:/faculty/evaluation";
		}
		
		
		
		// for putting overall subject
		float overallSubjectPercent;
		
		overallSubjectPercent = 
				(float)( Integer.parseInt(perSubjectModel.getBehaviour()) + Integer.parseInt(perSubjectModel.getFocus()) + Integer.parseInt(perSubjectModel.getAttendance()))/30;
		
		overallSubjectPercent = overallSubjectPercent * 100;
		
		DecimalFormat df  = new DecimalFormat("0.00");
		overallSubjectPercent = Float.parseFloat( df.format(overallSubjectPercent) );
		
		StudentPerSubject perSubject = new  StudentPerSubject();
	
		perSubject.setId( perSubjectModel.getId() );
		perSubject.setSemester( perSubjectModel.getSemester() );
		perSubject.setSubjectName( perSubjectModel.getSubject() );
		perSubject.setBehaviour( Integer.parseInt(perSubjectModel.getBehaviour() ) );
		perSubject.setFocus( Integer.parseInt( perSubjectModel.getFocus() ) );
		perSubject.setAttendance( Integer.parseInt( perSubjectModel.getAttendance() ) );
		perSubject.setSubjectOverall(overallSubjectPercent);
		
		
		spmsService.saveOrUpdateSubjectData(perSubject, perSubjectModel.getEmail());
		
		
		// after success
		//loading student to get batch semester and dept
		//to check if subject list is equal to per subject data of student to initialize overall subject data
		//to redirect back to the student list
		Student currentStudent = spmsService.loadStudentByEmail(perSubjectModel.getEmail());
		
		List<SubjectsList> allSemSubjects = new ArrayList<SubjectsList>();
		// for loading all subjects of batch,semester,dept
		allSemSubjects = spmsService.loadSubjectList(currentStudent.getBatch(), perSubject.getSemester(), currentStudent.getDept());
		
		List<StudentPerSubject> allFilledSubjects = new ArrayList<StudentPerSubject>();
		allFilledSubjects = spmsService.loadPerSubjectDataList( currentStudent.getEmail(), perSubject.getSemester() );
		
		if( (allSemSubjects.size()) == (allFilledSubjects.size()) ) {
			
			float overallSubjectPercentage;
			OverallSubjectsPercentageCal subjectsPercentageCal = new OverallSubjectsPercentageCal();
			
			overallSubjectPercentage = subjectsPercentageCal.calculate(allFilledSubjects);
			
			// now getting the overall sem data of student and percentage contoller
			StudentSemData overallSemData = new StudentSemData();
			PercentageController pController = new PercentageController();
			
			overallSemData = spmsService.loadStudentOneSemData(currentStudent.getEmail(), perSubject.getSemester() );
			pController = spmsService.loadPercentageControllerByDept(currentStudent.getDept());
			
			//assigning it to sem data as it can overall evaluation can be calculated again
			overallSemData.setOverallSubjectAssessment(overallSubjectPercentage);
			
			// now getting overall evaluation of semester
			float overallevaluationPercentage;
			OverallAssessmentCal overallAssessmentCal = new OverallAssessmentCal(overallSemData, pController);
			
			overallevaluationPercentage = overallAssessmentCal.overallPercent();
			
			// now assigning learner type
			String learnerType=null;
			if(pController != null ){
				
				if(pController.getOverallThreshold() > overallevaluationPercentage) {
					
					learnerType = "SLOW";
					
				}else if(pController.getOverallThreshold() <= overallevaluationPercentage) {
					learnerType = "FAST";
				}
				
			}else {
				
				if(60 > overallevaluationPercentage) {
					
					learnerType = "SLOW";
					
				}else if(60 <= overallevaluationPercentage) {
					learnerType = "FAST";
				}
				
			}
			
		
			//now assigning it to the overall sem data
			overallSemData.setOverallEvaluation(overallevaluationPercentage);
			overallSemData.setLearnerType(learnerType);
			// now updating it to student 
			spmsService.uploadMarks(overallSemData, currentStudent.getEmail());
		}
		
		if(perSubjectModel.getSearchType().equals("filter")) {
			
			filterModel.setDept(currentStudent.getDept());
			filterModel.setBatch(String.valueOf(currentStudent.getBatch()));
			filterModel.setSemester(String.valueOf(perSubject.getSemester()));
			redirectAttributes.addFlashAttribute("filterModel", filterModel);
			
			return "redirect:/faculty/studentFilterList";
			
		}
			
		tempEmail.setEmail(currentStudent.getEmail());
		redirectAttributes.addFlashAttribute("emailModel", tempEmail);
			
		return "redirect:/faculty/studentByEmail";
	
	
	}
	
	@GetMapping("/semData")
	public String semData(Model theModel) {
		
		Faculty currentFaculty = new Faculty();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			
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


		
		SortedSemDataModel semData = new SortedSemDataModel();

		theModel.addAttribute("currentFaculty", currentFaculty);
		theModel.addAttribute("semData", semData);
		
		return "faculty/summary-students";
	}
	
	@GetMapping("/sortSemData")
	public String sortSemData(@Valid 
							  @ModelAttribute("semData") SortedSemDataModel semData,
							  BindingResult theBindingResult,
							  Model theModel) {
		
		Faculty currentFaculty = new Faculty();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			
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
			
			return "faculty/summary-students";
		}else {
			//if successful
			
			List<StudentSemData> semDataList =  spmsService.loadStudentSemDataByLearner(semData);
			
			theModel.addAttribute("semDataList", semDataList);
				
			return "faculty/summary-students";
		}
	}
	
	@GetMapping("/facultyList")
	public String facultyList(Model theModel) {
		
		Faculty currentFaculty = new Faculty();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			
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
		theModel.addAttribute("deptModel", new DepartmentOnlyModel());
		
		return "faculty/view-faculty";
	}
	
	
	@GetMapping("/getList")
	public String getList(@Valid 
						  @ModelAttribute("deptModel") DepartmentOnlyModel deptModel,
						  BindingResult theBindingResult,
						  Model theModel) {
		
		Faculty currentFaculty = new Faculty();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			
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
			
			return "faculty/view-faculty";
		}else {
			//if successful
			
			List<Faculty> facultyList =  spmsService.loadFacultyByDept(deptModel.getDept());
			List<Faculty> enabledFacultyList = new ArrayList<Faculty>();
			
			if( !(facultyList.isEmpty()) ) {
				
				for(Faculty tempFaculty : facultyList) {
					
					if(tempFaculty.getEnabled() == true) {
						
						enabledFacultyList.add(tempFaculty);
						
					}
					
				}
				
			}
			
			theModel.addAttribute("facultyList", enabledFacultyList);
				
			return "faculty/view-faculty";
		
		}


	}
	
	
}
