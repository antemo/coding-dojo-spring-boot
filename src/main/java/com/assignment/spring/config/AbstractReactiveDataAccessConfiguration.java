package com.assignment.spring.config;

import com.assignment.spring.entity.Permission;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.connectionfactory.init.CompositeDatabasePopulator;
import org.springframework.data.r2dbc.connectionfactory.init.ConnectionFactoryInitializer;
import org.springframework.data.r2dbc.connectionfactory.init.ResourceDatabasePopulator;

import java.util.List;

public abstract class AbstractReactiveDataAccessConfiguration extends AbstractR2dbcConfiguration {

	@Override
	protected List<Object> getCustomConverters() {
		return List.of(new Permission.PermissionsReadConverter(), new Permission.PermissionsWriteConverter());
	}

	@Bean
	public ConnectionFactoryInitializer initializer(final ConnectionFactory connectionFactory) {
		final ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
		initializer.setConnectionFactory(connectionFactory);

		final CompositeDatabasePopulator databasePopulator = new CompositeDatabasePopulator();

		final ResourceDatabasePopulator weatherSchemaPopulator = new ResourceDatabasePopulator(new ClassPathResource("db/weather_schema.sql"));
		// this is needed for all startups after table was created
		// without this we would need to have some setup to store information about which scripts were executed
		weatherSchemaPopulator.setContinueOnError(true);
		databasePopulator.addPopulators(weatherSchemaPopulator);

		final ResourceDatabasePopulator userSchemaPopulator = new ResourceDatabasePopulator(new ClassPathResource("db/user_schema.sql"));
		// this is needed for all startups after table was created
		// without this we would need to have some setup to store information about which scripts were executed
		userSchemaPopulator.setContinueOnError(true);
		databasePopulator.addPopulators(userSchemaPopulator);

		final ResourceDatabasePopulator userDataPopulator = new ResourceDatabasePopulator(new ClassPathResource("db/user_data.sql"));
		// this is needed for all startups after table was created
		// without this we would need to have some setup to store information about which scripts were executed
		// R2DBC had problems with using 'ON CONFLICT DO NOTHING' option that PostgreSQL has, so we can't use that
		userDataPopulator.setContinueOnError(true);
		databasePopulator.addPopulators(userDataPopulator);

		initializer.setDatabasePopulator(databasePopulator);

		return initializer;
	}
}
