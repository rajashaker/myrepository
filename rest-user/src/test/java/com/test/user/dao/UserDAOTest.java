package com.test.user.dao;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.test.user.model.User;



public class UserDAOTest {


    private EmbeddedDatabase db;

    
    @Before
    public void setUp() {
        //db = new EmbeddedDatabaseBuilder().addDefaultScripts().build();
    	db = new EmbeddedDatabaseBuilder()
    		.setType(EmbeddedDatabaseType.HSQL)
    		.addScript("db/sql/create-db.sql")
    		.addScript("db/sql/insert-data.sql")
    		.build();
    }

    @Test
    public void testFindByname() {
    	NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
    	UserDaoImpl userDao = new UserDaoImpl();
    	userDao.setNamedParameterJdbcTemplate(template);
    	
    	User user = userDao.findByName("abc");
  
    	Assert.assertNotNull(user);
    	Assert.assertEquals(1, user.getId());
    	Assert.assertEquals("abc", user.getName());
    	Assert.assertEquals("abc@abc.com", user.getEmail());

    }
    
    @Test
    public void testFindAll() {
    	NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
    	UserDaoImpl userDao = new UserDaoImpl();
    	userDao.setNamedParameterJdbcTemplate(template);
    	
    	List<User> userList = userDao.findAll();
    	
    	Assert.assertNotNull(userList);
    	Assert.assertEquals(1, userList.get(0).getId());
    	Assert.assertEquals("abc", userList.get(0).getName());
    	Assert.assertEquals("abc@abc.com", userList.get(0).getEmail());

    }
    
    @Test
    public void testCreateUser() {
    	NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
    	UserDaoImpl userDao = new UserDaoImpl();
    	userDao.setNamedParameterJdbcTemplate(template);
    	
    	User user = new User();
    	user.setName("test");
    	user.setEmail("test@test.com");
    	
    	userDao.createUser(user);
    	
    	User createdUser = userDao.findByName("test");
    	  
    	Assert.assertNotNull(createdUser);
    	Assert.assertEquals("test", createdUser.getName());
    	Assert.assertEquals("test@test.com", createdUser.getEmail());
    }
    
    @Test
    public void testUpdateUser() {
    	NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
    	UserDaoImpl userDao = new UserDaoImpl();
    	userDao.setNamedParameterJdbcTemplate(template);
    	
    	User user = new User();
    	user.setName("test");
    	user.setEmail("test@test.com");
    	
    	userDao.createUser(user);
    	
    	User createdUser = userDao.findByName("test");
    	  
    	Assert.assertNotNull(createdUser);
    	Assert.assertEquals("test", createdUser.getName());
    	Assert.assertEquals("test@test.com", createdUser.getEmail());
    	
    	createdUser.setEmail("test@gmail.com");
    	
    	userDao.updateUser(createdUser);
    	
    	User updatedUser = userDao.findByName("test");
  	  
    	Assert.assertNotNull(updatedUser);
    	Assert.assertEquals("test", updatedUser.getName());
    	Assert.assertEquals("test@gmail.com", updatedUser.getEmail());
    }
    
    
    @Test
    public void testDeleteUser() {
    	NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
    	UserDaoImpl userDao = new UserDaoImpl();
    	userDao.setNamedParameterJdbcTemplate(template);
    	
    	User user = new User();
    	user.setName("zabc");
    	user.setEmail("test@zabc.com");
    	
    	userDao.createUser(user);
    	
    	User createdUser = userDao.findByName("zabc");
    	  
    	Assert.assertNotNull(createdUser);
    	Assert.assertEquals("zabc", createdUser.getName());
    	Assert.assertEquals("test@zabc.com", createdUser.getEmail());
    	
    	userDao.deleteUser("zabc");
    	
    	User deletedUser = userDao.findByName("zabc");
  	  
    	Assert.assertNull(deletedUser);
    }    

    @After
    public void tearDown() {
        db.shutdown();
    }

}
