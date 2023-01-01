package com.adnan.icode.fun.spms.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.hibernate.internal.build.AllowSysOut;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import com.adnan.icode.fun.spms.entity.Faculty;
import com.adnan.icode.fun.spms.entity.MessageCenter;
import com.adnan.icode.fun.spms.entity.Notification;
import com.adnan.icode.fun.spms.entity.PercentageController;
import com.adnan.icode.fun.spms.entity.ReadNotification;
import com.adnan.icode.fun.spms.entity.ReadStudentMessage;
import com.adnan.icode.fun.spms.entity.Student;
import com.adnan.icode.fun.spms.entity.StudentSemData;
import com.adnan.icode.fun.spms.exception.FileOutException;
import com.adnan.icode.fun.spms.helper.CookieCheckerRedirector;
import com.adnan.icode.fun.spms.helper.NotificationChecker;
import com.adnan.icode.fun.spms.helper.OverallAssessmentCal;
import com.adnan.icode.fun.spms.helper.RandomAlphanumericGenerator;
import com.adnan.icode.fun.spms.helper.StudentMessageChecker;
import com.adnan.icode.fun.spms.models.BasicEvalStatModel;
import com.adnan.icode.fun.spms.models.StudentAddMarksModel;
import com.adnan.icode.fun.spms.models.TempStudentProfileModel;
import com.adnan.icode.fun.spms.service.SpmsService;

@Controller
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	private CookieCheckerRedirector checkerRedirector;
	
	@Autowired
	private SpmsService spmsService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor trimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, trimmerEditor);
	}
	 
	
	@GetMapping("/studentLogin") 
	public String studentLogin(HttpServletRequest request, HttpServletResponse response) {
		// redirector if logged in==========================================
		
		String redirect = checkerRedirector.redirect(request, response);
		
		if (redirect != null) {
			return redirect;
		}
	    //====================================================================
		
		return "student/student-login";
	}
	
	@GetMapping("/studentAccessDenied")
	public String accessDenied() {
		// rendering page for students if they try to access faculty pages
		return "student/student-access";
	}
	
	//student home page controller
	@GetMapping("/studentPage")
	public String studentPage(Model theModel) {
		
		Student currentStudent = new Student();
		
		List<StudentSemData> semData = new ArrayList<StudentSemData>();
		
		PercentageController pController = new PercentageController();
		
		//checking for authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentEmail = authentication.getName();
		    
		    currentStudent = spmsService.loadStudentByEmail(currentEmail);
		    
		    semData = spmsService.loadStudentSemData(currentEmail); 
		   
		    pController = spmsService.loadPercentageControllerByDept(currentStudent.getDept());
		    
		}

		// for checking new notification===============================================================
		NotificationChecker notificationChecker = new NotificationChecker(currentStudent, spmsService);
		boolean newNotification = notificationChecker.check();
				
		theModel.addAttribute("newNotification", newNotification);
		//===========================================================================================

		// for checking new message===============================================================
		StudentMessageChecker messageChecker = new StudentMessageChecker(currentStudent, spmsService);
		boolean newMessage = messageChecker.check();
				
		theModel.addAttribute("newMessage", newMessage);
		//===========================================================================================

		
		
		//object to bind these eval and to render it easily
		BasicEvalStatModel stat = new BasicEvalStatModel();
		
		int internalStat, previousStat, classPerStat, evalSem;
		float overallStat;
		String internalStatMsg, previousStatMsg, classPerStatMsg;
		

		if(semData != null) {
			
			StudentSemData tempSemData = semData.get(semData.size()-1);
			
			// semester for which eval is displayed
			evalSem = tempSemData.getSemester();
			
			// basic 3 factors to show only if all 3 factors is filled or not
			if(tempSemData.getOverallInternalAssessment() == 0.0 ) {
				internalStat = 0;
				internalStatMsg = "incomplete";
			}else {
				internalStat = 100;
				internalStatMsg = "completed";
			}
			
			if(tempSemData.getOverallPreviousAssessment() == 0.0 ) {
				previousStat = 0;
				previousStatMsg = "incomplete";
			}else {
				previousStat = 100;
				previousStatMsg = "completed";
			}
			
			if(tempSemData.getOverallSubjectAssessment() == 0.0 ) {
				classPerStat = 0;
				classPerStatMsg = "incomplete";
			}else {
				classPerStat = 100;
				classPerStatMsg = "completed";
			}
			
			//overall evaluation percent
			OverallAssessmentCal overallCal = new OverallAssessmentCal(tempSemData,pController);
			
			overallStat = overallCal.overallPercent();
			
			

			stat.setSemester(evalSem);
			stat.setInternalStat(internalStat);
			stat.setPreviousStat(previousStat);
			stat.setClassPerStat(classPerStat);
			stat.setOverallStat(overallStat);
			stat.setiMsg(internalStatMsg);
			stat.setpMsg(previousStatMsg);
			stat.setcMsg(classPerStatMsg);
			
			theModel.addAttribute("stat", stat);
			
		}
		
		theModel.addAttribute("currentStudent", currentStudent);
		
		
		return "student/student-home";
	}   
	
	//student profile page controller
	@GetMapping("/studentProfile")
	public String studentProfile(Model theModel,
								@ModelAttribute("message") String message) {
		
		
		Student currentStudent = new Student();
		
		//checking for authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentEmail = authentication.getName();
		    
		    currentStudent = spmsService.loadStudentByEmail(currentEmail);
		    
		}
		
		// for checking new notification===============================================================
		NotificationChecker notificationChecker = new NotificationChecker(currentStudent, spmsService);
		boolean newNotification = notificationChecker.check();
				
		theModel.addAttribute("newNotification", newNotification);
		//===========================================================================================
		// for checking new message===============================================================
		StudentMessageChecker messageChecker = new StudentMessageChecker(currentStudent, spmsService);
		boolean newMessage = messageChecker.check();
				
		theModel.addAttribute("newMessage", newMessage);
		//===========================================================================================

		
		// creating object of profile model and instantiating it with current student
		// data to be displayed and for ensuring validation
		
		TempStudentProfileModel profileModel = new TempStudentProfileModel();
		
		profileModel.setFirstName(currentStudent.getFirstName());
		profileModel.setLastName(currentStudent.getLastName());
		profileModel.setUniversityEnroll(String.valueOf(currentStudent.getUniversityEnroll()));
		profileModel.setBatch(String.valueOf(currentStudent.getBatch()));
		
		profileModel.setContactNo(currentStudent.getContactNo());
		profileModel.setFatherName(currentStudent.getFatherName());
		profileModel.setGuardianContactNo(currentStudent.getGuardianContactNo());
		
		theModel.addAttribute("currentStudent", currentStudent);
		theModel.addAttribute("profileModel", profileModel);
				
		
		return "student/student-profile";
		
	}
	
	@GetMapping("/profileUpdating")
	public String profileUpdating(@Valid 
								  @ModelAttribute("profileModel") TempStudentProfileModel profileModel,
								  BindingResult theBindingResult,
								  Model theModel) {
		
		Student currentStudent = new Student();
		//checking for authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentEmail = authentication.getName();
		    
		    currentStudent = spmsService.loadStudentByEmail(currentEmail);
		    
		}

		// for checking new notification===============================================================
		NotificationChecker notificationChecker = new NotificationChecker(currentStudent, spmsService);
		boolean newNotification = notificationChecker.check();
				
		theModel.addAttribute("newNotification", newNotification);
		//===========================================================================================

		// for checking new message===============================================================
		StudentMessageChecker messageChecker = new StudentMessageChecker(currentStudent, spmsService);
		boolean newMessage = messageChecker.check();
				
		theModel.addAttribute("newMessage", newMessage);
		//===========================================================================================
		

		theModel.addAttribute("currentStudent", currentStudent);
		
		if(theBindingResult.hasErrors()) {
			
			return "student/student-profile";
		}else {
			
			Student tempStudent = spmsService.
								loadStudentByEnroll( 
										Long.parseLong( profileModel.getUniversityEnroll() ) 
													);
			
			// if enroll is not existing enroll and enroll doesnt belong to any othre student
			if( ( tempStudent != null ) && ( ( tempStudent.getUniversityEnroll() ) != ( currentStudent.getUniversityEnroll() ) ) ) {
				theModel.addAttribute("enrollExists", "enroll already exists");
				return "student/student-profile";
			}
			
			currentStudent.setFirstName(profileModel.getFirstName().toUpperCase());
			currentStudent.setLastName(profileModel.getLastName().toUpperCase());
			currentStudent.setUniversityEnroll(Long.parseLong(profileModel.getUniversityEnroll()));
			currentStudent.setBatch(Integer.parseInt(profileModel.getBatch()));
			
			currentStudent.setContactNo(profileModel.getContactNo());
			currentStudent.setFatherName(profileModel.getFatherName().toUpperCase());
			currentStudent.setGuardianContactNo(profileModel.getGuardianContactNo());
			
			spmsService.updateStudent(currentStudent);	
		}
									
		
		return "redirect:/student/studentProfile";
		
	}
	
	@PostMapping("/imageUpdating")
	public String imageUpdating(@RequestParam("image") CommonsMultipartFile file,
								HttpServletRequest request,
								Model theModel) throws FileOutException {
		
		
		Student currentStudent = new Student();
		//checking for authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {	
			String currentEmail = authentication.getName(); 
			
			currentStudent = spmsService.loadStudentByEmail(currentEmail);
			}
		
		
		// controlling type of contents to be uploaded
		if(file.getSize() <= 0 || file.getSize() > 500000 ) {
			theModel.addAttribute("message", "must be less than 500kb");
			return "redirect:/student/studentProfile";
		}
		
		if(!(file.getContentType().equals("image/jpeg"))){
			theModel.addAttribute("message", "file must be jpg");
			return "redirect:/student/studentProfile";
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
		currentStudent.setProfilePic(newFileName);
		spmsService.updateStudent(currentStudent);
		
		return "redirect:/student/studentProfile"; 
	}
	
	
	
	//student marks adding page controller
	@GetMapping("/studentMarks")
	public String studentMarks(Model theModel) {
		
		Student currentStudent = new Student();
		List<StudentSemData> semData = new ArrayList<StudentSemData>();
		
		//checking for authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentEmail = authentication.getName();
		    
		    currentStudent = spmsService.loadStudentByEmail(currentEmail);
		    
		    semData = spmsService.loadStudentSemData(currentEmail); 
		    
		}
		
		// for checking new notification===============================================================
		NotificationChecker notificationChecker = new NotificationChecker(currentStudent, spmsService);
		boolean newNotification = notificationChecker.check();
				
		theModel.addAttribute("newNotification", newNotification);
		//===========================================================================================

		// for checking new message===============================================================
		StudentMessageChecker messageChecker = new StudentMessageChecker(currentStudent, spmsService);
		boolean newMessage = messageChecker.check();
				
		theModel.addAttribute("newMessage", newMessage);
		//===========================================================================================

			
		theModel.addAttribute("assessment", semData);
		
		theModel.addAttribute("currentStudent", currentStudent);
		
		theModel.addAttribute("studentMarksModel", new StudentAddMarksModel());
		
		 
		return "student/student-marks";
		
	}
	
	//marks uploading processing
	@GetMapping("/marksUploading")
	public String marksUploading(@Valid 
								 @ModelAttribute("studentMarksModel") StudentAddMarksModel studentMarksModel,
								 BindingResult theBindingResult,
							     Model theModel) {
				
		Student currentStudent = new Student();
		List<StudentSemData> semData = new ArrayList<StudentSemData>();
		
		//checking for authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentEmail = authentication.getName();
		    
		    currentStudent = spmsService.loadStudentByEmail(currentEmail);
		    
		    semData = spmsService.loadStudentSemData(currentEmail); 
		    
		}
		
		// for checking new notification===============================================================
		NotificationChecker notificationChecker = new NotificationChecker(currentStudent, spmsService);
		boolean newNotification = notificationChecker.check();
				
		theModel.addAttribute("newNotification", newNotification);
		//===========================================================================================

		// for checking new message===============================================================
		StudentMessageChecker messageChecker = new StudentMessageChecker(currentStudent, spmsService);
		boolean newMessage = messageChecker.check();
				
		theModel.addAttribute("newMessage", newMessage);
		//===========================================================================================

			
		theModel.addAttribute("assessment", semData);
		theModel.addAttribute("currentStudent", currentStudent);
		
		
		if(theBindingResult.hasErrors()) {
			
			return "student/student-marks";
		}else {
			
			if(semData != null) {
				for(StudentSemData tempData: semData) {
					
					if(tempData.getSemester() == Integer.parseInt(studentMarksModel.getSemester())) {
	
						theModel.addAttribute("semExist", "Current Semester Data Already Uploaded");
						
						return "student/student-marks";
					}
				}
			}
			
			
			theModel.addAttribute("semester", studentMarksModel.getSemester());
			theModel.addAttribute("internalPercent", studentMarksModel.getOverallInternalAssessment());
			theModel.addAttribute("previousPercent", studentMarksModel.getOverallPreviousAssessment());
		
			return "student/student-marks-images";
			
		}
		
	}
	
	@PostMapping("/marksPicsUploading")
	public String marksPicsUploading(
									 @RequestParam("semester") String semester,
			                         @RequestParam("internalPercent") String internalPercent,
			                         @RequestParam("previousPercent") String previousPercent,
			                         @RequestParam("internalPic") CommonsMultipartFile internal,
			                         @RequestParam("previousPic") CommonsMultipartFile previous,
									 Model theModel,
									 HttpServletRequest request) throws FileOutException {
		
		Student currentStudent = new Student();
		List<StudentSemData> semData = new ArrayList<StudentSemData>();
		
		//checking for authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentEmail = authentication.getName();
		    
		    currentStudent = spmsService.loadStudentByEmail(currentEmail);
		    
		    semData = spmsService.loadStudentSemData(currentEmail); 
		    
		}
		
		// for checking new notification===============================================================
		NotificationChecker notificationChecker = new NotificationChecker(currentStudent, spmsService);
		boolean newNotification = notificationChecker.check();
				
		theModel.addAttribute("newNotification", newNotification);
		//===========================================================================================

		// for checking new message===============================================================
		StudentMessageChecker messageChecker = new StudentMessageChecker(currentStudent, spmsService);
		boolean newMessage = messageChecker.check();
				
		theModel.addAttribute("newMessage", newMessage);
		//===========================================================================================

			
		theModel.addAttribute("assessment", semData);
		theModel.addAttribute("currentStudent", currentStudent);
		
		
		// if any or both the images is null or of greater size or any or both are not image file
		if((internal.getSize() <= 0 || internal.getSize() > 500000) || (previous.getSize() <= 0 || previous.getSize() > 500000)
			|| (!(internal.getContentType().equals("image/jpeg"))) || (!(previous.getContentType().equals("image/jpeg"))) ) {
			
			// one which is null or of larger size or is'nt image will get notified
			
			// if internal pic is not an image
			if( (!(internal.getContentType().equals("image/jpeg"))) ) {	
				theModel.addAttribute("internalMessage", "file must be jpg");
			}
			
			// if previous pic is not an image
			if( (!(previous.getContentType().equals("image/jpeg"))) ) {
				theModel.addAttribute("previousMessage", "file must be jpg");
			}

			// if internal pic is null or larger
			if(internal.getSize() <= 0 || internal.getSize() > 500000) {	
				theModel.addAttribute("internalMessage", "must be less than 500kb");	
			}
						
			// if previous pic is null or larger
			if(previous.getSize() <= 0 || previous.getSize() > 500000) {
				theModel.addAttribute("previousMessage", "must be less than 500kb");				
		    }
						
			
			theModel.addAttribute("semester", semester);
			theModel.addAttribute("internalPercent",internalPercent);
			theModel.addAttribute("previousPercent", previousPercent);
			
			return "student/student-marks-images";
		}
		
//		 if everything works out perfectly then
		
		//for appending unique name to images
		RandomAlphanumericGenerator generator = new RandomAlphanumericGenerator();
		
		String uniqueStringOne = generator.generate();
		String uniqueStringTwo = generator.generate();
		
		String newInternalFileName = uniqueStringOne+internal.getOriginalFilename();
		String newPreviousFileName = uniqueStringTwo+previous.getOriginalFilename();
		
		String pathOne = request.getRealPath("/")+"resources"+File.separator+"uploads"+File.separator+newInternalFileName;
		
		String pathTwo = request.getRealPath("/")+"resources"+File.separator+"uploads"+File.separator+newPreviousFileName;
		
		byte [] imageOne = internal.getBytes();
		
		byte[] imageTwo = previous.getBytes();
		
		try {
			FileOutputStream fosOne = new FileOutputStream(pathOne);
			fosOne.write(imageOne);
			fosOne.close();
		}catch (IOException e) {
			throw new FileOutException("Error in uploading File");
		}
		
		try {
			FileOutputStream fosTwo = new FileOutputStream(pathTwo);
			fosTwo.write(imageTwo);
			fosTwo.close();
		}catch (IOException e) {
			throw new FileOutException("Error in uploading File");
		}

		DecimalFormat df = new DecimalFormat("0.00");
		//instantiating StudentSemData
		StudentSemData tempSemData = new StudentSemData();
		tempSemData.setSemester(Integer.parseInt(semester));
		tempSemData.setOverallInternalAssessment( Float.parseFloat( df.format(  Float.parseFloat(internalPercent)  ) ) );
		tempSemData.setOverallPreviousAssessment(Float.parseFloat( df.format(  Float.parseFloat(previousPercent)  ) ) );
		tempSemData.setInternalPic(newInternalFileName);
		tempSemData.setExternalPic(newPreviousFileName);
		
		//uploading data
		spmsService.uploadMarks(tempSemData,currentStudent.getEmail());

		
		
		return "redirect:/student/studentMarks";
		
	}
	
	@GetMapping("/studentNotification")
	public String studentNotification(Model theModel) {
		
		Student currentStudent = new Student();
		
		//checking for authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentEmail = authentication.getName();
		    
		    currentStudent = spmsService.loadStudentByEmail(currentEmail);
		    
		}

		
		// for checking new message===============================================================
		StudentMessageChecker messageChecker = new StudentMessageChecker(currentStudent, spmsService);
		boolean newMessage = messageChecker.check();
				
		theModel.addAttribute("newMessage", newMessage);
		//===========================================================================================

		
		List<Notification> notificationList = spmsService.loadNotificationByBatchAndDept(currentStudent.getBatch(), currentStudent.getDept());
		
		if( !(notificationList.isEmpty()) ) {
			
			int notificationId = notificationList.get(0).getId();
			
			ReadNotification currentlyReadNotification = new ReadNotification();
			
			if(currentStudent.getReadNotification() != null) {
				
				currentlyReadNotification = currentStudent.getReadNotification();
			
			}
			
			currentlyReadNotification.setNotification_id(notificationId);
			
			currentStudent.addReadNotification(currentlyReadNotification);
			
			spmsService.updateStudent(currentStudent);
		}
		
		
		theModel.addAttribute("currentStudent", currentStudent);
		theModel.addAttribute("notificationList", notificationList);
		
		return "student/student-notification";
	}
	
	@GetMapping("/studentEvaluation")
	public String studentEvaluation(Model theModel) {
		
		Student currentStudent = new Student();
		 
		List<StudentSemData> semData = new ArrayList<StudentSemData>();
		
		//checking for authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentEmail = authentication.getName();
		    
		    currentStudent = spmsService.loadStudentByEmail(currentEmail);
		    
		    semData = spmsService.loadStudentSemData(currentEmail); 
		    
		}

		// for checking new notification===============================================================
		NotificationChecker notificationChecker = new NotificationChecker(currentStudent, spmsService);
		boolean newNotification = notificationChecker.check();
				
		theModel.addAttribute("newNotification", newNotification);
		//===========================================================================================

		// for checking new message===============================================================
		StudentMessageChecker messageChecker = new StudentMessageChecker(currentStudent, spmsService);
		boolean newMessage = messageChecker.check();
				
		theModel.addAttribute("newMessage", newMessage);
		//===========================================================================================

		
		theModel.addAttribute("semData", semData);
		theModel.addAttribute("currentStudent", currentStudent);
		
		return "student/student-evaluation";
		
	}
	
	@GetMapping("/inbox")
	public String inbox(Model theModel) {
				
		Student currentStudent = new Student();
		
		//checking for authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentEmail = authentication.getName();
		    
		    currentStudent = spmsService.loadStudentByEmail(currentEmail);
		    
		}

		// for checking new notification===============================================================
		NotificationChecker notificationChecker = new NotificationChecker(currentStudent, spmsService);
		boolean newNotification = notificationChecker.check();
				
		theModel.addAttribute("newNotification", newNotification);
		//============================================================================================
		
		List<MessageCenter> studentInbox = new ArrayList<MessageCenter>();
		
		studentInbox = spmsService.loadInboxByEmail(currentStudent.getEmail());
		
		//==========================updating read message===========================
		
		if( !(studentInbox.isEmpty()) ) {
			
			int messageId = studentInbox.get(0).getId();
			
			ReadStudentMessage currentlyReadMessage = new ReadStudentMessage();
			
			if(currentStudent.getReadStudentMessage() != null) {
				
				currentlyReadMessage = currentStudent.getReadStudentMessage();
			
			}
			
			currentlyReadMessage.setMessageId(messageId);
			
			currentStudent.addReadStudentMessage(currentlyReadMessage);
			
			spmsService.updateStudent(currentStudent);
		}
		
		//==========================================================================
			
		
		theModel.addAttribute("currentStudent", currentStudent);
		theModel.addAttribute("studentInbox", studentInbox);
		
		return "student/inbox";
	}
	
	@GetMapping("/outbox")
	public String outbox(Model theModel) {
		
		Student currentStudent = new Student();
		
		//checking for authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentEmail = authentication.getName();
		    
		    currentStudent = spmsService.loadStudentByEmail(currentEmail);
		    
		}
		
		// for checking new notification===============================================================
		NotificationChecker notificationChecker = new NotificationChecker(currentStudent, spmsService);
		boolean newNotification = notificationChecker.check();
				
		theModel.addAttribute("newNotification", newNotification);
		//===========================================================================================

		// for checking new message===============================================================
		StudentMessageChecker messageChecker = new StudentMessageChecker(currentStudent, spmsService);
		boolean newMessage = messageChecker.check();
				
		theModel.addAttribute("newMessage", newMessage);
		//===========================================================================================

		
		
		theModel.addAttribute("currentStudent", currentStudent);
		
		List<MessageCenter> studentOutbox = new ArrayList<MessageCenter>();
		
		studentOutbox = spmsService.loadOutboxByEmail(currentStudent.getEmail());
		
		theModel.addAttribute("studentOutbox", studentOutbox);
		
		return "student/outbox";
	}
	
	@GetMapping("/writeMessage")
	public String writeMessage(@RequestParam("toEmail") String toEmail,
							   Model theModel) {
		
		Student currentStudent = new Student();
		
		//checking for authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentEmail = authentication.getName();
		    
		    currentStudent = spmsService.loadStudentByEmail(currentEmail);
		    
		}
		
		// for checking new notification===============================================================
		NotificationChecker notificationChecker = new NotificationChecker(currentStudent, spmsService);
		boolean newNotification = notificationChecker.check();
				
		theModel.addAttribute("newNotification", newNotification);
		//===========================================================================================

		// for checking new message===============================================================
		StudentMessageChecker messageChecker = new StudentMessageChecker(currentStudent, spmsService);
		boolean newMessage = messageChecker.check();
				
		theModel.addAttribute("newMessage", newMessage);
		//===========================================================================================

		
		theModel.addAttribute("currentStudent", currentStudent);
		theModel.addAttribute("toEmail", toEmail);
		
		return "/student/write-message";
		
	}
	
	@PostMapping("/sendMessage")
	public String sendMessage(@RequestParam("toEmail") String toEmail,
							  @RequestParam("message") String message) {
		
		Student currentStudent = new Student();
		
		//checking for authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentEmail = authentication.getName();
		    
		    currentStudent = spmsService.loadStudentByEmail(currentEmail);
		    
		}
	

		MessageCenter newMessage = new MessageCenter();
		
		newMessage.setFromEmail(currentStudent.getEmail());
		newMessage.setToEmail(toEmail);
		newMessage.setMessage(message);
		
		spmsService.saveOrUpdateMessage(newMessage);
		
		
		
		return "redirect:/student/inbox";
	}
	
	

}
