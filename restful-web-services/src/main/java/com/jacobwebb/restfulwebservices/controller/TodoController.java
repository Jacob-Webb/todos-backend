package com.jacobwebb.restfulwebservices.controller;

import java.net.URI;
import java.util.Collection;
import java.util.HashSet;
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
import com.jacobwebb.restfulwebservices.model.Todo;
import com.jacobwebb.restfulwebservices.model.User;

@CrossOrigin(origins="${crossOrigin}")
@RestController
public class TodoController {
	
	@Autowired
	private TodoJpaRepository todoJpaRepository;
	
	@Autowired
	private UserJpaRepository userRepository;
	
	
	/*
	 * Create a new Todo
	 */
	@PostMapping("/todos/{username}")
	public ResponseEntity<Void> createTodo(
			@PathVariable String username, @RequestBody Todo todo) {
		
		// if user exists
		User user = userRepository.findByUsername(username);
		if (user != null) {
			todo.setUser(user);
			Todo createdTodo = todoJpaRepository.save(todo);
			
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
					path("/{id}").buildAndExpand(createdTodo.getId()).toUri();
				
				return ResponseEntity.created(uri).build();
		}
		
		// Otherwise, return Not Found (404)
		return ResponseEntity.notFound().build();
	}
	
	/*
	 * Return all the todos associated with the User of {username}
	 */
	@GetMapping("/todos/{username}")
	public Collection<Todo> getAllTodos(@PathVariable String username) {
		
		// Check that user isn't null before trying to get the Todo list
		User user = userRepository.findByUsername(username);
		if (user != null) {
			return user.getTodos();
		} else return null;
	}
	
	/*
	 * Find and return the id via the User's list of Todos
	 */
	@GetMapping("/todos/{username}/{id}")
	public Todo getTodo(@PathVariable String username, @PathVariable long id) {
	
		// if the user exists, look throught the Todos
		User user = userRepository.findByUsername(username);
		if (user != null) {
			for (Todo todo: user.getTodos()) {
				// If the identified todo exists, return it.
				if (todo.getId() == id) {
					return todo;
				}
			}
		// Otherwise, return null
		} return null;
		
	}
	
	/*
	 * Update a Todo
	 */
	@PutMapping("/todos/{username}/{id}")
	public ResponseEntity<Todo> updateTodo( @PathVariable String username,
											@PathVariable long id, 
											@RequestBody Todo todo) {
		
		User user = userRepository.findByUsername(username);
		if (user != null) {
			Todo todoUpdated = user.getTodo(id);
			
			if (todoUpdated != null) {
				todoUpdated = todo;
				
				// reset User because @param todo doesn't have a User and it can't be null. 
				todoUpdated.setUser(user);
				todoJpaRepository.save(todoUpdated);
				return new ResponseEntity<Todo>(todoUpdated, HttpStatus.OK);
			} 
		} 
		
		return new ResponseEntity<Todo>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/todos/{username}/{id}")
	public ResponseEntity<Void> deleteTodo(
		@PathVariable String username, @PathVariable long id) {
		
		User user = userRepository.findByUsername(username);
		if (user != null) {
			Collection<Todo> todos = user.getTodos();
			for (Todo todo: todos) {
				if (todo.getId() == id) {
					todos.remove(todo);
					user.setTodos(todos);
					userRepository.save(user);
					return ResponseEntity.noContent().build();
				}
			}
		}
		return ResponseEntity.notFound().build();
	}

}
