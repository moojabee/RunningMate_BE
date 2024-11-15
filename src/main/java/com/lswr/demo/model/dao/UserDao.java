package com.lswr.demo.model.dao;

import java.util.List;
import java.util.Optional;

import com.lswr.demo.model.dto.LoginDto;
import com.lswr.demo.model.dto.User;

public interface UserDao {
	
	List<User> selectAll();
	void insertUser(User user);
	void updateUser(User user);
	User selectUserByEmail(String email);
	User selectUserByNickname(String nickname);
	User login(LoginDto loginDto);
}
