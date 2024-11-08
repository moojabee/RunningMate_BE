package com.lswr.demo.model.service;

import java.util.List;

import com.lswr.demo.model.dto.LoginDto;
import com.lswr.demo.model.dto.User;

public interface UserService {
	
	// 이메일 중복 검사
	public boolean isEmailDuplicated(String email);
	
	// 닉네임 중복 검사
	public boolean isNicknameDuplicated(String nickname);
	
	// 전체 사용자 목록 불러오기
	public List<User> getUserList();
	
	// 사용자 정보 불러오기
	public User getUser(String email);

	// 회원가입
	public void registUser(User user);
	
	// 로그인
	public boolean loginUser(LoginDto loginDto);
}
