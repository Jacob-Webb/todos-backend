package com.jacobwebb.restfulwebservices.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.jacobwebb.restfulwebservices.model.Contact;
import com.jacobwebb.restfulwebservices.model.Todo;
import com.jacobwebb.restfulwebservices.model.User;

@CrossOrigin(origins="${crossOrigin}")
@RestController
public class UserController {

	@Autowired
	private UserJpaRepository repository;
	
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
	
}
