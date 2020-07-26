package com.jacobwebb.restfulwebservices.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Contact {
	
	@Column(name="email")
	private String email;
	
	@Column(name="phone")
	private String phone;
	
	public Contact() {
		
	}

	public Contact(String email, String phone) {
		this.email = email;
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
