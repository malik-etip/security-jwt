package com.etip.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories(basePackages = "com.etip.app.repository")
@EnableMongoRepositories(basePackages = "com.etip.app.repository")
public class EtipProStructureApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtipProStructureApplication.class, args);
	}

}
