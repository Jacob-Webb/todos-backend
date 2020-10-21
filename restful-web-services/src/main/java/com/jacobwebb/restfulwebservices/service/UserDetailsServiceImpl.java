package com.jacobwebb.restfulwebservices.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jacobwebb.restfulwebservices.dao.PasswordResetTokenRepository;
import com.jacobwebb.restfulwebservices.dao.UserJpaRepository;
import com.jacobwebb.restfulwebservices.jwt.resource.JwtUserDetails;
import com.jacobwebb.restfulwebservices.model.ConfirmationToken;
import com.jacobwebb.restfulwebservices.model.PasswordResetToken;
import com.jacobwebb.restfulwebservices.model.User;

@Component
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserJpaRepository userRepository;
	
	@Autowired
	ConfirmationTokenService confirmationTokenService;
	
	@Autowired
	EmailSenderService emailSenderService;
	
	@Autowired
    private MessageSource messages;
	
	@Autowired
	PasswordResetTokenRepository passwordTokenRepository;
	
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }
 
    @Override
    public UserDetails loadUserByUsername(String email)
      throws UsernameNotFoundException {
 
        User user = userRepository.findByEmail(email);
  
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
 
        return new JwtUserDetails(user);
    }
    
    public void signupUser(User user) {
    	
		// Encrypt user password
		user.setPassword(passwordEncoderBean().encode(user.getPassword()));
		
		final ConfirmationToken confirmationToken = new ConfirmationToken(user);
		
		sendConfirmationEmail(user.getEmail(), confirmationToken.getConfirmationToken());
		
		userRepository.save(user);

		confirmationTokenService.saveConfirmationToken(confirmationToken);
		
    }
    
    public void confirmUser(ConfirmationToken confirmationToken) {
    	
    	  final User user = confirmationToken.getUser();

    	  user.setEnabled(true);

    	  userRepository.save(user);

    	  confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());
    }
    
    public void updateRegistrant(User user) {
    	User checkUser = userRepository.findByEmail(user.getEmail());
    	
		// set updated values to saved user
		checkUser.setEmail(user.getEmail());
		checkUser.setFirstName(user.getFirstName());
		checkUser.setLastName(user.getLastName());
		checkUser.setPhone(user.getPhone());
		checkUser.setPassword(passwordEncoderBean().encode(user.getPassword()));
		
		userRepository.save(checkUser);
    }
    
    public void sendConfirmationEmail(String userEmail, String token) {
    	
    	final SimpleMailMessage mailMessage = new SimpleMailMessage(); 
    	
    	mailMessage.setTo(userEmail);
    	mailMessage.setSubject("Todo Confirmation Link!");
    	mailMessage.setFrom("${spring.mail.username}");
    	mailMessage.setText(
    			"Thank you for registering. Please click on the below link to activate your account.\n\n" + 
    					emailSenderService.getFrontendUrl() + "/login/" + token);

    	emailSenderService.sendEmail(mailMessage);
    }
    
    public SimpleMailMessage constructResetTokenEmail(String token, User user) {
    	String url = emailSenderService.getFrontendUrl() + "/changePassword?token=" + token;
        String message = "Reset your password";
        
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }
    
	public void createPasswordResetTokenForUser(User user, String token) {
	    PasswordResetToken myToken = new PasswordResetToken(token, user);
	    passwordTokenRepository.save(myToken);
	}
	
	private SimpleMailMessage constructEmail(String subject, String body, User user) {
			    SimpleMailMessage email = new SimpleMailMessage();
			    email.setSubject(subject);
			    email.setText(body);
			    email.setTo(user.getEmail());
			    email.setFrom("${spring.mail.username}");
			    return email;
	}

}
