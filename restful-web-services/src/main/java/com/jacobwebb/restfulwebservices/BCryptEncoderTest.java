package com.jacobwebb.restfulwebservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/* 
 * Useful in creating encrypted passwords to test out
 * 
 */
public class BCryptEncoderTest {
	
    public static PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }
    
	public static String encode(String pass) {
		return passwordEncoderBean().encode(pass);
	}

	public static void main(String[] args) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		for (int i = 1; i <= 10; i++) {
			String encodedString = encoder.encode("dummy");
			System.out.println(encodedString);
		}
		
		System.out.println();
		
		for (int i = 1; i <= 10; ++i) {
			System.out.println(encode("dummy"));
		}
	}
	
}
