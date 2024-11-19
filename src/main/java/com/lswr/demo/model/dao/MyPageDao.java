package com.lswr.demo.model.dao;

import java.util.List;

import com.lswr.demo.model.dto.Board;
import com.lswr.demo.model.dto.MyPage;
import com.lswr.demo.model.dto.Run;

public interface MyPageDao {
	
	// 유저 정보 조회
    MyPage getUserInfo(Long userId);

    // 유저 게시글 조회
    List<Board> getUserBoards(Long userId);

    // 유저 런닝 기록 조회
    List<Run> getUserRuns(Long userId);
	
}
