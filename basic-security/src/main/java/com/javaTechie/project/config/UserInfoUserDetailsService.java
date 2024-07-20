package com.javaTechie.project.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.javaTechie.project.entity.UserInfo;
import com.javaTechie.project.repository.UserInfoRepository;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserInfoRepository repository;

	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<UserInfo> userInfo= repository.findByName(username);
		//returns userDetail object
		return userInfo.map(UserInfoUserDetails::new) 
		        .orElseThrow(()-> new UsernameNotFoundException("user not found"+username));
		
	}


}
