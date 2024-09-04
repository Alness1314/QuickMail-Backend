package com.alness.quickmail.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
    
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(getInfo());
	}

	private Info getInfo() {
		return new Info()
				.title("Quick Mail")
				.description("Assistant application for sending income emails, using handlebar templates.")
				.version("1.0")
				.license(getLicense());
	}

	private License getLicense() {
		return new License()
				.name("Alness Zadro")
				.url("https://github.com/Alness1314");
	}
    

}
