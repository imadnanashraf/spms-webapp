package com.adnan.icode.fun.spms.successhandler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.adnan.icode.fun.spms.entity.Faculty;
import com.adnan.icode.fun.spms.service.SpmsService;

@Component
public class CustomFacultySuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private SpmsService spmsService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// authentication will provide username, password, roles intially provided by
		// USER DETAIL SERVICE
		// if username parameter in security config wasn't changed then
		// authentication.getName() will provide username given at the time of login
		// in this case username parameter is changed to email
		// thus in security tag as well as in this class username or get name will 
		//provide email
		
//		String email = authentication.getName();
//		
//		Faculty theFaculty = spmsService.loadFacultyByEmail(email);
//		
//		HttpSession session = request.getSession();
//		
//		session.setAttribute("loggedFaculty", theFaculty);
		
		// NOT NEEDED AS INFO CAN BE ACCCESSED BY USING SECURITYCONTEXTHOLDER

		String email = authentication.getName();
		
		Faculty theFaculty = spmsService.loadFacultyByEmail(email);
		
		spmsService.deleteAllFacultyTokens(theFaculty);
		
		response.sendRedirect(request.getContextPath() + "/faculty/facultyPage");
		

	}

}
