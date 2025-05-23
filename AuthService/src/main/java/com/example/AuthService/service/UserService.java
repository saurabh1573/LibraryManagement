package com.example.AuthService.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.AuthService.DTO.UserCredDTO;
import com.example.AuthService.Dao.UserRepository;
import com.example.AuthService.model.UserCredentials;

@Service
@Primary
public class UserService {
	@Autowired
	UserRepository repository;
	
	@Autowired
	ToeknService tokenservice;
	@Autowired 
	PasswordEncoder encode;
	
	public UserCredDTO register(UserCredDTO credential) {
		UserCredentials cred=dtotomodel(credential);
		cred.setPassword(encode.encode(cred.getPassword()));
		return modeltodto(repository.save(cred));
	}
	
	private UserCredDTO modeltodto(UserCredentials credentials) {
		// TODO Auto-generated method stub
		UserCredDTO cred=new UserCredDTO();
		cred.setId(credentials.getId());
		cred.setName(credentials.getName());
		cred.setEmail(credentials.getEmail());
		cred.setPassword(credentials.getPassword());
		cred.setAuthority(credentials.getAuthority());
		return cred;
	}

	private UserCredentials dtotomodel(UserCredDTO credential) {
		// TODO Auto-generated method stub
		UserCredentials cred=new UserCredentials();
		cred.setName(credential.getName());
		cred.setPassword(credential.getPassword());
		cred.setEmail(credential.getEmail());
		cred.setAuthority(credential.getAuthority());
		return cred;
	}

	public Optional<UserCredentials> findbyname(String username) {
		 return repository.findByName(username);
		
	}
	
	public String generateToken(UserCredDTO cred) {
		return tokenservice.generatetoken(cred.getName());
	}
	public boolean validatetoken(String Token) {
		return tokenservice.validateToken(Token);
	}

	public boolean exists(String username) {
		// TODO Auto-generated method stub
		try {
			repository.findByName(username);
		}
		catch(Exception e){
			return false;
		}
		return true;
	}

	public String extractuser(String token) {
		// TODO Auto-generated method stub
		return tokenservice.extractuser(token);
		
	}
}
