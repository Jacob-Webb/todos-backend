package com.jacobwebb.restfulwebservices.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jacobwebb.restfulwebservices.entity.Contact;

public interface UserContactJpaRepository extends JpaRepository<Contact, Long> {

}
