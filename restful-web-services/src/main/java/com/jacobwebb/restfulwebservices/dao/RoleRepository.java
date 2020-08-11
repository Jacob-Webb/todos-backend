package com.jacobwebb.restfulwebservices.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jacobwebb.restfulwebservices.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
    Role findByName(String name);

    @Override
    void delete(Role role);

}
