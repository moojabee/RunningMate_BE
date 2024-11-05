package com.lswr.demo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lswr.demo.model.dao.UserDao;
import com.lswr.demo.model.dto.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;
	
	@Override
	public List<User> getUserList() {
		List<User> list = userDao.selectAll();
		return list;
	}
}
