package com.adnan.icode.fun.spms.controllers;

import javax.servlet.ServletException;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import com.adnan.icode.fun.spms.exception.EmailSendingException;
import com.adnan.icode.fun.spms.exception.FileOutException;
import com.adnan.icode.fun.spms.exception.NoMappingException;

@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler(EmailSendingException.class)
	public String emailSendingError() {
		
		return "information/server-error";
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String missingServletRequest() {
		
		return "information/page-not-found";
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public String invalidPage() {
		
		return "information/page-not-found";
	}
	
	@ExceptionHandler(FileOutException.class)
	public String errorUploading() {
		
		return "information/uploading-error";
	}
	

}
