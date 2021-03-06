package com.manalo.prototype.config;

import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import freemarker.template.TemplateException;
import freemarker.template.utility.XmlEscape;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.manalo.prototype"})
public class WebConfiguration extends WebMvcConfigurerAdapter {
	
	private static final Logger logger = Logger.getLogger(WebConfiguration.class);
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		final LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		
		registry.addInterceptor(localeChangeInterceptor);
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		
		final CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		cookieLocaleResolver.setDefaultLocale(StringUtils.parseLocaleString("en_PH"));
		
		return cookieLocaleResolver;
	}
	
	@Bean
	public MessageSource messageSource() {
		
		final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:language/translation");
		/**
		 * If true, the key of the message will be displayed if the key is not
		 * found, instead of throwing a NoSuchMessageException
		 * 
		 */
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(90);
		
		return messageSource;
	}
	
	@Bean
	public ViewResolver viewResolver() {
		
		final FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
		
		freeMarkerViewResolver.setCache(true);
		freeMarkerViewResolver.setSuffix(".ftl");
		
		return freeMarkerViewResolver;
	}
	
	@Bean
	public FreeMarkerConfigurer freemarkerConfig() {
		
		final FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
		freeMarkerConfigurer.setTemplateLoaderPath("/views/");
		
		final HashMap<String, Object> variables = new HashMap<String, Object>();
		variables.put("xml_escape", new XmlEscape());
		variables.put("number_format", "0.######");
		
		freeMarkerConfigurer.setFreemarkerVariables(variables);
		
		freemarker.template.Configuration configuration = null;
		
		try {
			configuration = freeMarkerConfigurer.createConfiguration();
		} catch (IOException | TemplateException e) {
			logger.warn("Failed to created FreeMarker configuration", e);
		}
		configuration.setNumberFormat("0.######");
		
		return freeMarkerConfigurer;
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
}
