package com.jacobwebb.restfulwebservices;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/* 
 * Useful in creating encrypted passwords to test out
 * 
 */
public class BCryptEncoderTest {

	public static void main(String[] args) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		for (int i = 1; i <= 10; i++) {
			String encodedString = encoder.encode("dummy");
			System.out.println(encodedString);
		}
	}
}
