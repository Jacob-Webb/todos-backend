package com.jacobwebb.restfulwebservices.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//Controller
@RestController
@CrossOrigin(origins="${crossOrigin}")
public class HelloWorldController {
	
	public class HelloWorldBean {
		
		private String message;
		
		public HelloWorldBean(String message) {
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
	
	// get
	//Uri - /hello-world
	//method - "Hello World"
	@GetMapping(path="/hello-world")
	public String helloWorld() {
		
		return "helloWorld";
	}
	
	//hello-world-bean
	@GetMapping(path="/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		
		//return new HelloWorldBean("Hello World Java Service");
		throw new RuntimeException("Some Error has happened");
	}
	
	//hello-world-bean
	@GetMapping(path="/hello-world-bean/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		
		//throw new RuntimeException("Some Error has happened");
		return new HelloWorldBean(String.format("Hello World, %s", name));
	}

}
