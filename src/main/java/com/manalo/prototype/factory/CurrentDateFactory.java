package com.manalo.prototype.factory;

import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * Factory class to make dates testable
 *
 */
@Component
public class CurrentDateFactory {
	
	public Date instance() {
		return new Date();
	}
}
