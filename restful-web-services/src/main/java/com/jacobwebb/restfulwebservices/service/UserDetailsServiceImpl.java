package com.jacobwebb.restfulwebservices.service;

import org.springframework.beans.factory.annotation.Autowired;
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
    
    public User signupUser(User user) {
    	
		// Encrypt user password
		user.setPassword(passwordEncoderBean().encode(user.getPassword()));
		userRepository.save(user);
		
		final ConfirmationToken confirmationToken = new ConfirmationToken(user);

		confirmationTokenService.saveConfirmationToken(confirmationToken);
		
		return user;
    }
    
    public void confirmUser(ConfirmationToken confirmationToken) {
    	
    	  final User user = confirmationToken.getUser();

    	  user.setEnabled(true);

    	  userRepository.save(user);

    	  confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());
    }
    
    /*
	public static JwtUserDetails create(User user) {
		List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
		roles.add(new SimpleGrantedAuthority(user.getRoles().toString()));
		return new JwtUserDetails(user.getId(), user.getUsername(), user.getPassword(), roles);
	}
	*/

}
