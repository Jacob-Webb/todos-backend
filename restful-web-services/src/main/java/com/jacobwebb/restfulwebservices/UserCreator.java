package com.jacobwebb.restfulwebservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.jacobwebb.restfulwebservices.dao.UserContactJpaRepository;
import com.jacobwebb.restfulwebservices.dao.UserJpaRepository;
import com.jacobwebb.restfulwebservices.entity.Todo;
import com.jacobwebb.restfulwebservices.entity.User;
import com.jacobwebb.restfulwebservices.entity.UserContact;

public class UserCreator {
	
	@Autowired
	private UserJpaRepository userJpaRepository;
	
	@Autowired
	private UserContactJpaRepository contactJpaRepository;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	String encryptedPassword = encoder.encode("My1birthday");
	
	User newUser = new User("JacobW", encryptedPassword, "Jacob", "Webb", "Admin");
	
	UserContact contact = new UserContact("sanctifyd83@yahoo.com", "(909) 322-5267");
	
	User userCreated = userJpaRepository.save(newUser);
	
	public static void main(String[] args) {

		
	}

}
