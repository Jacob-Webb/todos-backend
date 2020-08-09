package com.jacobwebb.restfulwebservices.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jacobwebb.restfulwebservices.model.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long>{

}
