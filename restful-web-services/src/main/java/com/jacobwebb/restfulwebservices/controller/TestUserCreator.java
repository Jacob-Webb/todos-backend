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
import com.jacobwebb.restfulwebservices.entity.Todo;
import com.jacobwebb.restfulwebservices.entity.User;
import com.jacobwebb.restfulwebservices.entity.UserContact;
import com.jacobwebb.restfulwebservices.service.TodoHardcodedService;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class TestUserCreator {

	@Autowired
	private UserJpaRepository repository;
	
	@GetMapping("/jpa/users")
	public List<User> getAllUsers() {
		return repository.findAll();
		//return todoService.findAll();
	}
	
	@GetMapping("/jpa/users/{username}")
	public User getUser(@PathVariable String username) {
		return repository.findByUsername(username);
		//return todoService.findById(id);
	}
	
//	@DeleteMapping("/jpa/users/{username}/todos/{id}")
//	public ResponseEntity<Void> deleteTodo(
//		@PathVariable String username, @PathVariable long id) {
//		
//		todoJpaRepository.deleteById(id);
//		
//		return ResponseEntity.noContent().build();
//	}
	
//	@PutMapping("/jpa/users/{username}/todos/{id}")
//	public ResponseEntity<Todo> updateTodo(
//			@PathVariable String username,
//			@PathVariable long id, @RequestBody Todo todo) {
//		
//		Todo todoUpdated = todoJpaRepository.save(todo);
//		
//		return new ResponseEntity<Todo>(todo, HttpStatus.OK);
//	}
	
	// Create a new Todo
	//POST
	@PostMapping("/jpa/users")
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
