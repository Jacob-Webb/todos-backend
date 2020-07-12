package com.jacobwebb.restfulwebservices.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jacobwebb.restfulwebservices.entity.UserContact;

public interface UserContactJpaRepository extends JpaRepository<UserContact, Long> {

}
