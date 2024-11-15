package com.lswr.demo.model.service;

import java.util.List;

import com.lswr.demo.model.dto.LoginDto;
import com.lswr.demo.model.dto.User;

public interface UserService {
	
	// 사용자 이메일 유효성
	public boolean isValidEmail(String email);
	
	// 사용자 닉네임 유효성
	public boolean isValidNickname(String nickname);
	
	// 전체 사용자 목록 불러오기
	public List<User> getUserList();
	
	// 사용자 정보 불러오기
	public User getUser(String email);

	// 회원가입
	public void registUser(User user);
	
	// 로그인
	public boolean loginUser(LoginDto loginDto);
	
	// 회원정보 변경
	public void updateUser(User user);
}
