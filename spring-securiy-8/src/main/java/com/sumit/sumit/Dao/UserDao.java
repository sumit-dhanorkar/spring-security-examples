package com.sumit.sumit.Dao;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
	private final static List<UserDetails> APPLICATION_USER	= Arrays.asList(
			new User(
						"sumit@gmail.com", 
						"password", 
						Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))
					),
			new User(
					 "amit@gmail.com",
					 "password",
					  Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))
					)
		);
	
	

	public UserDetails findUserByEmail(String email) {
		return APPLICATION_USER
				.stream()
				.filter(u-> u.getUsername().equals(email))
				.findFirst()
				.orElseThrow(()-> new UsernameNotFoundException(email));
	}
}
