package com.jacobwebb.rest.basic.auth;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtInMemoryUserDetailsService implements UserDetailsService {

  static List<JwtUserDetails> inMemoryUserList = new ArrayList<>();

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	  return null;
  }
  /*
  static {
    inMemoryUserList.add(new JwtUserDetails(1L, "webbj",
        "$2a$10$3zHzb.Npv1hfZbLEU5qsdOju/tk2je6W6PnNnY.c1ujWPcZh4PL6e", "ROLE_USER_2"));
    inMemoryUserList.add(new JwtUserDetails(2L, "jake",
    	"$2a$10$dNl2vukzoHwla8ZweG/XWOhZi4PhGNtlkr6TXNTk3nfIKNB9/SPh.", "ROLE_USER_2"));
  }
*/
  /*
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<JwtUserDetails> findFirst = inMemoryUserList.stream()
        .filter(user -> user.getUsername().equals(username)).findFirst();

    if (!findFirst.isPresent()) {
      throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
    }

    return findFirst.get();
  }
*/
  
}


