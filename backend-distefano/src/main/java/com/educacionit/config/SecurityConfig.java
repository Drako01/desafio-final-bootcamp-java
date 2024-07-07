package com.educacionit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.educacionit.jwt.JwtAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig { // Back

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private AuthenticationProvider authProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(authRequest ->
	            authRequest
	                .requestMatchers("/auth/**", "/swagger-ui/**")
	                .permitAll()
	                .requestMatchers("/css/**", "/js/**", "/login",
                   		 "/img/**", "/favicon.ico", "/fecha/*", "/signup",
                   		 "/**","/productos/**", "/backend/**"
	                		, "/categorias/**", "/carritos/**",
	                		"/backend/productos/json", "/backend/categorias/json")
                   
                   .permitAll()
	                .requestMatchers("/productos-listar/**", "/categorias-listar/**"
	                     )
	                .hasAnyRole("USER", "ADMIN")
	                
	                .anyRequest().authenticated()
	        )
	        .sessionManagement(sessionManager ->
	            sessionManager
	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        )
	        .authenticationProvider(authProvider)
	        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
	        .build();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
	    return new WebMvcConfigurer() {
	        @Override
	        public void addCorsMappings(CorsRegistry registry) {
	            registry.addMapping("/**")
	                .allowedOrigins("http://localhost:3000")
	                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	                .allowedHeaders("*")
	                .allowCredentials(true);
	        }
	    };
	}

}
