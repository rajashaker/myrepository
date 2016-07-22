package com.test.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.test.user.model.User;

@Repository("userDAO")
public class UserDaoImpl implements UserDAO {

	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public List<User> findByName(String name) {
		List<User> userList = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);

		String sql = "SELECT * FROM user_table WHERE name=:name";

		try {
			userList = namedParameterJdbcTemplate.query(sql, params, new UserMapper());
			if(null!=userList && userList.size()<1){
				userList=null;
			}
		} catch (EmptyResultDataAccessException e) {
			// do nothing just make result =null
			userList = null;
		}

		// new BeanPropertyRowMapper(Customer.class));

		return userList;
	}

	public List<User> findAll() {
		Map<String, Object> params = new HashMap<String, Object>();
		List<User> result = null;
		String sql = "SELECT * FROM user_table";
		try {
			result = namedParameterJdbcTemplate.query(sql, params, new UserMapper());
		} catch (EmptyResultDataAccessException e) {
			// do nothing just make result =null
			result = null;
		}
		return result;
	}

	public boolean createUser(User user) {
		String insertQuery = "insert into user_table (id,name,email,created_date) values (:id,:name,:email,:created_date)";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", nextId());
		params.put("name", user.getName());
		params.put("email", user.getEmail());
		params.put("created_date", new Date());
		namedParameterJdbcTemplate.update(insertQuery, params);
		return true;
	}

	public boolean updateUser(User user) {
		String updateQuery = "update user_table set email=:email where name=:name";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", user.getName());
		params.put("email", user.getEmail());
		namedParameterJdbcTemplate.update(updateQuery, params);
		return true;
	}

	public boolean deleteUser(String username) {
		String deleteQuery = "delete from user_table where name=:name";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", username);
		namedParameterJdbcTemplate.update(deleteQuery, params);
		return true;
	}

	public long nextId() {
		// TODO Auto-generated method stub
		String selectQuery = "select * from user_table where id = (select max(id) from user_table)";
		Map<String, Object> params = new HashMap<String, Object>();
		User user = namedParameterJdbcTemplate.queryForObject(selectQuery, params, new UserMapper());
		return user.getId() + 1;
	}

	private static final class UserMapper implements RowMapper<User> {

		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			user.setCreatedDate(rs.getDate("created_date"));
			return user;
		}
	}

}
