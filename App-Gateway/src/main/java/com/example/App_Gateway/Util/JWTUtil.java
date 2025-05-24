package com.example.App_Gateway.Util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {
	
private final String secret="57356949357wyrsdjgyu753449isvd8w7e712e9ivcsjc";
	
	private SecretKey getsecretkey() {
		byte[] keybite=Decoders.BASE64.decode(this.secret);
		return Keys.hmacShaKeyFor(keybite);
	}

	

	public String extractuser(String token) {
		// TODO Auto-generated method stub
		return Jwts.parserBuilder().setSigningKey(getsecretkey()).build().parseClaimsJws(token).getBody().getAudience();
		
	}

}
