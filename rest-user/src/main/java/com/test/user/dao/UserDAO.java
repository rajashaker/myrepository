package com.test.user.dao;

import java.util.List;

import com.test.user.model.User;

public interface UserDAO {

	User findByName(String name);	
	List<User> findAll();
	
	boolean createUser(User user);
	boolean updateUser(User user);
	boolean deleteUser(String username);
}
