package com.manalo.prototype.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Registers Spring's {@code DispatcherServlet}
 */
public class ApplicationInitialiser implements WebApplicationInitializer {
	
	private static final String CONFIG_LOCATION = "com.manalo.prototype.config";
	
	private static final String DISPATCHER_SERVLET_NAME = "springmvc";
	
	private static final String MAPPING_URL = "/";
	
	@Override
	public void onStartup(ServletContext servletContext) {
		
		WebApplicationContext context = getContext();
		servletContext.addListener(new ContextLoaderListener(context));
		
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(DISPATCHER_SERVLET_NAME,
				new DispatcherServlet(context));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping(MAPPING_URL);
	}
	
	private AnnotationConfigWebApplicationContext getContext() {
		
		final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation(CONFIG_LOCATION);
		
		return context;
	}
	
}
