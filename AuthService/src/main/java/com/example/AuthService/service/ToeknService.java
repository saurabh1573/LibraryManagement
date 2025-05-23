package com.example.AuthService.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Configuration
public class ToeknService {
	
	@Bean
	@Lazy
	public UserService userservice() {
		return new UserService();
	}
	
	private String secret="57356949357wyrsdjgyu753449isvd8w7e712e9ivcsjc";
	
	private SecretKey getsecretkey() {
		byte[] keybite=Decoders.BASE64.decode(this.secret);
		return Keys.hmacShaKeyFor(keybite);
	}
	
	public String  generatetoken(String username) {
		Map<String,Object> claims=new HashMap<>();
		return createtoken(claims,username);
	}

	private String createtoken(Map<String, Object> claims, String username) {
		// TODO Auto-generated method stub
		return Jwts.builder().setClaims(claims).setAudience(username) 
		.setIssuedAt(new Date(System.currentTimeMillis()))
		.setExpiration(new Date(System.currentTimeMillis()+60*60*1000))
		.signWith(getsecretkey(), SignatureAlgorithm.HS256).compact();	
	}
	public boolean validateToken(final String token) {
		try {
			Claims claims=Jwts.parserBuilder().setSigningKey(getsecretkey()).build().parseClaimsJws(token).getBody();
			if(userservice().exists(claims.getAudience())) {
				return claims.getExpiration().before(new Date());
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
			return false;
		}
		
	}

	public String extractuser(String token) {
		// TODO Auto-generated method stub
		return Jwts.parserBuilder().setSigningKey(getsecretkey()).build().parseClaimsJws(token).getBody().getAudience();
		
	}
	
}
