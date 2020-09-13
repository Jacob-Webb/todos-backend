package com.jacobwebb.restfulwebservices.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jacobwebb.restfulwebservices.dao.RoleRepository;
import com.jacobwebb.restfulwebservices.dao.UserJpaRepository;
import com.jacobwebb.restfulwebservices.model.Role;
import com.jacobwebb.restfulwebservices.model.User;

@CrossOrigin(origins="${crossOrigin}")
@RestController
public class UserController {

	@Autowired
	private UserJpaRepository userRepository;
	
	@Autowired RoleRepository roleRepository;
	
	/*
	 * Create User with a role
	 */
	@PostMapping("/users/create/{role}") 
	public ResponseEntity<?> create(@RequestBody User user, @PathVariable String role) {
		
		// Check if the username is taken before creating a new user
		if (userRepository.findByUsername(user.getUsername()) != null) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		// Encrypt user password and set it
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		
		Collection<Role> roles = new ArrayList<>();
		
		// Check if role exists and add it
		String upperCaseRole = role.toUpperCase();
		String userRole = "ROLE_" + upperCaseRole;
		if (roleRepository.findByName(userRole) != null) {
		  roles.add(roleRepository.findByName(userRole));
		  user.setRoles(roles);
		} else {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED); 
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
	
	@GetMapping("/users")
	public Collection<User> getAllUsers() {
		return userRepository.findAll();
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
