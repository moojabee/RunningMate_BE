package com.lswr.demo.model.service;

import java.util.List;
import com.lswr.demo.model.dto.User;

public interface UserService {
	//전체 사용자 목록 불러오기
	public List<User> getUserList();

}
