package com.jacobwebb.restfulwebservices.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jacobwebb.restfulwebservices.model.User;

public interface UserJpaRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);
}
