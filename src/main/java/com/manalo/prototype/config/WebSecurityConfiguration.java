package com.manalo.prototype.config;

import javax.inject.Inject;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebMvcSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Inject
	private BasicDataSource dataSource;
	
	private static final Logger logger = Logger.getLogger(WebSecurityConfiguration.class);
	
	private static final String USERS_QUERY = "select username, password, (case active when 1 then 'true' else 'false' end) as enabled from users where username=?";
	
	private static final String AUTHORITIES_QUERY = "select username, role from users where username=?";
	
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
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() {
		
		AuthenticationManager authenticationManager = null;
		
		try {
			authenticationManager = super.authenticationManagerBean();
		} catch (Exception e) {
			logger.warn("Failed calling super.authenticationManagerBean()", e);
		}
		
		return authenticationManager;
	}
	
	/**
	 * Forward slashes are important
	 */
	@Override
	protected void configure(HttpSecurity http) {
		
		try {
			http.authorizeRequests()
					.antMatchers("/login*", "/static/**").permitAll()
					.antMatchers("/**").authenticated();
			http.formLogin()
					.loginPage("/login")
					.failureUrl("/login?error")
					.defaultSuccessUrl("/home", false);
			http.rememberMe()
					.tokenValiditySeconds(86400)
					.key("REMEMBER_ME_KEY");
			http.logout()
					.logoutUrl("/logout")
					.logoutSuccessUrl("/login?logout");
			http.sessionManagement()
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
