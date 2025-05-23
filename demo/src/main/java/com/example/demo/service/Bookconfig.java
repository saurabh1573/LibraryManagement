package com.example.demo.service;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Bookconfig {
	 @Bean
	    @LoadBalanced
	    public RestTemplate template(){
	        return new RestTemplate();
	    }
	}

