package com.jacobwebb.restfulwebservices;

import com.jacobwebb.restfulwebservices.model.Contact;
import com.jacobwebb.restfulwebservices.model.Role;
import com.jacobwebb.restfulwebservices.model.User;

/* 
 * Useful in creating encrypted passwords to test out
 * 
 */
public class UserTest {

	public static void main(String[] args) {
		
		Role role = new Role("ROLE_USER");
		
		Contact contact = new Contact("sanctifyd83@yahoo.com", "909-322-5267");

		User user = new User("sanctifyd", "pass", "jacob", "webb", contact);
		
		user.addRole(role);
		
		if (user.hasRole(role.getName())) {
			System.out.println(role.getName());
		}
	}
}
