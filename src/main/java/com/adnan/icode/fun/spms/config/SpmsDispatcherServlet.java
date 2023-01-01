package com.adnan.icode.fun.spms.config;

import javax.servlet.ServletRegistration.Dynamic;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpmsDispatcherServlet extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] {SpmsConfiguration.class};
	} 
 
	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String [] {"/"};
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		 boolean done = registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
		
	}

	
	
	
}
