package com.assignment.spring.config;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.r2dbc.connectionfactory.TransactionAwareConnectionFactoryProxy;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * Class for customized R2DBC configuration.<br>
 * This was added to introduce custom converters, otherwise Spring Boot has automatic configuration that uses {@code spring.r2dbc} configuration properties to setup R2DBC connection factory.
 */
@Configuration
@Profile("!test")
@EnableR2dbcRepositories
public class ReactiveDataAccessConfiguration extends AbstractReactiveDataAccessConfiguration {

	private final PostgresqlConnectionFactory postgresqlConnectionFactory;

	@Autowired
	// @formatter:off
	public ReactiveDataAccessConfiguration(@Value("${datasource.host}") final String host, @Value("${datasource.database}") final String database,
										   @Value("${datasource.username}") final String username, @Value("${datasource.password}") final String password) {
		postgresqlConnectionFactory = new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
																							 .host(host)
																							 .database(database)
																							 .username(username)
																							 .password(password)
																							 .build());
	}
	// @formatter:on

	@Bean
	public ConnectionFactory connectionFactory() {
		return new TransactionAwareConnectionFactoryProxy(postgresqlConnectionFactory);
	}
}
