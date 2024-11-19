package com.lswr.demo.model.service;

import java.util.List;

import com.lswr.demo.model.dto.MyPage;
import com.lswr.demo.model.dto.Run;

public interface MyPageService {
	
	// 유저 정보 조회
    MyPage getUserInfo(Long userId);
    
    // 유저 게시글 조회
    MyPage getUserBoard(Long userId);

    // 유저 런닝 기록 조회
    List<Run> getUserRun(Long userId);
	
}
