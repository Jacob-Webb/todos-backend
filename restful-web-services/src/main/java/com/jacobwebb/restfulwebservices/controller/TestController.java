package com.jacobwebb.restfulwebservices.controller;

import java.net.URI;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jacobwebb.restfulwebservices.dao.TodoJpaRepository;
import com.jacobwebb.restfulwebservices.dao.UserJpaRepository;
import com.jacobwebb.restfulwebservices.model.Todo;
import com.jacobwebb.restfulwebservices.model.User;
import com.jacobwebb.restfulwebservices.service.EmailSenderService;

@CrossOrigin(origins="${crossOrigin}")
@RestController
public class TestController {
	
	@Autowired
	EmailSenderService emailSenderService;
	
	/*
	 * Create a new Todo
	 */
	@PostMapping("/test")
	public ResponseEntity<Void> testEmail() {
		
		final SimpleMailMessage mailMessage = new SimpleMailMessage(); 
    	
    	mailMessage.setTo("sanctifyd83@yahoo.com");
    	mailMessage.setSubject("Todo Confirmation Link!");
    	mailMessage.setFrom("${spring.mail.username}");
    	mailMessage.setText(emailSenderService.getFrontendUrl() + "/login/");
    	
    	emailSenderService.sendEmail(mailMessage);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
}