package com.etip.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableReactiveMongoRepositories(basePackages = "com.etip.app.repository")
@EnableWebSecurity
public class EtipProStructureApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtipProStructureApplication.class, args);
	}

}
