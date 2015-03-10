package com.manalo.prototype.factory;

import static org.mockito.Mockito.*;
import static org.fest.assertions.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CurrentDateFactoryTest {
	
	private CurrentDateFactory dateFactory;
	
	private Calendar calendar;
	
	@Test
	public void testMock() {
		
		String expected = "Tue Mar 10 22:56:23 GMT 2015";
		
		dateFactory = mock(CurrentDateFactory.class);
		when(dateFactory.instance()).thenReturn(calendar.getTime());
		
		Date actual = dateFactory.instance();
		assertThat(actual.toString()).isEqualTo(expected);
	}
	
	@Test
	public void testActual() {
		dateFactory = new CurrentDateFactory();
		assertThat(dateFactory.instance()).isAfter(calendar.getTime());
	}
	
	@BeforeMethod
	public void beforeMethod() {
		calendar = Calendar.getInstance();
		calendar.set(2015, 2, 10, 22, 56, 23);
	}
}
