package com.educacionit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.educacionit.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
    private AuthenticationProvider authProvider;
    
    
	public SecurityConfig() {
		super();
	}


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http
	            .csrf(csrf -> 
	                csrf
	                .disable())
	            .authorizeHttpRequests(authRequest ->
	              authRequest
	              	.requestMatchers("/*/**").permitAll()
	                .requestMatchers("/auth/**").permitAll()
	                .requestMatchers("/swagger-ui/**").permitAll()
	                //.anyRequest().authenticated()
	                )
	            .sessionManagement(sessionManager->
	            		sessionManager 
	            		.sessionCreationPolicy(SessionCreationPolicy.STATELESS))	//politica de creacion de sesiones, que no la utilice
	            .authenticationProvider(authProvider)	//proveedor de autenticciaon
	            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
	            .build();
		
		
	}
}
