package com.educacionit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${api.base-url}")
    private String baseUrl;
    
    @Value("${bienvenida}")
    private String bienvenida;

    @Bean
    String baseUrl() {
        return baseUrl;
    }
    
    @Bean
	public String bienvenida() {
        return bienvenida;
    }
}
