package com.jacobwebb.restfulwebservices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jacobwebb.restfulwebservices.dao.UserJpaRepository;
import com.jacobwebb.restfulwebservices.jwt.resource.JwtUserDetails;
import com.jacobwebb.restfulwebservices.model.ConfirmationToken;
import com.jacobwebb.restfulwebservices.model.User;

@Component
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserJpaRepository userRepository;
	
	@Autowired
	ConfirmationTokenService confirmationTokenService;
	
	@Autowired
	EmailSenderService emailSenderService;
	
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
    
    private void sendConfirmationEmail(String userEmail, String token) {
    	
    	final SimpleMailMessage mailMessage = new SimpleMailMessage(); 
    	
    	mailMessage.setTo(userEmail);
    	mailMessage.setSubject("Todo Confirmation Link!");
    	mailMessage.setFrom("${spring.mail.username}");
    	mailMessage.setText(
    			"Thank you for registering. Please click on the below link to activate your account.\n\n" + 
    					emailSenderService.getFrontendUrl() + "/login/" + token);

    	emailSenderService.sendEmail(mailMessage);
    }
    
    /*
	public static JwtUserDetails create(User user) {
		List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
		roles.add(new SimpleGrantedAuthority(user.getRoles().toString()));
		return new JwtUserDetails(user.getId(), user.getUsername(), user.getPassword(), roles);
	}
	*/

}
