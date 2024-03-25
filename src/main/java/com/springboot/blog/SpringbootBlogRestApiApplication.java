package com.springboot.blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring boot blog app REST APIs ",
				description = "Spring boot Blog App REST APIs Documentation ",
				version = "v1.0",
				contact = @Contact(
						name = "praful shahane",
						email = "praful@gmail.com",
						url = "http://localhost:8080"
						),
				license = @License(
						name = "Apche 2.0",
						url = "http://localhost:8080/license"
						)
				),
		externalDocs = @ExternalDocumentation(
				description = "Spring boot Blog App REST APIs Documentation  for external doc info",
				url = "http://localhost:8080/externalDocs"
				)
		)
public class SpringbootBlogRestApiApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}

}
