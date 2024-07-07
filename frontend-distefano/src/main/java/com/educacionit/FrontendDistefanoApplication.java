package com.educacionit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class FrontendDistefanoApplication {

	public static void main(String[] args) {
		final Logger logger = LoggerFactory.getLogger(FrontendDistefanoApplication.class);
		ConfigurableApplicationContext context = SpringApplication.run(FrontendDistefanoApplication.class, args);
		Environment env = context.getEnvironment();
		String port = env.getProperty("server.port");
		logger.info("http://localhost:" + port);

	}
	

	
}
