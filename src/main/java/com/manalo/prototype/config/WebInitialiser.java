package com.manalo.prototype.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitialiser extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}
	
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[]{WebConfiguration.class, HibernateConfiguration.class, H2Configuration.class,
				SecurityConfiguration.class};
	}
	
	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}
	
}
