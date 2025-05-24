package com.example.App_Gateway.Filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import com.example.App_Gateway.Util.JWTUtil;
import com.netflix.spectator.impl.Config;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter  implements GlobalFilter, Ordered{
	
	@Autowired
	RouteValidator validator;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Autowired
	JWTUtil util;
	
	@Override
    public int getOrder() {
        return -1; // control filter priority
    }
	
	 @Override
	    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		 if (validator.isSecured.test(exchange.getRequest())) {
             //header contains token or not
             if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                 throw new RuntimeException("missing authorization header");
             }

             String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
             
             final List<String> token=new ArrayList<>();
             if (authHeader != null && authHeader.startsWith("Bearer ")) {
                 token.add(authHeader.substring(7));
             }
             else {
            	  exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                  return exchange.getResponse().setComplete();
             }
             return webClientBuilder.build()
                            .post()
                            .uri("http://AUTHSERVICE/auth/validate")
                            .bodyValue(token.get(0))
                            .retrieve()
                            .bodyToMono(Boolean.class)
                            .flatMap(isValid -> {
                                if (isValid) {
                                	String author=util.extractuser(token.get(0));
                                	ServerHttpRequest request = exchange.getRequest().mutate().header("Loggedinuser", author).build();
                                	
                                    return chain.filter(exchange.mutate().request(request).build() );
                                } else {
                                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                    return exchange.getResponse().setComplete();
                                }
                            });
                       //jwtUtil.validateToken(authHeader);
		 }
		 else {
             return chain.filter(exchange);
		 }
           
	}
}
