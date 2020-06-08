package com.assignment.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

import static com.assignment.spring.web.RegistrationController.REGISTRATION_URL;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfiguration {

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(final ServerHttpSecurity http) {
		// @formatter:off
		return http.csrf().disable()
				   .authorizeExchange()
				   .pathMatchers(HttpMethod.POST, REGISTRATION_URL).permitAll()
				   .pathMatchers("/**").authenticated()
				   .and()
				   .httpBasic()
				   .and()
				   .formLogin().disable()
				   .logout().disable()
				   .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
				   .build();
		// @formatter:on
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
