package com.adnan.icode.fun.spms.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.adnan.icode.fun.spms.entity.Faculty;
import com.adnan.icode.fun.spms.entity.Student;
@Component
public class SessionCheckerRedirector {
	
	public String redirector(HttpServletRequest request) {
		
		String redirect = null;
		
		HttpSession session = request.getSession();
		
		//getting the session to redirect if user is still logged in
		Student theStudent = (Student) session.getAttribute("loggedStudent");
		Faculty theFaculty = (Faculty) session.getAttribute("loggedFaculty");
		
		if(theStudent != null) {
			
			redirect = "redirect:/student/studentPage";
			
			return redirect;
		}
		
		if(theFaculty != null) {
			redirect = "redirect:/faculty/facultyPage";
			return redirect;
		}
		
		return redirect;
		
	}

}
