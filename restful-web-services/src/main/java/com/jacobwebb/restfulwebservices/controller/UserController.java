 package com.jacobwebb.restfulwebservices.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jacobwebb.restfulwebservices.dao.RoleRepository;
import com.jacobwebb.restfulwebservices.dao.UserJpaRepository;
import com.jacobwebb.restfulwebservices.model.Role;
import com.jacobwebb.restfulwebservices.model.Todo;
import com.jacobwebb.restfulwebservices.model.User;

@CrossOrigin(origins="${crossOrigin}")
@RestController
public class UserController {

	@Autowired
	private UserJpaRepository userRepository;
	
	@Autowired 
	RoleRepository roleRepository;
	
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }
	
	/*
	 * Create User with generic role
	 */
    
	@PostMapping("/users") 
	public ResponseEntity<?> createWithOutRole(@RequestBody User user) {
		//Role role = new Role("ROLE_USER");
		
		user.addRole(roleRepository.findByName("ROLE_USER"));
		
		return createUser(user);
	}
	
	/*
	 * Create User with a role
	 */
	@PostMapping("/users/{role}") 
	public ResponseEntity<?> createWithRole(@RequestBody User user, @PathVariable String role) {
		
		// Check if role exists and add it
		String userRole = "ROLE_" + role.toUpperCase();
		if (roleRepository.findByName(userRole) != null) {
		  user.addRole(roleRepository.findByName(userRole));
		} else {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		return createUser(user);
	}
	
	/*
	 *  Returned logged in user 
	 */
	@GetMapping("/users/login")
	public ResponseEntity<?> login(Principal principal) {
		if(principal == null)
			//This should be ok http status because this will be used for input path
			return ResponseEntity.ok(principal);
		
		return new ResponseEntity<>(userRepository.findByUsername(principal.getName()), HttpStatus.OK);
	}
	
	/*
	 * Return all users
	 */
	@GetMapping("/users")
	public Collection<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	/*
	 * Return a user given by the id
	 */
	@GetMapping("/users/{id}")
	public User getUser(@PathVariable long id) {
		return userRepository.findById(id);
	}
	
	/*
	 * Edit User information
	 */
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUserRole(
			@PathVariable long id, @RequestBody User user) {

		
		User updatedUser = userRepository.findById(id);
		
		if (updatedUser != null) {
			return new ResponseEntity<User>(userRepository.save(user), HttpStatus.OK);
		}
		
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}
	
	/*
	 * Delete User. 
	 * Return 'No Content' if successfully deleted
	 * Return '404 not found' if user doesn't exist
	 */
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable long id) {
		
		User deletedUser = userRepository.findById(id);
		
		if(deletedUser != null) {
			userRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
	
		return ResponseEntity.notFound().build();
	}

	
	// Utility class for creating a User
	private ResponseEntity<?> createUser(@RequestBody User user) {
		
		// Check if the username is taken before creating a new user
		if (userRepository.findByUsername(user.getUsername()) != null) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		// Encrypt user password
		user.setPassword(passwordEncoderBean().encode(user.getPassword()));
		
		return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED); 
	}
	
	
	
	/*
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
