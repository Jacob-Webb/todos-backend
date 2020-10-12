package com.jacobwebb.restfulwebservices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailSenderService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

}
