package com.educacionit.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

	@Value("${api.base-url}")
	private String baseUrl;

	@Value("${bienvenida}")
	private String bienvenida;

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.expiration}")
    private Long jwtExpiration;
	
	
	@Bean
	String baseUrl() {
		return baseUrl;
	}

	@Bean
	public String bienvenida() {
		return bienvenida;
	}
	
	@Bean
	public String jwtSecret() {
		return jwtSecret;
	}
	
	@Bean
	public Long jwtExpiration() {
		return jwtExpiration;
	}
	
	
	@Bean
    @Qualifier("restTemplateFront")
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new AuthInterceptor());
        return restTemplate;
    }
}
