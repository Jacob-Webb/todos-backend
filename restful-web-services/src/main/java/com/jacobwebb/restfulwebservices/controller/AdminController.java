package com.jacobwebb.restfulwebservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jacobwebb.restfulwebservices.service.UserService;

@RestController
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/admin/users")
	public ResponseEntity<?> findAllUsers() {
		return ResponseEntity.ok(userService.findAllUsers());
	}

}
