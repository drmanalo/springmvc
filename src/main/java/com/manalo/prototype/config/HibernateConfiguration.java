package com.manalo.prototype.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Persistence configuration
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class HibernateConfiguration {
	
	@Value("${db.driver}")
	private String driverClassName;
	
	@Value("${db.url}")
	private String dbUrl;
	
	@Value("${db.user}")
	private String username;
	
	@Value("${db.pass}")
	private String password;
	
	@Value("${hibernate.dialect}")
	private String dialect;
	
	@Value("${hibernate.show.sql}")
	private String showSql;
	
	@Value("${hibernate.hbm2ddl.auto}")
	private String hbm2ddl;
	
	@Value("${db.pool.max.total}")
	private Integer maxTotal;
	
	@Value("${db.pool.min.evictable.millis}")
	private Long minEvictableMillis;
	
	private static final String PACKAGES_TO_SCAN = "com.manalo.prototype.entity";
	private static final String FLYWAY_TABLE_NAME = "FLYWAY";
	
	@Bean
	public BasicDataSource dataSource() {
		
		final BasicDataSource dataSource = new BasicDataSource();
		
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(dbUrl);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setMinEvictableIdleTimeMillis(minEvictableMillis);
		dataSource.setMaxTotal(maxTotal);
		
		return dataSource;
	}
	
	@Bean
	@DependsOn("flyway")
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
		
		final LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		
		sessionFactoryBean.setDataSource(dataSource);
		sessionFactoryBean.setPackagesToScan(PACKAGES_TO_SCAN);
		sessionFactoryBean.setHibernateProperties(setupProperties());
		
		return sessionFactoryBean;
	}
	
	@Bean
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		
		final HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory);
		
		return transactionManager;
	}
	
	@Bean
	public Flyway flyway() {
		
		final Flyway flyway = new Flyway();
		
		flyway.setDataSource(dataSource());
		flyway.setTable(FLYWAY_TABLE_NAME);
		flyway.migrate();
		
		return flyway;
	}
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	private Properties setupProperties() {
		
		final Properties properties = new Properties();
		
		properties.setProperty("hibernate.dialect", dialect);
		properties.setProperty("hibernate.show_sql", showSql);
		properties.setProperty("hibernate.hbm2ddl.auto", hbm2ddl);
		properties.setProperty("hibernate.cache.use_second_level_cache", "false");
		properties.setProperty("hibernate.cache.use_query_cache", "false");
		properties.setProperty("hibernate.generate_statistics", "false");
		properties.setProperty("hibernate.cache.use_structured_entries", "false");
		
		return properties;
	}
	
}