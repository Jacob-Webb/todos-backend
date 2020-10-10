package com.jacobwebb.restfulwebservices.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jacobwebb.restfulwebservices.dao.ConfirmationTokenRepository;
import com.jacobwebb.restfulwebservices.model.ConfirmationToken;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
	
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	
	public void saveConfirmationToken(ConfirmationToken confirmationToken) {

		confirmationTokenRepository.save(confirmationToken);
	}
	
	public void deleteConfirmationToken(Long id) {
		
		confirmationTokenRepository.deleteById(id);
		
	}
	
	public Optional<ConfirmationToken> findConfirmationTokenByToken(String token) {
		
		return confirmationTokenRepository.findConfirmationTokenByToken(token);
	}

}
