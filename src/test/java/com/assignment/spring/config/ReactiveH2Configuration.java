package com.assignment.spring.config;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.h2.H2ConnectionOption;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.r2dbc.connectionfactory.TransactionAwareConnectionFactoryProxy;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@Profile("test")
@EnableR2dbcRepositories
public class ReactiveH2Configuration extends AbstractReactiveDataAccessConfiguration {

	private final H2ConnectionFactory h2ConnectionFactory;

	@Autowired
	// @formatter:off
	public ReactiveH2Configuration() {
		h2ConnectionFactory = new H2ConnectionFactory(H2ConnectionConfiguration.builder().inMemory("testDB").property(H2ConnectionOption.DB_CLOSE_DELAY, "-1").build());
	}
	// @formatter:on

	@Bean
	public ConnectionFactory connectionFactory() {
		return new TransactionAwareConnectionFactoryProxy(h2ConnectionFactory);
	}
}
