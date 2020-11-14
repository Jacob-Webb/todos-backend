package com.jacobwebb.restfulwebservices.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jacobwebb.restfulwebservices.model.Todo;

@Repository
public interface TodoJpaRepository extends JpaRepository<Todo, Long>{

	//List<Todo> findByUsername(String username);
}
