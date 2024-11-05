package com.lswr.demo.model.dao;

import java.util.List;

import com.lswr.demo.model.dto.User;

public interface UserDao {
	
	List<User> selectAll();
	void insertUser(User user);
}
