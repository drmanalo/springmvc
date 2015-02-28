package com.manalo.prototype.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;

import javax.inject.Inject;

import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.manalo.prototype.config.H2Configuration;
import com.manalo.prototype.config.HibernateConfiguration;
import com.manalo.prototype.config.SecurityConfiguration;
import com.manalo.prototype.config.WebConfiguration;

@ContextConfiguration(classes = {WebConfiguration.class, H2Configuration.class, HibernateConfiguration.class,
		SecurityConfiguration.class})
@ActiveProfiles("dev")
@WebAppConfiguration
public class SecurityConfigurationTest extends AbstractTestNGSpringContextTests {
	
	@Inject
	private FilterChainProxy springSecurityFilterChain;
	
	@Inject
	private WebApplicationContext wac;
	
	private MockMvc mvc;
	
	@BeforeMethod
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(wac).addFilters(springSecurityFilterChain).build();
	}
	
	@Test
	public void requiresAuthentication() throws Exception {
		mvc.perform(get("/"))
				.andExpect(redirectedUrl("http://localhost/login"));
	}
	
	@Test
	public void authenticationFailed() throws Exception {
		mvc.perform(formLogin().user("hoodlum").password("guest"))
				.andExpect(status().isMovedTemporarily())
				.andExpect(redirectedUrl("/login?error"))
				.andExpect(unauthenticated());
	}
	
	@Test
	public void authenticationSuccess_USER() throws Exception {
		mvc.perform(formLogin().user("guest").password("guest"))
				.andExpect(status().isMovedTemporarily())
				.andExpect(authenticated().withUsername("guest").withRoles("USER"))
				.andExpect(redirectedUrl("/home"));
	}
	
	@Test
	public void authenticationSuccess_ADMIN() throws Exception {
		mvc.perform(formLogin().user("admin").password("secret"))
				.andExpect(status().isMovedTemporarily())
				.andExpect(authenticated().withUsername("admin").withRoles("ADMIN"))
				.andExpect(redirectedUrl("/home"));
	}
}
