package com.manalo.prototype.config;

import javax.inject.Inject;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Inject
	private BasicDataSource dataSource;
	
	private static final Logger logger = Logger.getLogger(SecurityConfiguration.class);
	
	private static final String USERS_QUERY = "select username, password, true from users where username=?";
	
	private static final String AUTHORITIES_QUERY = "select username, 'ROLE_USER' from users where username=?";
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		
		try {
			auth.jdbcAuthentication()
					.dataSource(dataSource)
					.usersByUsernameQuery(USERS_QUERY)
					.authoritiesByUsernameQuery(AUTHORITIES_QUERY)
					.passwordEncoder(passwordEncoder());
		} catch (Exception e) {
			logger.warn("JDBC authentication failed", e);
		}
	}
	
	/**
	 * Forward slashes are important
	 */
	@Override
	protected void configure(HttpSecurity http) {
		
		try {
			http.authorizeRequests()
					.antMatchers("/login*", "/static/**").permitAll()
					.antMatchers("/**").authenticated()
					.and()
					.formLogin()
					.loginPage("/login")
					.failureUrl("/login?error")
					.defaultSuccessUrl("/home", false)
					.and()
					.rememberMe()
					.tokenValiditySeconds(86400)
					.key("REMEMBER_ME_KEY")
					.and()
					.logout()
					.logoutUrl("/login?logout")
					.and()
					.sessionManagement()
					.maximumSessions(1)
					.expiredUrl("/login?expired");
		} catch (Exception e) {
			logger.warn("HttpSecurity deny", e);
		}
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
}
