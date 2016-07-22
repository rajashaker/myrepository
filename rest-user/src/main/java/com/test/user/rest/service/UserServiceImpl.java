package com.test.user.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.inject.Inject;

import com.test.user.dao.UserDAO;
import com.test.user.model.User;
import com.test.user.rest.request.UserRequest;
import com.test.user.rest.response.UserResponse;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	UserDAO userDAO;

	public UserResponse createUser(UserRequest userRequest) {
		UserResponse userResponse = new UserResponse();
		// write logic for creating user in database
		User user = new User();
		user.setName(userRequest.getName());
		user.setEmail(userRequest.getEmail());
		userDAO.createUser(user);
		userResponse.setEmail(user.getEmail());
		userResponse.setName(user.getName());
		return userResponse;
	}

	public UserResponse[] viewAllUsers() {
		List<User> userList = userDAO.findAll();
		UserResponse[] userResponse = null;
		// write logic for getting list of users from database
		if (userList != null) {
			userResponse = new UserResponse[userList.size()];
			int count = 0;
			for (User user : userList) {
				userResponse[count]= new UserResponse();
				userResponse[count].setName(user.getName());
				userResponse[count].setEmail(user.getEmail());
				count++;
			}
		}
		return userResponse;
	}

	public UserResponse viewUser(String name) {
		UserResponse userResponse = null;
		// write logic for getting user from database
		List<User> userList = userDAO.findByName(name);
		if (userList != null && userList.size()>0) {
			userResponse = new UserResponse();
			userResponse.setEmail(userList.get(0).getEmail());
			userResponse.setName(userList.get(0).getName());
		}
		return userResponse;
	}

	public UserResponse deleteUser(String userName) {
		// write logic for deleting user from database
		userDAO.deleteUser(userName);
		UserResponse userResponse = new UserResponse();
		userResponse.setName(userName);
		return userResponse;
	}

	public UserResponse updateUser(UserRequest userRequest) {
		UserResponse userResponse = new UserResponse();
		// write logic for update user to database
		User user = new User();
		user.setName(userRequest.getName());
		user.setEmail(userRequest.getEmail());

		userDAO.updateUser(user);
		userResponse.setName(user.getName());
		userResponse.setEmail(user.getEmail());

		return userResponse;
	}

}
