package com.manalo.prototype.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.inject.Inject;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.manalo.prototype.config.WebConfiguration;

@ContextConfiguration(classes = WebConfiguration.class)
@WebAppConfiguration
public class HomeControllerTest extends AbstractTestNGSpringContextTests {
	
	@Inject
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@BeforeClass
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void getHome() throws Exception {
		this.mockMvc.perform(get("/home"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("user/home"))
				.andExpect(content().contentType("text/html;charset=ISO-8859-1"));
	}
	
}
