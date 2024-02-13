package com.mvprojects.bankcore;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Banking REST API Documentation",
				description = "Spring Boot restfull api for Banking/Finance",
				version = "v1.0",
				contact = @Contact(
						name = "Arland Michelena V.",
						email = "arlandmvillegas@gmail.com"
						//url
				),
				license = @License(
						name = "MIT",
						url = "www.arland.net./license"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot User Documentation",
				url = "www.arland.net./userDocs.html"
		)
)
public class FintechCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(FintechCoreApplication.class, args);
	}

}
