package com.adnan.icode.fun.spms.controllers;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.adnan.icode.fun.spms.entity.Faculty;
import com.adnan.icode.fun.spms.entity.FacultyVerificationToken;
import com.adnan.icode.fun.spms.entity.Student;
import com.adnan.icode.fun.spms.entity.StudentVerificationToken;
import com.adnan.icode.fun.spms.helper.CookieCheckerRedirector;
import com.adnan.icode.fun.spms.models.TempFormPasswordModel;
import com.adnan.icode.fun.spms.models.TempPasswordModel;
import com.adnan.icode.fun.spms.service.SpmsService;

@Controller
@RequestMapping("/password")
public class PasswordController {
	
	@Autowired
	private SpmsService spmsService;
	
	@Autowired
	private CookieCheckerRedirector checkerRedirector;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor editor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, editor);
	}
	
	@GetMapping("/forgotPassword")
	public String passwordForgetPage(Model theModel,
			HttpServletRequest request, HttpServletResponse response) {
		
		// redirector if logged in==========================================
		
		String redirect = checkerRedirector.redirect(request, response);
		
		if (redirect != null) {
			return redirect;
		}
	    //====================================================================
		
		theModel.addAttribute("tempPasswordModel", new TempPasswordModel());
		
		return "password/forgot-page";
		
	}
	
	@GetMapping("/processing")
	public String processingPasswordForm(
			@Valid @ModelAttribute("tempPasswordModel") TempPasswordModel thePasswordModel,
			BindingResult theBindingResult,
			Model theModel,
			HttpServletRequest request, HttpServletResponse response) {

		// redirector if logged in==========================================
		
		String redirect = checkerRedirector.redirect(request, response);
		
		if (redirect != null) {
			return redirect;
		}
	    //====================================================================

		
		
		Student theStudent = new Student();
		theStudent = null;
		
		Faculty theFaculty = new Faculty();
		theFaculty = null;
		
		if(theBindingResult.hasErrors()) {
			return "password/forgot-page";
		}else {
			
			if(thePasswordModel.getRole().equals("student")) {
				theStudent = spmsService.loadStudentByEmail(thePasswordModel.getEmail().toLowerCase());
				theModel.addAttribute("user", theStudent);

			}
			
			if(thePasswordModel.getRole().equals("faculty")) {
				theFaculty= spmsService.loadFacultyByEmail(thePasswordModel.getEmail().toLowerCase());
				theModel.addAttribute("user", theFaculty);
			}
			
			if(theStudent == null && theFaculty == null) {
				theModel.addAttribute("emailExist", "email doesn't exists");
				
				return "password/forgot-page";
				
			}else {
				theModel.addAttribute("thePasswordModel", thePasswordModel);
				return "password/checking-user";
				
				
			}
		
		}
			
	}
	
	@GetMapping("/sendPasswordToken")
	public String sendPasswordToken(
			@ModelAttribute("thePasswordModel") TempPasswordModel thePasswordModel,
			@RequestParam("email") String email,
			HttpServletRequest request, HttpServletResponse response) {

		// redirector if logged in==========================================
		 
		String redirect = checkerRedirector.redirect(request, response);
		
		if (redirect != null) {
			return redirect;
		}
	    //====================================================================
	
		spmsService.sendPasswordToken(thePasswordModel,request);
		
		return "password/password-confirmation";
		
	}
	
	// STUDENT CONFIRMATION PROCESSOR
	@GetMapping("/studentConfirmation")
	public String studentConfirmation(@RequestParam("token") String token,
										Model theModel) {
		Date currentDate = null;
		Date expiryDate = null;
		
		StudentVerificationToken studentToken = spmsService.findStudentToken(token);
		
		if(studentToken != null ) {
			currentDate = new Date();
			expiryDate = studentToken.getExpiryDate();
			
			if(expiryDate.getTime() - currentDate.getTime() <= 0) {
				// if expired
				
				return "information/invalid-verification";
				
			}else if(expiryDate.getTime() - currentDate.getTime() > 0) {
				//if not expired
				String studentEmail = studentToken.getStudent().getEmail();
				Student tempStudent = spmsService.loadStudentByEmail(studentEmail);
				
				theModel.addAttribute("passwordModel", new TempFormPasswordModel());
				theModel.addAttribute("email", tempStudent.getEmail());
				theModel.addAttribute("changeType", "student");
				
				return "password/change-password";
			}
			
		}
		
		return "information/invalid-verification";
	}
	
	// FACULTY CONFIRMATION PROCESSOR
	@GetMapping("/facultyConfirmation")
	public String facultyConfirmation(@RequestParam("token") String token,
										Model theModel) {
		Date currentDate = null;
		Date expiryDate = null;
		
		FacultyVerificationToken facultyToken = spmsService.findFacultyToken(token);
		
		if(facultyToken != null ) {
			currentDate = new Date();
			expiryDate = facultyToken.getExpiryDate();
			
			if(expiryDate.getTime() - currentDate.getTime() <= 0) {
				//if expired
				
				return "information/invalid-verification";
				
			}else if(expiryDate.getTime() - currentDate.getTime() > 0) {
				//if not expired
				
				String facultyEmail = facultyToken.getFaculty().getEmail();
				Faculty tempFaculty = spmsService.loadFacultyByEmail(facultyEmail);

				
				theModel.addAttribute("passwordModel", new TempFormPasswordModel());
				theModel.addAttribute("email", tempFaculty.getEmail());
				theModel.addAttribute("changeType", "faculty");
				
				return "password/change-password";
			}
			
		}
		
		return "information/invalid-verification";
	}
	
	@GetMapping("/change")
	public String changePassword(
			@Valid @ModelAttribute("passwordModel") TempFormPasswordModel passwordModel,
			BindingResult theBindingResult,
			@RequestParam("email") String email,
			@RequestParam("changeType") String changeType,
			Model theModel) {
		
		if(theBindingResult.hasErrors()) {
			
			theModel.addAttribute("email", email);
			theModel.addAttribute("changeType", changeType);
			return "password/change-password";
		}
		
		spmsService.savePassword(passwordModel, email, changeType);
		
		
		return "password/change-password-successful";
	}
	

	


}
