package com.manalo.prototype.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manalo.prototype.entity.User;
import com.manalo.prototype.factory.CurrentDateFactory;
import com.manalo.prototype.form.UserForm;
import com.manalo.prototype.repository.UserDao;

@Service
@Transactional
public class UserService {
	
	@Inject
	private CurrentDateFactory currentDateFactory;
	
	@Inject
	private PasswordEncoder passwordEncoder;
	
	@Inject
	private UserDao userDao;
	
	public Integer save(UserForm userForm) {
		
		User user = new User();
		
		BeanUtils.copyProperties(userForm, user);
		user.setCreated(currentDateFactory.instance());
		
		return userDao.save(user);
	}
	
	public void update(UserForm userForm) {
		
		User user = userDao.findById(userForm.getId(), User.class);
		
		BeanUtils.copyProperties(userForm, user);
		user.setUpdated(currentDateFactory.instance());
		user.setPassword(passwordEncoder.encode(userForm.getPassword()));
		
		userDao.update(user);
	}
	
	public List<User> list() {
		return userDao.getList(User.class);
	}
	
	public UserForm loadUser(Integer id) {
		
		UserForm userForm = new UserForm();
		
		User user = userDao.findById(id, User.class);
		
		BeanUtils.copyProperties(user, userForm);
		
		return userForm;
	}
	
	/**
	 * Returns the username of the deleted user
	 * 
	 * @param id
	 * @return
	 */
	public String delete(Integer id) {
		User user = userDao.findById(id, User.class);
		userDao.delete(user);
		return user.getUsername();
	}
	
	public void setCurrentDateFactory(CurrentDateFactory currentDateFactory) {
		this.currentDateFactory = currentDateFactory;
	}
	
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
}
