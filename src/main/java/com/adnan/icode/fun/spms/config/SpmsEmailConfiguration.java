package com.adnan.icode.fun.spms.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource({"classpath:email-config.properties"})
public class SpmsEmailConfiguration {
	
	@Value("${mail.host}") 
	private String host;
	
	@Value("${mail.port}") 
	private String port;
    
	@Value("${mail.username}") 
    private String username;
    
	@Value("${mail.password}") 
    private String password; 
	
	@Value("${mail.transport.protocol}") 
	private String protocol;
	
	@Value("${mail.smtp.auth}") 
	private String auth;
    
	@Value("${mail.smtp.starttls.enable}") 
    private String tlsEnable;
    
	@Value("${mail.debug}") 
    private String debug; 
	
	@Value("${mail.smtp.socketFactory.fallback}")
	private String fallback;
	

	@Bean
	public JavaMailSender getJavaMailSender() {
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(host);
		mailSender.setPort(Integer.parseInt(port));
		mailSender.setUsername(username);
		mailSender.setPassword(password);	
		mailSender.setJavaMailProperties(getProperties());
		
		return mailSender;
		
	}
	
	private Properties getProperties() {
		
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", protocol );
		props.setProperty("mail.smtp.auth", auth );
		props.setProperty("mail.smtp.starttls.enable", tlsEnable);
		props.setProperty("mail.debug", debug);
		props.setProperty("mail.smtp.socketFactory.fallback", fallback);
		
		return props;
	}

	

}
