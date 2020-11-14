package com.jacobwebb.restfulwebservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jacobwebb.restfulwebservices.dao.UserJpaRepository;

/*
 * Runs the application
 */

@SpringBootApplication
public class RestfulWebServicesApplication{
	
	@Autowired
	UserJpaRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(RestfulWebServicesApplication.class, args);
	}

}
