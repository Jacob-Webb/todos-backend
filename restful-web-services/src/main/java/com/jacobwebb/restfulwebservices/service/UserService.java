package com.jacobwebb.restfulwebservices.service;

import java.util.List;

import com.jacobwebb.restfulwebservices.model.User;

public interface UserService {

	User saveUser(User user);

	User findByUsername(String username);

	List<User> findAllUsers();

}
