package com.example.AuthService.Config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.AuthService.model.UserCredentials;

public class CustomUserDetail implements UserDetails {
    
	public String username;
	public String password;
	public Authority auth;
	public  class Authority implements GrantedAuthority{
		
		public String authority;
		
		public Authority(String auth) {
			this.authority=auth;
		}
		@Override
		public String getAuthority() {
			// TODO Auto-generated method stub
			return this.authority;
		}
		
	}
	
	public CustomUserDetail(UserCredentials cred) {
		this.username=cred.getName();
		this.password=cred.getPassword();
		this.auth=new Authority(cred.getAuthority());
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		List<Authority> authorities=List.of(this.auth);
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}
	
	
}
