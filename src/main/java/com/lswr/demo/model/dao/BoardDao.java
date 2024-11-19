package com.lswr.demo.model.dao;

import java.util.List;

import com.lswr.demo.model.dto.Board;
import com.lswr.demo.model.dto.BoardImg;
import com.lswr.demo.model.dto.Comment;
import com.lswr.demo.model.dto.User;

public interface BoardDao {
	
	// 팔로잉 유저 조회
	List<Long> getFollowingUserIds(Long userId);
	
	// 유저 주소
	String getAddress(Long userId);
	// 같은 주소 유저 조회
	List<Long> getUserIdsByAddress(String address);
	
	// 유저 공개 여부
	int getPrivateStatus(Long userId);
	
	// 해당 유저들의 게시글 조회
	List<Board> getBoardsByUserIds(List<Long> userIds);
	
	// 게시글 삽입
	void insertBoard(Board board);
	void insertBoardImg(BoardImg boardImg);
	
	// 게시글 수정 (이미지 삭제 후 다시 삽입)
    void updateBoard(Board board);
    void deleteBoardImgByIds(List<Long> deleteImgIds);
    
    // 게시글 삭제
    void deleteBoard(Long boardId, Long userId);

    // 좋아요 눌렀는지 확인
    boolean isLikedByUser(Long userId, Long boardId);

    // 좋아요 추가
    void addLike( Long userId, Long boardId);

    // 좋아요 제거
    void removeLike(Long userId, Long boardId);
	
}
