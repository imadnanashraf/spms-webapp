package com.adnan.icode.fun.spms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.adnan.icode.fun.spms.service.FacultyLoginService;
import com.adnan.icode.fun.spms.successhandler.CustomFacultySuccessHandler;

@Configuration
@EnableWebSecurity
@Order(2)
public class SpmsSecurityFacultyConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private FacultyLoginService facultyLoginService;
	
	@Autowired
	private CustomFacultySuccessHandler customFacultySuccessHandler;
	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.authenticationProvider(facultyAuthenticationProvider());
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider facultyAuthenticationProvider() {
		
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(facultyLoginService);
		auth.setPasswordEncoder(bCryptPasswordEncoder());
		
		return auth;
	}


	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		
		http
		.antMatcher("/faculty/**")
		.authorizeRequests()
		.antMatchers("/faculty/**").hasRole("FACULTY")
		.and()
		.formLogin()
		.loginPage("/faculty/facultyLogin")
		.loginProcessingUrl("/faculty/authenticateTheFaculty")
		.successHandler(customFacultySuccessHandler)
		.usernameParameter("email") 
		.passwordParameter("password")
		.permitAll()
		.and()
		.rememberMe().userDetailsService(facultyLoginService)
		.alwaysRemember(true)
		.rememberMeCookieName("f_user")
		.and()
		.logout()
		.logoutUrl("/faculty/logout")
		.permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/student/studentAccessDenied");

				}

	
	
	

}
