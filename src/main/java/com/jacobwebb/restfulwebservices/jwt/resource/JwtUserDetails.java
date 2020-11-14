package com.jacobwebb.restfulwebservices.jwt.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jacobwebb.restfulwebservices.model.Role;
import com.jacobwebb.restfulwebservices.model.User;

public class JwtUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 1028261261936413762L;
	private User user;
	
	public JwtUserDetails (User user) {
		this.user = user;
	}
 
    @Override
    public String getPassword() {
        return user.getPassword();
    }
 
    @Override
    public String getUsername() {
        return user.getEmail();
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	
    	List<GrantedAuthority> authorities = new ArrayList<>();
    	
    	for (Role role : this.user.getRoles()) {
    		authorities.add(new SimpleGrantedAuthority(role.getName()));
    	}
    	
        return authorities;
    }
   

}
