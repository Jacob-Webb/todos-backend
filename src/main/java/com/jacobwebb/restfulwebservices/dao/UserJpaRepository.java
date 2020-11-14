package com.jacobwebb.restfulwebservices.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jacobwebb.restfulwebservices.model.User;

public interface UserJpaRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);

	User findById(long id);
}
