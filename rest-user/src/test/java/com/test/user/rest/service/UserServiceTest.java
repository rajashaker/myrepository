package com.test.user.rest.service;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.util.ReflectionTestUtils;

import com.test.user.dao.UserDAO;
import com.test.user.model.User;
import com.test.user.rest.request.UserRequest;
import com.test.user.rest.response.UserResponse;

public class UserServiceTest {
	
	@Mock
	UserDAO userDAO;
	
	@Spy
	UserServiceImpl userService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(userService, "userDAO", userDAO);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateUser() {
		UserRequest userRequest = new UserRequest();
		userRequest.setName("service");
		userRequest.setEmail("service@service.com");

		Mockito.when(userDAO.createUser(any(User.class))).thenReturn(true);
		
		UserResponse userResponse = userService.createUser(userRequest);
    	Assert.assertNotNull(userResponse);
    	Assert.assertEquals("service", userResponse.getName());
    	Assert.assertEquals("service@service.com", userResponse.getEmail());
	}

	@Test
	public void testViewAllUsers() {
		List<User> userList = new ArrayList<User>();
		User user1 = new User();
		User user2 = new User();
		user1.setEmail("service1@service1.com");
		user2.setEmail("service2@service2.com");
		user1.setName("service1");
		user2.setName("service2");
		
		userList.add(user1);
		userList.add(user2);
		
		Mockito.when(userDAO.findAll()).thenReturn(userList);
		
		UserResponse [] userResponse = userService.viewAllUsers();
    	
		Assert.assertNotNull(userResponse);
    	Assert.assertEquals("service1", userResponse[0].getName());
    	Assert.assertEquals("service2@service2.com", userResponse[1].getEmail());
	}

	@Test
	public void testViewUser() {
		
		User user = new User();
		user.setName("service3");
		user.setEmail("service3@service3.com");
		List<User> userList = new ArrayList<User>();
		userList.add(user);
		Mockito.when(userDAO.findByName(any(String.class))).thenReturn(userList);
		
		UserResponse userResponse = userService.viewUser("vuser");
		
		Assert.assertNotNull(userResponse);
    	Assert.assertEquals("service3", userResponse.getName());
    	Assert.assertEquals("service3@service3.com", userResponse.getEmail());		

	}

	@Test
	public void testDeleteUser() {

		Mockito.when(userDAO.deleteUser(any(String.class))).thenReturn(true);
		
		UserResponse userResponse = userService.deleteUser("vuser");
		
		Assert.assertNotNull(userResponse);
    	Assert.assertEquals("vuser", userResponse.getName());
	}

	@Test
	public void testUpdateUser() {
		UserRequest userRequest = new UserRequest();
		userRequest.setEmail("service5@service5.com");
		userRequest.setName("service5");
		Mockito.when(userDAO.updateUser(any(User.class))).thenReturn(true);
		
		UserResponse userResponse = userService.updateUser(userRequest);
		Assert.assertNotNull(userResponse);
    	Assert.assertEquals("service5", userResponse.getName());
    	Assert.assertEquals("service5@service5.com", userResponse.getEmail());

	}

}
