package com.jacobwebb.restfulwebservices.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jacobwebb.restfulwebservices.entity.User;

public interface UserJpaRepository extends JpaRepository<User, Long>{

}
