package com.jacobwebb.restfulwebservices.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.jacobwebb.restfulwebservices.dao.UserJpaRepository;

@Service("userDetailsService")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserJpaRepository userRepository;
	
	@Autowired
	private IUserService service;
	
	@Autowired
	private MessageSource messages;
	
	@Autowired
	private RoleRepository roleRepository;
}
