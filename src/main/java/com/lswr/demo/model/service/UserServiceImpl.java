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
	
    public boolean isEmailDuplicated(String email) {
        Optional<User> user = userDao.selectUserByEmail(email);
        if(user.isEmpty()) return false;
        return true;
    }
    
    public boolean isNicknameDuplicated(String nickname) {
        Optional<User> user = userDao.selectUserByNickname(nickname);
        if(user.isEmpty()) return false;
        return true;
    } 
	
	@Override
	public List<User> getUserList() {
		List<User> list = userDao.selectAll();
		return list;
	}
	
	@Override
	public User getUser(String email) {
		Optional<User> user = userDao.selectUserByEmail(email);
		if(user.isEmpty()) return null;
		return user.get();
	}

	@Override
	public void registUser(User user) {
		userDao.insertUser(user);
	}

	@Override
	public boolean loginUser(LoginDto loginDto) {
		Optional<User> user = userDao.login(loginDto);
		return user.isPresent();
	}

	@Override
	public void updateUser(User user) {
		userDao.updateUser(user);
	}
}
