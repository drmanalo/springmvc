package com.manalo.prototype.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manalo.prototype.entity.User;
import com.manalo.prototype.form.UserForm;
import com.manalo.prototype.repository.UserDao;

@Service
@Transactional
public class UserService {
	
	@Inject
	private UserDao userDao;
	
	private static final Logger logger = Logger.getLogger(UserService.class);
	
	public Integer save(UserForm userForm) {
		
		User user = new User();
		
		copyToEntity(user, userForm);
		user.setCreated(new Date());
		
		return userDao.save(user);
	}
	
	public void update(UserForm userForm) {
		
		User user = userDao.findById(userForm.getId(), User.class);
		
		if (user != null) {
			copyToEntity(user, userForm);
			user.setUpdated(new Date());
		}
		
		userDao.update(user);
	}
	
	private void copyToEntity(User user, UserForm userForm) {
		
		try {
			BeanUtils.copyProperties(user, userForm);
		} catch (IllegalAccessException | InvocationTargetException e) {
			logger.warn("Unable to copy userForm to user entity", e);
		}
	}
	
	public List<User> list() {
		return userDao.getList(User.class);
	}
	
	public UserForm loadUser(Integer id) {
		
		UserForm userForm = new UserForm();
		User user = userDao.findById(id, User.class);
		
		try {
			BeanUtils.copyProperties(userForm, user);
		} catch (IllegalAccessException | InvocationTargetException e) {
			logger.warn("Unable to copy user entity to userForm", e);
		}
		
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
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
}
