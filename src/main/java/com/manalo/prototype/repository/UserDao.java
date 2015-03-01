package com.manalo.prototype.repository;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.manalo.prototype.entity.User;

@Repository
public class UserDao extends Dao<User> {
	
	public User findByUsername(String username) {
		
		Query query = getSession().createQuery("from User where username = :username");
		query.setString("username", username);
		
		return (User) query.uniqueResult();
	}
	
}
