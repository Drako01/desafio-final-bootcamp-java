package com.educacionit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig { //Front

	
	@Autowired
    private JwtTokenFilter jwtTokenFilter;
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http        		
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authRequest -> authRequest
                        .requestMatchers("/css/**", "/js/**", "/login",
                        		 "/img/**", "/favicon.ico", "/fecha/*", "/signup",
                        		 "/**","/productos/**", "/backend/**"
    	                		, "/categorias/**", "/carritos/**",
    	                		"/backend/productos/json", "/backend/categorias/json")
                        
                        .permitAll()
                        .requestMatchers("http://localhost:8080/productos-listar/**", 
                        				"http://localhost:8080/categorias-listar/**"
       	                     )
       	                .hasAnyRole("USER", "ADMIN")
    	                .anyRequest().authenticated()
    	                
                )  
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                    )
                .addFilterBefore(jwtTokenFilter, 
                		UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    
}
