package com.jacobwebb.restfulwebservices.task;

import java.time.Instant;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jacobwebb.restfulwebservices.dao.ConfirmationTokenRepository;
import com.jacobwebb.restfulwebservices.dao.PasswordResetTokenRepository;

@Service
@Transactional
public class PurgeTokensTask {
	
	@Autowired
	ConfirmationTokenRepository confirmationTokenRepository;
	
	@Autowired
	PasswordResetTokenRepository passwordTokenRepository;
	
	@Scheduled(cron = "${purge.cron.expression}")
	public void purgeExpired() {
		Date now = Date.from(Instant.now());
		confirmationTokenRepository.deleteAllExpiredSince(now);
		passwordTokenRepository.deleteAllExpiredSince(now);
	}
}
