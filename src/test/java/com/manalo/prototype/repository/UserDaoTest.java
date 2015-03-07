package com.manalo.prototype.repository;

import javax.inject.Inject;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.manalo.prototype.config.H2Configuration;
import com.manalo.prototype.config.HibernateConfiguration;
import com.manalo.prototype.config.WebConfiguration;
import com.manalo.prototype.entity.User;

import static org.fest.assertions.api.Assertions.*;

@ActiveProfiles("test")
@ContextConfiguration(classes = {WebConfiguration.class, HibernateConfiguration.class, H2Configuration.class})
@Transactional
@WebAppConfiguration
public class UserDaoTest extends AbstractTransactionalTestNGSpringContextTests {
	
	@Inject
	private UserDao userDao;
	
	private User user = new User();
	private String username = "user";
	private String password = "hello";
	private String role = "ROLE_TESTER";
	private boolean active = true;
	
	@BeforeMethod
	public void setup() {
		user.setUsername(username);
		user.setPassword(password);
		user.setActive(active);
		user.setRole(role);
	}
	
	@Test
	public void save() {
		
		userDao.save(user);
		User actual = userDao.findByUsername(username);
		
		assertThat(actual).isNotNull();
		assertThat(actual.getId()).isEqualTo(50);
		assertThat(actual.getUsername()).isEqualTo(username);
		assertThat(actual.getPassword()).isEqualTo(password);
		assertThat(actual.isActive()).isEqualTo(active);
		assertThat(actual.getRole()).isEqualTo(role);
	}
	
}
