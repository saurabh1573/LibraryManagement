package com.example.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AuthService.DTO.AuthRequestDTO;
import com.example.AuthService.DTO.UserCredDTO;
import com.example.AuthService.model.UserCredentials;
import com.example.AuthService.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UserService service;
	
	@Autowired
	AuthenticationManager authenticationmanager;
	
	
	@PostMapping("/register")
	public UserCredDTO register(@RequestBody AuthRequestDTO cred) {
		Authentication auth=authenticationmanager.authenticate(new UsernamePasswordAuthenticationToken(cred.getUsername(),cred.getPassword()));
		if(auth.isAuthenticated()) {
			return service.register(cred.getUser());
		}
		return cred.getUser();
	}
	
	@PostMapping("/token")
	public String generateTokne(@RequestBody UserCredDTO cred) {
		Authentication auth=authenticationmanager.authenticate(new UsernamePasswordAuthenticationToken(cred.getName(),cred.getPassword()));
		if(auth.isAuthenticated()) {
			return service.generateToken(cred);
		}
		else {
			return "You Don't have permission to generate Token";
		}
		
	}
	@GetMapping("/exists/{username}")
	public boolean exists(@PathVariable String username) {
		return service.exists(username);
	}
	
	@PostMapping("/validate")
	public boolean validatetoken(@RequestBody String token) {
		
			return service.validatetoken(token);
	
	}
	
	@PostMapping("/extractuser")
	public String extractuser(@RequestBody String token) {
			return service.extractuser(token);
	}
}
