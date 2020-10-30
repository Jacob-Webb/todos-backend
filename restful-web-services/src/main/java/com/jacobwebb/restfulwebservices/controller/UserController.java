 package com.jacobwebb.restfulwebservices.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jacobwebb.restfulwebservices.dao.RoleRepository;
import com.jacobwebb.restfulwebservices.dao.UserJpaRepository;
import com.jacobwebb.restfulwebservices.model.ConfirmationToken;
import com.jacobwebb.restfulwebservices.model.Role;
import com.jacobwebb.restfulwebservices.model.Todo;
import com.jacobwebb.restfulwebservices.model.User;
import com.jacobwebb.restfulwebservices.security.UserSecurityService;
import com.jacobwebb.restfulwebservices.service.ConfirmationTokenService;
import com.jacobwebb.restfulwebservices.service.EmailSenderService;
import com.jacobwebb.restfulwebservices.service.UserDetailsServiceImpl;

@CrossOrigin(origins="${crossOrigin}")
@RestController
public class UserController {

	@Autowired
	private UserJpaRepository userRepository;
	
	@Autowired 
	RoleRepository roleRepository;
	
	@Autowired
	UserDetailsServiceImpl userService;
	
	@Autowired
	EmailSenderService emailSenderService;
	
	@Autowired
	ConfirmationTokenService confirmationTokenService;
	
	@Autowired
	UserSecurityService securityService;
	
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }
    
    /*
     * Register a new user if one with the same email doesn't already exist
     */
  	@PostMapping("/user/register") 
  	public ResponseEntity<?> registerUser(@RequestBody User user) {

		// Check if the username is taken before creating a new user
		if (userRepository.findByEmail(user.getEmail()) != null) {
			
			User checkUser = userRepository.findByEmail(user.getEmail());
			
			
			// If the user exists and has been enabled already, return a conflict error
			if (checkUser.isEnabled()) {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
			
			//Otherwise, find the users confirmation token and resend an email.  
			else {
				ConfirmationToken confirmationToken = confirmationTokenService.findConfirmationTokenByUserId(checkUser.getId());
				
				userService.updateRegistrant(user);
		
				userService.sendConfirmationEmail(user.getEmail(), confirmationToken.getConfirmationToken());
				//
				return new ResponseEntity<>(HttpStatus.ACCEPTED);
			}
		}
		
		// First time this user has been saved
		user.addRole(roleRepository.findByName("ROLE_USER"));
		
		userService.signupUser(user);
		
		return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED); 
  	}
  	
  	/*
  	 * Receives the users confirmation token when registering to verify that this token 
  	 * does indeed belong to the user. 
  	 */
  	@PostMapping("/user/register/confirm")
  	public ResponseEntity<?> confirmConfirmationToken(@RequestBody String token) {
  		
  		
		Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenService.findConfirmationTokenByToken(token);
		
		if (optionalConfirmationToken == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		optionalConfirmationToken.ifPresent(userService::confirmUser);
		
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
		
  	}
  	
  	/*
  	 * Api call to ensure that the email supplied by the front end is unique
  	 * Otherwise, throw an error
  	 */
    @PostMapping("/user/register/verify")
    public ResponseEntity<?> isNewUser(@RequestBody User user) {

		// Check if the username is taken before creating a new user
		if (userRepository.findByEmail(user.getEmail()) != null) {
			
			User checkUser = userRepository.findByEmail(user.getEmail());
			
			/*
			 * If the user exists and has been enabled already
			 * return a conflict error
			 */
			if (checkUser.isEnabled()) {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			} 
		}
		return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }
  	
    /*
     * Creates a token and email for the user to reset their password IF the user exists
     */
	@PostMapping("/user/resetPassword")
	public ResponseEntity<?> resetPasswordRequest(@RequestBody String userEmail) {
		
		System.out.println(userEmail);
		
	    User user = userRepository.findByEmail(userEmail);
	    if (user == null) {
	    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	    String token = UUID.randomUUID().toString();
	    userService.createPasswordResetTokenForUser(user, token);
	    emailSenderService.sendEmail(userService.constructResetTokenEmail(token, user));
	    return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	/*
	 * Confirms that the supplied ResetPasswordToken is a legitimate token
	 */
	@PostMapping("/user/resetPassword/confirm")
	public ResponseEntity<?> confirmPasswordReset(@RequestBody String token) {
		
		String result = securityService.validatePasswordResetToken(token);
		
		if (result != null) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/user/resetPassword/savePassword")
	public ResponseEntity<?> changePassword(@RequestParam String resetToken, @RequestBody String password) {
		
		String result = securityService.validatePasswordResetToken(resetToken);
		
		if (result != null) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		User user = userService.getUserByPasswordResetToken(resetToken);
		user.setPassword(passwordEncoderBean().encode(password));
		userRepository.save(user);
		return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
	}
	
	/*
	 * Create User with generic role
	 */
	@PostMapping("/api/users") 
	public ResponseEntity<?> createWithOutRole(@RequestBody User user) {
		//Role role = new Role("ROLE_USER");
		
		user.addRole(roleRepository.findByName("ROLE_USER"));
		
		return createUser(user);
	}
	
	/*
	 * Create User with a role
	 */
	@PostMapping("/api/users/{role}") 
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
	@GetMapping("/api/users/login")
	public ResponseEntity<?> login(Principal principal) {
		if(principal == null)
			//This should be ok http status because this will be used for input path
			return ResponseEntity.ok(principal);
		
		return new ResponseEntity<>(userRepository.findByEmail(principal.getName()), HttpStatus.OK);
	}
	
	/*
	 * Return all users
	 */
	@GetMapping("/api/users")
	public Collection<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	/*
	 * Return a user given by the id
	 */
	/*
	@GetMapping("/api/users/{id}")
	public User getUserByID(@PathVariable long id) {
		User user = userRepository.findById(id);
		
		// Cleans up return JSON by eliminating recursive objects
		for (Role role: user.getRoles()) {
			role.setUsers(null);
			role.setPrivileges(null);
		}
		for (Todo todo: user.getTodos()) {
			todo.setUser(null);
		}
		return user;
	}
	*/
	
	/*
	 * Return a user given by the id
	 */
	@GetMapping("/api/users/{email}")
	public User getUserByEmail(@PathVariable String email) {
		User user = userRepository.findByEmail(email);
		
		// Cleans up return JSON by eliminating recursive objects
		for (Role role: user.getRoles()) {
			role.setUsers(null);
			role.setPrivileges(null);
		}
		for (Todo todo: user.getTodos()) {
			todo.setUser(null);
		}
		return user;
	}
	
	/*
	 * Return a user given by the id
	 */
	@GetMapping("/api/users/name/{username}")
	public User getUserByUsername(@PathVariable String email) {
		User user = userRepository.findByEmail(email);
		
		// Cleans up return JSON by eliminating recursive objects
		for (Role role: user.getRoles()) {
			role.setUsers(null);
			role.setPrivileges(null);
		}
		for (Todo todo: user.getTodos()) {
			todo.setUser(null);
		}
		return user;
	}
	
	/*
	 * Edit User information
	 */
	@PutMapping("/api/users/{id}")
	public ResponseEntity<User> updateUserRole(
			@PathVariable long id, @RequestBody User user) {

		
		User updatedUser = userRepository.findById(id);
		
		if (updatedUser != null) {
			updatedUser = userRepository.save(user);
			for (Role role: updatedUser.getRoles()) {
				role.setPrivileges(null);
			}
			return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
		}
		
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}
	
	/*
	 * Delete User. 
	 * Return 'No Content' if successfully deleted
	 * Return '404 not found' if user doesn't exist
	 */
	@DeleteMapping("/api/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable long id) {
		
		User deletedUser = userRepository.findById(id);
		
		if(deletedUser != null) {
			userRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
	
		return ResponseEntity.notFound().build();
	}
	
	/****************************************************
	 * Utility Functions
	 ***************************************************/

	// Utility class for creating a User
	private ResponseEntity<?> createUser(@RequestBody User user) {
		
		// Check if the username is taken before creating a new user
		if (userRepository.findByEmail(user.getEmail()) != null) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		// Encrypt user password
		user.setPassword(passwordEncoderBean().encode(user.getPassword()));
		
		return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED); 
	}
	
}
