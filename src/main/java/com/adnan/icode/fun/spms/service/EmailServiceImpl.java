package com.adnan.icode.fun.spms.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.adnan.icode.fun.spms.exception.EmailSendingException;
@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	

	@Override
	@Async
	public void sendEmail(String email, String mailContent, String sender) {
		
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setFrom(sender);
			helper.setTo(email);
			helper.setSubject("VERIFY");
			helper.setText(mailContent, true);
		} catch (MessagingException e) {
			System.out.println("Error in loading email");
		}
		
		try {
		javaMailSender.send(message);
		
		}catch (Exception e) {
			throw new EmailSendingException("Server Error");
		}
			
	}

}
