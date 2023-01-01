package com.adnan.icode.fun.spms.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.adnan.icode.fun.spms.entity.Faculty;
import com.adnan.icode.fun.spms.entity.FacultyVerificationToken;
import com.adnan.icode.fun.spms.entity.Student;
import com.adnan.icode.fun.spms.entity.StudentVerificationToken;
import com.adnan.icode.fun.spms.helper.CookieCheckerRedirector;
import com.adnan.icode.fun.spms.helper.SessionCheckerRedirector;
import com.adnan.icode.fun.spms.service.SpmsService;


@Controller
@RequestMapping("/start")
public class StartController {
	
	@Autowired
	private CookieCheckerRedirector checkerRedirector;
	
	@Autowired
	private SpmsService spmsService;
	
	@GetMapping("/homePage")
	public String homePage(HttpServletRequest request, HttpServletResponse response) {
		// redirector if logged in==========================================
		
		String redirect = checkerRedirector.redirect(request, response);
		
		if (redirect != null) {
			return redirect;
		}
	    //====================================================================
		 
		return "general/welcome";
	}
	
	@GetMapping("/about")
	public String about(HttpServletRequest request, HttpServletResponse response,
						Model theModel) {
		// redirector if logged in==========================================
		
		String redirect = checkerRedirector.redirect(request, response);
		
		if (redirect != null) {
			return redirect;
		}
	    //====================================================================
		 List<Integer> picNumber = new ArrayList<Integer>();
		 for(int i = 1; i<= 17; i++) {
			 picNumber.add(i);
		 }
		 theModel.addAttribute("picNumber", picNumber);
		 
		return "general/about";
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
				System.out.println("not expired");
				String studentEmail = studentToken.getStudent().getEmail();
				Student tempStudent = spmsService.loadStudentByEmail(studentEmail);
				
				tempStudent.setEnabled(true);
				spmsService.updateStudent(tempStudent);
				
				spmsService.deleteAllStudentTokens(tempStudent);
				theModel.addAttribute("email", studentEmail);
				
				return "information/account-verified";
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
				// if expired
				
				return "information/invalid-verification";
				
			}else if(expiryDate.getTime() - currentDate.getTime() > 0) {
				//if not expired
				//then delete all the verification token 
				//of faculty
				String facultyEmail = facultyToken.getFaculty().getEmail();
				Faculty tempFaculty = spmsService.loadFacultyByEmail(facultyEmail);
				
				spmsService.deleteAllFacultyTokens(tempFaculty);
				theModel.addAttribute("email", facultyEmail);
				
				return "information/account-verified";
			}
			
		}
		
		return "information/invalid-verification";
	}
	
	// renew email verification link
	@GetMapping("/renewEmail")
	public String renewStudentEmailLink(
			@RequestParam("email") String email,
			@RequestParam("account") String account,
			HttpServletRequest request) {
		
		if(account.equals("student")) {
			Student theStudent = spmsService.loadStudentByEmail(email);
			
			if(theStudent != null && !theStudent.getEnabled()){
				
					spmsService.newEmailToken(email, account, request);
					
					return "information/email-link-renewed";
				
			}
			
		}
		
		if(account.equals("faculty")) {
			Faculty theFaculty = spmsService.loadFacultyByEmail(email);
			
			if(theFaculty != null && !theFaculty.getEnabled()){
				
				spmsService.newEmailToken(email, account, request);
				
				return "information/email-link-renewed";
			}
			
		}
		
		
		
		
		return "information/invalid-verification";
		
	}
	
	

}
