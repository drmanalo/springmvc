package com.manalo.prototype.service;

import static org.mockito.Mockito.*;
import static org.fest.assertions.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import com.manalo.prototype.entity.User;
import com.manalo.prototype.form.UserForm;
import com.manalo.prototype.repository.UserDao;

public class UserServiceTest {
	
	private UserDao userDao;
	private UserService userService;
	
	@Test
	public void save() {
		userService.save(new UserForm());
		verify(userDao).save(any(User.class));
	}
	
	@Test
	public void update() {
		
		Integer id = 23;
		User user = new User();
		
		when(userDao.findById(id, User.class)).thenReturn(user);
		
		userService.update(new UserForm());
		verify(userDao).update(any(User.class));
	}
	
	@Test
	public void list() {
		
		List<User> expected = new ArrayList<>();
		when(userDao.getList(User.class)).thenReturn(expected);
		
		List<User> actual = userService.list();
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	public void loadUser() {
		
		Integer id = 23;
		String password = "password";
		String role = "role";
		String username = "username";
		
		User user = new User();
		user.setId(id);
		user.setActive(true);
		user.setPassword(password);
		user.setRole(role);
		user.setUsername(username);
		
		when(userDao.findById(id, User.class)).thenReturn(user);
		
		UserForm actual = userService.loadUser(id);
		
		assertThat(actual.getId()).isEqualTo(id);
		assertThat(actual.getPassword()).isEqualTo(password);
		assertThat(actual.getRole()).isEqualTo(role);
		assertThat(actual.getUsername()).isEqualTo(username);
	}
	
	@Test
	public void delete() {
		
		Integer id = 2384;
		String username = "username";
		
		User user = new User();
		user.setUsername(username);
		
		when(userDao.findById(id, User.class)).thenReturn(user);
		
		String actual = userService.delete(id);
		assertThat(actual).isEqualTo(username);
	}
	
	@BeforeMethod
	public void beforeMethod() {
		userDao = mock(UserDao.class);
		userService = new UserService();
		userService.setUserDao(userDao);
	}
	
}
