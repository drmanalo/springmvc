package com.manalo.prototype.config;

import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

/**
 * Hypersonic 2 configuration for development environment
 */
@Configuration
@Profile(value = {"dev", "test"})
public class H2Configuration {
	
	@Value("classpath:sql/schema.sql")
	private Resource dataScript;
	
	private static final Logger logger = Logger.getLogger(H2Configuration.class);
	
	/**
	 * Provides H2 console to look into the db if necessary. When the
	 * application is running use a web browser to access http://localhost:11111
	 * to view a console that allows db queries to be run and viewed. Parameters
	 * url, user name and password should be same as JDBC connection.
	 * 
	 * In an instance,
	 * 
	 * <pre>
	 * jdbc:h2:mem:test;
	 * </pre>
	 * 
	 * @return {@link org.h2.tools.Server}
	 */
	@Bean(destroyMethod = "shutdown")
	public Server createWebServer() {
		
		final String[] args = {"-web", "-webPort", "11111"};
		
		Server server = null;
		
		try {
			server = Server.createWebServer(args).start();
		} catch (SQLException e) {
			logger.warn("Failed to create H2 web server", e);
		}
		
		return server;
	}
	
	@Bean
	public DataSourceInitializer dataSourceInitialiser(BasicDataSource dataSource) {
		
		final DataSourceInitializer initialiser = new DataSourceInitializer();
		
		initialiser.setDataSource(dataSource);
		initialiser.setDatabasePopulator(databasePopulator());
		
		return initialiser;
	}
	
	private DatabasePopulator databasePopulator() {
		
		final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(dataScript);
		
		return populator;
	}
	
}
