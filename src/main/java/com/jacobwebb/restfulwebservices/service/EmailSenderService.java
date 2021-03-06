package com.jacobwebb.restfulwebservices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
public class EmailSenderService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${frontend.url}")
	private String frontendUrl;
	
	public EmailSenderService() {
		
	}
	
	@Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }
	
	public String getFrontendUrl() {
		return frontendUrl;
	}

}
