package com.jacobwebb.restfulwebservices.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jacobwebb.restfulwebservices.dao.UserJpaRepository;
import com.jacobwebb.restfulwebservices.model.User;

@CrossOrigin(origins="${crossOrigin}")
@RestController
public class UserController {

	@Autowired
	private UserJpaRepository userRepository;
	
	@PostMapping("/user/registration")
	public ResponseEntity<?> register(@RequestBody User user) {
		if(userRepository.findByUsername(user.getUsername()) != null) {
			return new ResponseEntity<> (HttpStatus.CONFLICT);
		}
		//user.setRole(Role.USER);
		return new ResponseEntity<> (userRepository.save(user), HttpStatus.CREATED);
	}
	
	@GetMapping("/user/login")
	public ResponseEntity<?> login(Principal principal) {
		if(principal == null)
			//This should be ok http status because this will be used for input path
			return ResponseEntity.ok(principal);
		
		return new ResponseEntity<>(userRepository.findByUsername(principal.getName()), HttpStatus.OK);
	}
	
	
	
	
	/*
	
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return repository.findAll();
		//return todoService.findAll();
	}
	
	@GetMapping("/users/{id}")
	public User getUser(@PathVariable long id) {
		return repository.findById(id).get();
		//return todoService.findById(id);
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> deleteUser(
		@PathVariable String username, @PathVariable long id) {
		
		repository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(
			@PathVariable long id, @RequestBody User user) {
		
		User userUpdated = repository.save(user);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	// Create a new Todo
	//POST
	@PostMapping("/users")
	public ResponseEntity<Void> createUser(@RequestBody User user) {

		User createUser = repository.save(user);
		
		// Location
		// Get current resource url
		// users/{username}/todos/{id}
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
			path("/{id}").buildAndExpand(createUser.getUserId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	*/
	
}
