 package com.jacobwebb.restfulwebservices.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jacobwebb.restfulwebservices.dao.RoleRepository;
import com.jacobwebb.restfulwebservices.dao.UserJpaRepository;
import com.jacobwebb.restfulwebservices.model.ConfirmationToken;
import com.jacobwebb.restfulwebservices.model.User;
import com.jacobwebb.restfulwebservices.security.UserSecurityService;
import com.jacobwebb.restfulwebservices.service.ConfirmationTokenService;
import com.jacobwebb.restfulwebservices.service.EmailSenderService;
import com.jacobwebb.restfulwebservices.service.UserDetailsServiceImpl;

@CrossOrigin(origins="${crossOrigin}")
@RestController
public class UserUtilityController {

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
  		System.out.println("We made it to the register");

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
}
