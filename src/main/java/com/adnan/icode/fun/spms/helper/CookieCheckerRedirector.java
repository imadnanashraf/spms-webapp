package com.adnan.icode.fun.spms.helper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component
public class CookieCheckerRedirector {
	
	public String redirect(HttpServletRequest request, HttpServletResponse response) {
		
		String s_user = null;
		String f_user = null;
		String redirect = null;
		
		Cookie[] user = request.getCookies();
		
		for(Cookie tempCookie: user) {
			
			if("s_user".equals(tempCookie.getName())) {
				
				s_user = tempCookie.getValue();
			}
			if("f_user".equals(tempCookie.getName())) {
				
				f_user = tempCookie.getValue();
			}
			
		}
		
		
		if(s_user != null) {
			
			redirect = "redirect:/student/studentPage";
		
		} if(f_user != null) {
			
			redirect = "redirect:/faculty/facultyPage";
	
		}else  {
			
			HttpSession session = request.getSession();
			session.invalidate();	
			
			}
	
		
		return redirect;
		
	}

}
