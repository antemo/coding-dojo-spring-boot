package com.assignment.spring;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;

//@EnableWebFlux not needed since Spring Boot will automatically recognize webflux on classpath
@SpringBootApplication
public class Application {

	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * Added for (de)serialization of {@link Instant}
	 */
	@Bean
	public Module javaTimeModule() {
		return new JavaTimeModule();
	}
}
