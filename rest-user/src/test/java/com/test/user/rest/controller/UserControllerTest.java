package com.test.user.rest.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.test.user.rest.config.AbstractManagementTests;
import com.test.user.rest.request.UserRequest;
import com.test.user.rest.response.UserResponse;
import com.test.user.rest.service.UserService;
import com.test.user.rest.util.TestUtil;

public class UserControllerTest extends AbstractManagementTests {

	@Autowired
	@Mock
	UserService userServiceMock;
	
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;


	@Before
	public void setUp() {
		System.out.println("Beginning setUp");
		// We have to reset our mock between tests because the mock objects
		// are managed by the Spring container. If we would not reset them,
		// stubbing and verified behavior would "leak" from one test to another.
		Mockito.reset(userServiceMock);
		//ReflectionTestUtils.setField(userServiceMock, "userDAO", userDAO);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		System.out.println("End setUp");
	}
	
	@Test
	public void testPing(){
		System.out.println("Beginning testPing");
		try {
			ResultActions ra=mockMvc.perform(get("/ping"));
			ra.andExpect(status().isOk());
			System.out.println(ra);
					//.andExpect(jsonPath("$result", is("Hello")));

		} catch (Exception e) {
			e.printStackTrace();
		}
		verifyNoMoreInteractions(userServiceMock);
		System.out.println("End testPing");
	}
	
	
	@Test
	public void testViewUser() {
		System.out.println("Beginning testViewUser");
		String name = "raja";
		UserResponse userResponse = new UserResponse();
		userResponse.setName("raja");
		userResponse.setEmail("rajashaker@gmail.com");

		when(userServiceMock.viewUser((any(String.class)))).thenReturn(userResponse);
		try {
			mockMvc.perform(get("/users/{userName}", name).contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk()).andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
					.andExpect(jsonPath("$result.email", is("rajashaker@gmail.com")));

		} catch (Exception e) {
			e.printStackTrace();
		}
		//verifyNoMoreInteractions(userServiceMock);
		System.out.println("End testViewUser");
	}

	@Test
	public void testViewAllUsers() {
		System.out.println("Begin testViewAllUsers");

		UserResponse[] userResponseArray = new UserResponse[2];
		userResponseArray[0] = new UserResponse();
		userResponseArray[0].setName("raja");
		userResponseArray[0].setEmail("rajashaker@gmail.com");

		userResponseArray[1] = new UserResponse();
		userResponseArray[1].setName("shaker");
		userResponseArray[1].setEmail("shaker@gmail.com");

		when(userServiceMock.viewAllUsers()).thenReturn(userResponseArray);
		try {
			mockMvc.perform(get("/users")).andExpect(status().isOk())
					.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
					.andExpect(jsonPath("$result[1].email", is("shaker@gmail.com")));
			System.out.println("unit test successful");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//verifyNoMoreInteractions(userServiceMock);
		System.out.println("End testViewAllUsers");
	}

	@Test
	public void testCreateUser() {
		System.out.println("Start testCreateUser");

		UserRequest userRequest = new UserRequest();
		userRequest.setName("raja");
		userRequest.setEmail("raja@gmail.com");
		UserResponse userResponse = new UserResponse();
		userResponse.setName("raja");
		userResponse.setEmail("raja@gmail.com");

		when(userServiceMock.createUser(any(UserRequest.class))).thenReturn(userResponse);
		try {
			mockMvc.perform(post("/users").contentType(TestUtil.APPLICATION_JSON_UTF8)
					.content(TestUtil.convertObjectToJsonBytes(userRequest))).andExpect(status().isOk())
					.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
					.andExpect(jsonPath("$result.email", is("raja@gmail.com")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//verifyNoMoreInteractions(userServiceMock);
		System.out.println("End testCreateUser");
	}

	@Test
	public void testUpdateUser() {
		System.out.println("Start testUpdateUser");
		
		UserRequest userRequest = new UserRequest();
		userRequest.setName("raja");
		userRequest.setEmail("shaker@gmail.com");
		UserResponse userResponse = new UserResponse();
		userResponse.setName("raja");
		userResponse.setEmail("raja@gmail.com");

		when(userServiceMock.updateUser(any(UserRequest.class))).thenReturn(userResponse);
		try {
			mockMvc.perform(post("/users/{userName}", "raja").header("x-http-method-override", "put")
					.contentType(TestUtil.APPLICATION_JSON_UTF8)
					.content(TestUtil.convertObjectToJsonBytes(userRequest))).andExpect(status().isOk())
					.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
					.andExpect(jsonPath("$result.email", is("raja@gmail.com")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//verifyNoMoreInteractions(userServiceMock);
		System.out.println("End testUpdateUser");
	}

	@Test
	public void testDeleteUser() {
		System.out.println("Begin testDeleteUser");

		UserRequest userRequest = new UserRequest();
		userRequest.setName("raja");
		userRequest.setEmail("shaker@gmail.com");
		UserResponse userResponse = new UserResponse();
		userResponse.setName("raja");
		userResponse.setEmail("shaker@gmail.com");

		when(userServiceMock.deleteUser(any(String.class))).thenReturn(userResponse);
		try {
			mockMvc.perform(post("/users/{userName}", "raja").header("x-http-method-override", "delete")
					.contentType(TestUtil.APPLICATION_JSON_UTF8)
					.content(TestUtil.convertObjectToJsonBytes(userRequest))).andExpect(status().isOk())
					.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
					.andExpect(jsonPath("$result.email", is("shaker@gmail.com")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//verifyNoMoreInteractions(userServiceMock);
		System.out.println("End testDeleteUser");

	}

}
