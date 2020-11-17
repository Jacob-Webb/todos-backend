package com.jacobwebb.restfulwebservices.controller;

import java.net.URI;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jacobwebb.restfulwebservices.dao.TodoJpaRepository;
import com.jacobwebb.restfulwebservices.dao.UserJpaRepository;
import com.jacobwebb.restfulwebservices.model.Todo;
import com.jacobwebb.restfulwebservices.model.User;
import com.jacobwebb.restfulwebservices.service.EmailSenderService;
import com.jacobwebb.restfulwebservices.service.UserDetailsServiceImpl;

@CrossOrigin(origins="${crossOrigin}")
@RestController
public class TestController {
	
	@Autowired
	EmailSenderService emailSenderService;
	
	@Autowired
	private UserJpaRepository userRepository;
	
	@Autowired
	UserDetailsServiceImpl userService;
	
	public class GenericResponse {
	    private String message;
	    private String error;
	 
	    public GenericResponse(String message) {
	        super();
	        this.message = message;
	    }
	 
	    public GenericResponse(String message, String error) {
	        super();
	        this.message = message;
	        this.error = error;
	    }
	}
	
	/*
	 * Create a new Todo
	 */
	@PostMapping("/test")
	public String apiTest() {
		
		return "You made it through";
	}
	
}