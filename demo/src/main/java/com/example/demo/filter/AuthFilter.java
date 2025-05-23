package com.example.demo.filter;

import java.io.IOException;

import org.apache.hc.core5.http.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.ClientHttpResponseStatusCodeException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

@Component
public class AuthFilter implements Filter {
	
	@Autowired
	RestTemplate template;
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httprequest=(HttpServletRequest) request;
		if(httprequest.getHeader("Loggedinuser")!=null ||httprequest.getHeader("Loggedinuser")=="") {
			String username=httprequest.getHeader("Loggedinuser");
			boolean validuser=template.getForObject("http://AUTHSERVICE/auth/exists/", Boolean.class);
			if(!validuser) {
				HttpServletResponse httpresponse= (HttpServletResponse) response;
				httpresponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
				httpresponse.getWriter().write("You are not authorized to use this service.Please send reuest to App gateway with poper auth token.");
				httpresponse.getWriter().flush();
			}
			chain.doFilter(httprequest, response);
		}
		else {
			HttpServletResponse httpresponse= (HttpServletResponse) response;
			httpresponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
			httpresponse.getWriter().write("You are not authorized to use this service.Please send reuest to App gateway with poper auth token.");
			httpresponse.getWriter().flush();
		}
		
		

	}

}
