package com.example.App_Gateway.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.spectator.impl.Config;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>{
	
	@Autowired
	RouteValidator validator;
	
	@Autowired 
	RestTemplate template;
	
	public AuthenticationFilter() {
		super(Config.class);
	}
	public static class Config{
		
	}
	
	@Override
	public GatewayFilter apply(Config config) {
		
		return((exchange, chain) -> {
			ServerHttpRequest request = null;
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
//                    //REST call to AUTH service
                    boolean validate=template.postForObject("http://AUTHSERVICE/auth/validate" , authHeader, Boolean.class);
                    
                    if(!validate){
                    	throw new RuntimeException("unauthorized access to application");
                    }
                    String username=template.postForObject("http://AUTHSERVICE/auth/extractuser" , authHeader, String.class);
                    request=exchange.getRequest().mutate().header("Loggedinuser", username).build()  ;                //jwtUtil.validateToken(authHeader);

                } catch (Exception e) {
                    System.out.println("invalid access...!");
                    throw new RuntimeException("unauthorized access to application");
                }
            }
            if(request!=null) {
            return chain.filter(exchange.mutate().request(request).build());
            }
            return chain.filter(exchange);
        });
	}
}
