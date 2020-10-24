package com.jacobwebb.restfulwebservices.security;

public interface ISecurityUserService {
	
	String validatePasswordResetToken(String token);

}
