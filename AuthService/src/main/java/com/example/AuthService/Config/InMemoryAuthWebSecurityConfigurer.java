package com.example.AuthService.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class InMemoryAuthWebSecurityConfigurer {
	
	
	@Bean 
	public UserDetailsService userdetailservice() {
		return new CustomUserDetailsService();
	}
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@SuppressWarnings("removal")
	@Bean 
	public SecurityFilterChain securityfilterchain(HttpSecurity http) throws Exception{
		return http.csrf((csrf)->csrf.disable())
                .authorizeHttpRequests((auth)->auth.requestMatchers("/auth/**").permitAll().anyRequest().authenticated())
                .build();
	}
	
	@Bean
	public AuthenticationProvider authenticationprovider() {
		DaoAuthenticationProvider daoap= new DaoAuthenticationProvider();
		daoap.setUserDetailsService(userdetailservice());
		daoap.setPasswordEncoder(encoder());
		return daoap;
	}
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}