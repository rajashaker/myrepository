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
    	
    	List<User> userList = userDao.findByName("abc");
  
    	Assert.assertNotNull(userList);
    	Assert.assertEquals(1, userList.get(0).getId());
    	Assert.assertEquals("abc", userList.get(0).getName());
    	Assert.assertEquals("abc@abc.com", userList.get(0).getEmail());
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
    	
    	User user1 = new User();
    	user1.setName("test1");
    	user1.setEmail("test1@test1.com");
    	
    	User user2 = new User();
    	user2.setName("test2");
    	user2.setEmail("test2@test2.com");
    	
    	userDao.createUser(user1);
    	userDao.createUser(user2);
    	
    	List<User> userList = userDao.findByName("test1");
    	
    	Assert.assertNotNull(userList);
    	Assert.assertEquals("test1", userList.get(0).getName());
    	Assert.assertEquals("test1@test1.com", userList.get(0).getEmail());
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
    	
    	List<User> createdUser = userDao.findByName("test");
    	  
    	Assert.assertNotNull(createdUser);
    	Assert.assertEquals("test", createdUser.get(0).getName());
    	Assert.assertEquals("test@test.com", createdUser.get(0).getEmail());
    	
    	createdUser.get(0).setEmail("test@gmail.com");
    	
    	userDao.updateUser(createdUser.get(0));
    	
    	List<User> updatedUser = userDao.findByName("test");
  	  
    	Assert.assertNotNull(updatedUser);
    	Assert.assertEquals("test", updatedUser.get(0).getName());
    	Assert.assertEquals("test@gmail.com", updatedUser.get(0).getEmail());
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
    	
    	List<User> createdUser = userDao.findByName("zabc");
    	  
    	Assert.assertNotNull(createdUser);
    	Assert.assertEquals("zabc", createdUser.get(0).getName());
    	Assert.assertEquals("test@zabc.com", createdUser.get(0).getEmail());
    	
    	userDao.deleteUser("zabc");
    	
    	List<User> deletedUser = userDao.findByName("zabc");
  	  
    	Assert.assertNull(deletedUser);
    }    

    @After
    public void tearDown() {
        db.shutdown();
    }

}
