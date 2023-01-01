package com.adnan.icode.fun.spms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.adnan.icode.fun.spms.service.StudentLoginService;
import com.adnan.icode.fun.spms.successhandler.CustomStudentSuccessHandler;

@Configuration
@EnableWebSecurity
@Order(1)
public class SpmsSecurityStudentConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private StudentLoginService studentLoginService;
	
	@Autowired
	private CustomStudentSuccessHandler customStudentSuccessHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.authenticationProvider(studentAuthenticationProvider());
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider studentAuthenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService((UserDetailsService)studentLoginService);
		auth.setPasswordEncoder(bCryptPasswordEncoder());
		
		return auth;
	}
	
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		
		http.antMatcher("/student/**")
		.authorizeRequests()
		.antMatchers("/student/**").hasRole("STUDENT")
		.and()
		.formLogin()
		.loginPage("/student/studentLogin")
		.loginProcessingUrl("/student/authenticateTheStudent")
		.successHandler(customStudentSuccessHandler)
		.usernameParameter("email")
		.passwordParameter("password")
		.permitAll()
		.and()
		.rememberMe().userDetailsService(studentLoginService)
		.alwaysRemember(true)
		.rememberMeCookieName("s_user")
		.and() 
		.logout()
		.logoutUrl("/student/logout")
		.permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/faculty/facultyAccessDenied");  
		// exceptional handling if Faculty uses student page
		
		
	}
	
	

}
