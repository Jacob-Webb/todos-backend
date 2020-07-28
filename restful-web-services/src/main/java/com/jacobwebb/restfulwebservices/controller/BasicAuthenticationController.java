package com.jacobwebb.restfulwebservices.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.jacobwebb.restfulwebservices.basic.AuthenticationBean;

//Controller
@RestController
@CrossOrigin(origins="${crossOrigin}")
public class BasicAuthenticationController {
	
	private class AuthenticationBean {
		
		private String message;
		
		public AuthenticationBean(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		@Override
		public String toString() {
			return "HelloWorldBean [message=" + message + "]";
		}
		
	}
	
	@GetMapping(path="/basicauth")
	public AuthenticationBean helloWorldBean() {
	
		return new AuthenticationBean("You are authenticated");
	}

}
