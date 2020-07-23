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

import com.jacobwebb.restfulwebservices.dao.UserContactJpaRepository;
import com.jacobwebb.restfulwebservices.entity.Contact;
import com.jacobwebb.restfulwebservices.entity.Todo;
import com.jacobwebb.restfulwebservices.service.TodoHardcodedService;

@CrossOrigin(origins="${crossOrigin}")
@RestController
public class ContactController {
	
	@Autowired
	private UserContactJpaRepository repository;
	
	@GetMapping("/contacts")
	public List<Contact> getAllContacts() {
		return repository.findAll();
		//return todoService.findAll();
	}
/*	
	@GetMapping("/users/{username}/todos/{id}")
	public Todo getTodo(@PathVariable String username, @PathVariable long id) {
		return repository.findById(id).get();
		//return todoService.findById(id);
	}
	
	@DeleteMapping("/users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteTodo(
		@PathVariable String username, @PathVariable long id) {
		
		//Todo todo = todoService.deleteById(id);
		repository.deleteById(id);
		
		return ResponseEntity.noContent().build();
		
//		if(todo != null) {
//			return ResponseEntity.noContent().build();
//		}
//		
//		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/users/{username}/todos/{id}")
	public ResponseEntity<Todo> updateTodo(
			@PathVariable String username,
			@PathVariable long id, @RequestBody Todo todo) {
		
		//Todo todoUpdated = todoService.save(todo);
		Todo todoUpdated = repository.save(todo);
		
		return new ResponseEntity<Todo>(todo, HttpStatus.OK);
	}
*/	
	// Create a new Todo
	//POST
	@PostMapping("/contacts")
	public ResponseEntity<Void> createContact(@RequestBody Contact contact) {
		
		//Todo createdTodo = todoService.save(todo);
		Contact createdContact = repository.save(contact);
		
		// Location
		// Get current resource url
		// users/{username}/todos/{id}
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
			path("/{contact_id}").buildAndExpand(createdContact.getContactId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
}
