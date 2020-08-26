package com.jacobwebb.restfulwebservices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jacobwebb.restfulwebservices.dao.UserJpaRepository;
import com.jacobwebb.restfulwebservices.jwt.resource.JwtUserDetails;
import com.jacobwebb.restfulwebservices.model.User;

public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserJpaRepository userRepository;
 
    @Override
    public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {
 
        User user = userRepository.findByUsername(username);
  
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
 
        return new JwtUserDetails(user);
    }

}
