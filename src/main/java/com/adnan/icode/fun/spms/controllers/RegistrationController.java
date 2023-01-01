package com.adnan.icode.fun.spms.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.hibernate.internal.build.AllowSysOut;
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

import com.adnan.icode.fun.spms.entity.Faculty;
import com.adnan.icode.fun.spms.entity.Student;
import com.adnan.icode.fun.spms.helper.CookieCheckerRedirector;
import com.adnan.icode.fun.spms.helper.SessionCheckerRedirector;
import com.adnan.icode.fun.spms.models.TempRegisterModel;
import com.adnan.icode.fun.spms.service.SpmsService;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
	
	@Autowired
	private CookieCheckerRedirector checkerRedirector;
	
	@Autowired
	private SpmsService spmsService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor trimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, trimmerEditor);
	}
	
	@GetMapping("/registerNewUser")
	public String registerForm(Model theModel,
			HttpServletRequest request, HttpServletResponse response) {
		// redirector if logged in==========================================
		
		String redirect = checkerRedirector.redirect(request, response);
		
		if (redirect != null) {
			return redirect;
		}
	    //====================================================================
		
		theModel.addAttribute("tempRegisterModel", new TempRegisterModel());
		
		return "register/register-form";
	}
	
	@GetMapping("/processing")
	public String processingRegistrationForm(
			@Valid @ModelAttribute("tempRegisterModel") TempRegisterModel theTempRegisterModel,
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
	
		if (theBindingResult.hasErrors()) {
			return "register/register-form";
		}else {
			
			if(theTempRegisterModel.getBasicRole().equals("student")) {
				theStudent = spmsService.loadStudentByEmail(theTempRegisterModel.getEmail());	
			}
			
			if(theTempRegisterModel.getBasicRole().equals("faculty")) {
				theFaculty = spmsService.loadFacultyByEmail(theTempRegisterModel.getEmail());
			}
			
			if(theStudent == null && theFaculty == null) {
				//successfull
				spmsService.registrationNewUser(theTempRegisterModel, request);
				
			}else {
				theModel.addAttribute("emailExists", "email already exists");
				
				return "register/register-form";
		}
		
		}
				
			
				theModel.addAttribute("role",theTempRegisterModel.getBasicRole().toUpperCase());
				return "register/registration-confirmation";
		
	}
	
	

}
