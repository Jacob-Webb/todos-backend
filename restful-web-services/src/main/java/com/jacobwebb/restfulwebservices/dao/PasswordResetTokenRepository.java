package com.jacobwebb.restfulwebservices.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jacobwebb.restfulwebservices.model.ConfirmationToken;
import com.jacobwebb.restfulwebservices.model.PasswordResetToken;

@Repository
public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long> {
	
	PasswordResetToken findByToken(String token);

}
