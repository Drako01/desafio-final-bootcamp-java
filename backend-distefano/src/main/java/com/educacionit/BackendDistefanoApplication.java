package com.educacionit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class BackendDistefanoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = 
				SpringApplication.run(BackendDistefanoApplication.class, args);
		Environment env = context.getEnvironment();
		String port = env.getProperty("server.port");
		log.info("http://localhost:" + port);
	}

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
