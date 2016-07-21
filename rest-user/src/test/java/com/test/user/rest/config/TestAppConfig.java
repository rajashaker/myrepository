package com.test.user.rest.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.test.user.rest.service.UserService;
import com.test.user.rest.service.UserServiceImpl;

@Configuration
public class TestAppConfig {
	@Bean
	public UserService userService() {
		return Mockito.mock(UserService.class);
	}
}
