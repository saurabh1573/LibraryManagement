package com.example.AuthService.Config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.AuthService.model.UserCredentials;
import com.example.AuthService.service.UserService;

public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserService service;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<UserCredentials> cred=service.findbyname(username);
		return cred.map(CustomUserDetail::new).orElseThrow(()->new UsernameNotFoundException("User does not exist in permitted list of users, Please register"));
	}

}
