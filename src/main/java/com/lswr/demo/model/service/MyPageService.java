package com.lswr.demo.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lswr.demo.model.dto.Board;
import com.lswr.demo.model.dto.Follow;
import com.lswr.demo.model.dto.MyPage;
import com.lswr.demo.model.dto.Run;
import com.lswr.demo.model.dto.User;

public interface MyPageService {

	// 유저 정보 조회
	MyPage getUserInfo(Long userId);

	// 유저 게시글 조회
	List<Board> getUserBoard(Long userId, Long currentUserId);

	// 유저 런닝 기록 조회
	List<Run> getUserRun(Long userId);

	// 게시글 수정
	void updateBoard(Board board, List<Long> deleteImgIds, List<MultipartFile> files);
	// 게시글 삭제
	void deleteBoard(Long boardId, Long userId);

	// 좋아요 변경
	int toggleLike(Long userId, Long boardId);
	
	// 비공개 확인
	boolean isPrivate(Long userId);
	// 팔로워 확인
    boolean isFollower(Long userId, Long followerId);
    
    // 개인 정보 수정
    void updateUserInfo(User user, MultipartFile userImg);
    
    // 팔로워 팔로잉 리스트
    List<Follow> getFollower(Long userId);
    List<Follow> getFollowing(Long userId);
}
