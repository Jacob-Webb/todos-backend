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
		
		Iterable<ConfirmationToken> checkTokens = confirmationTokenRepository.findAll();
		
		for (ConfirmationToken checkToken: checkTokens) {
			if (token.equals(checkToken.getConfirmationToken())) {
				System.out.println(checkToken.getConfirmationToken());
				Optional<ConfirmationToken> foundToken = Optional.of(checkToken);
				return foundToken;
			}
		}
		
		return null;
	}
	
	public ConfirmationToken findConfirmationTokenByUserId(Long user_id) {
		
		Iterable<ConfirmationToken> checkTokens = confirmationTokenRepository.findAll();
		
		for (ConfirmationToken checkToken: checkTokens) {
			if (user_id == checkToken.getUser().getId()) {
				return checkToken;
			}
		}
		
		return null;
	}

}
