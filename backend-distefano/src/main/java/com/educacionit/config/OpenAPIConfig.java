package com.educacionit.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {
	
	//http://localhost:8080/swagger-ui/index.html#/
    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API REST Full | Java | EducacionIT")
                        .version("1.0.0")
                        .description("La API REST proporciona endpoints para "
                        		+ "administrar categorías de productos, productos, ventas y clientes"
                        		+ " en un sistema de comercio electrónico. Este consta de un BackEnd y "
                        		+ "un FrontEnd, ambos desarrollados en SpringBoot con Java y SQL")
                        .contact(new Contact()
                                .name("Alejandro Di Stefano")
                                .email("addistefano@live.com.ar")
                                .url("https://www.educacionit.com/"))
                        .license(new License()
                                .name("Licencia")
                                .url("https://github.com/Drako01/java-trabajo-final/blob/main/LICENSE")
                                )
                )
                .servers(List.of(new Server()
                		.url("http://localhost:8080")
                		.description("Servidor Local")));
    }
}
