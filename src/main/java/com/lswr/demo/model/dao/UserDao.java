package com.lswr.demo.model.dao;

import java.util.List;
import java.util.Optional;

import com.lswr.demo.model.dto.LoginDto;
import com.lswr.demo.model.dto.User;

public interface UserDao {
	
	List<User> selectAll();
	void insertUser(User user);
	Optional<User> selectUserByEmail(String email);
	Optional<User> selectUserByNickname(String nickname);
	Optional<User> login(LoginDto loginDto);
}
