package com.manalo.prototype.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * Registers the {@link DelegatingFilterProxy} to use the
 * springSecurityFilterChain before any other registered filters
 *
 */
public class SecurityInitialiser extends AbstractSecurityWebApplicationInitializer {
	
	/**
	 * ensure that expired sessions are cleaned up
	 */
	@Override
	protected boolean enableHttpSessionEventPublisher() {
		return true;
	}
}
