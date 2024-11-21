package com.lswr.demo.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lswr.demo.model.dao.UserDao;
import com.lswr.demo.model.dto.LoginDto;
import com.lswr.demo.model.dto.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;
	
	@Override
	public boolean isValidEmail(String email) {
		User user =userDao.selectUserByEmail(email);
		if(user==null) return true;
		else return false;
	}

	@Override
	public boolean isValidNickname(String nickname) {
		User user =userDao.selectUserByEmail(nickname);
		if(user==null) return true;
		else return false;
	}

	@Override
	public List<User> getUserList() {
		List<User> list = userDao.selectAll();
		return list;
	}
	
	@Override
	public User getUser(String email) {
		User user = userDao.selectUserByEmail(email);
		if(user==null) return null;
		return user;
	}

	@Override
	public User getUserById(Long id) {
		User user = userDao.selectUserById(id);
		if(user==null) return null;
		return user;
	}
	
	@Override
	public void registUser(User user) {
		log.info(user.toString());
		userDao.insertUser(user);
	}

	@Override
	public boolean loginUser(LoginDto loginDto) {
		User user = userDao.login(loginDto);
		return user!=null;
	}

	@Override
	public void updateUser(User user) {
		userDao.updateUser(user);
	}
}
