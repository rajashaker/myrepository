package com.test.user.rest.service;

import com.test.user.rest.request.UserRequest;
import com.test.user.rest.response.UserResponse;

public interface UserService {

	public UserResponse createUser(UserRequest userRequest);
	public UserResponse[] viewAllUsers();
	public UserResponse viewUser(String name);
	public UserResponse deleteUser(String userName);
	public UserResponse updateUser(UserRequest userRequest);
}
