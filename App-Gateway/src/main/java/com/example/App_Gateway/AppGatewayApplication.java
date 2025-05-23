package com.example.App_Gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.web.client.RestTemplate;

import com.example.App_Gateway.Filter.AuthenticationFilter;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class AppGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppGatewayApplication.class, args);
	}

	@Bean
	public RestTemplate gettemplate() {
		return new RestTemplate();
	}

}
