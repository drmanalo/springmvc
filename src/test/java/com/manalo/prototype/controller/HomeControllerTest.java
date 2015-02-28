package com.manalo.prototype.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.inject.Inject;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.manalo.prototype.config.WebConfiguration;

@ContextConfiguration(classes = {WebConfiguration.class})
@WebAppConfiguration
public class HomeControllerTest extends AbstractTestNGSpringContextTests {
	
	@Inject
	private WebApplicationContext wac;
	
	private MockMvc mvc;
	
	@BeforeMethod
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void home() throws Exception {
		this.mvc.perform(get("/home"))
				.andExpect(status().isOk())
				.andExpect(view().name("user/home"));
	}
	
	@Test
	public void login() throws Exception {
		this.mvc.perform(get("/login"))
				.andExpect(status().isOk())
				.andExpect(view().name("user/login"));
	}
	
}
