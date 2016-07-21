package com.test.user.rest.config;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.test.user.rest.controller.UserController;
import com.test.user.rest.service.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestAppConfig.class,WebAppContext.class,UserController.class})

@WebAppConfiguration

public abstract class AbstractManagementTests {

}
