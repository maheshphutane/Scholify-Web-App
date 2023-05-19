package com.hello_world_sprinboot.scholify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.hello_world_sprinboot.scholify.repository")
@EntityScan("com.hello_world_sprinboot.scholify.model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class Scholify {

	public static void main(String[] args) {
		SpringApplication.run(Scholify.class, args);
	}

}
