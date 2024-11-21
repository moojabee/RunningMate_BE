package com.lswr.demo.model.dao;

import java.util.List;

import com.lswr.demo.model.dto.Board;
import com.lswr.demo.model.dto.BoardImg;
import com.lswr.demo.model.dto.Follow;
import com.lswr.demo.model.dto.MyPage;
import com.lswr.demo.model.dto.Run;
import com.lswr.demo.model.dto.User;

public interface MyPageDao {
	
	// 유저 정보 조회
    MyPage getUserInfo(Long userId);

    // 유저 게시글 조회
    List<Board> getUserBoards(Long userId);
    
    // 유저 런닝 기록 조회
    List<Run> getUserRuns(Long userId);
    
    void insertMyBoardImg(BoardImg boardImg);
    // 게시글 수정 (이미지 삭제 후 다시 삽입)
    void updateMyBoard(Board board);
    void deleteMyBoardImgByIds(List<Long> deleteImgIds);
    
    // 게시글 삭제
    void deleteMyBoard(Long boardId, Long userId);
    
    // 좋아요 눌렀는지 확인
    boolean isLikedMyBoard(Long userId, Long boardId);
    // 좋아요 추가
    void addLikeMyBoard( Long userId, Long boardId);
    // 좋아요 제거
    void removeLikeMyBoard(Long userId, Long boardId);
    
    // 비공개 계정인지
    Integer isPrivate(Long userId);
    // 팔로워 목록에 있는지
    Integer isFollower(Long writerId, Long loginId);
    
    // 개인 정보 업데이트
    void updateUserInfo(User user);
	
    // 팔로워 팔로잉
    List<Follow> findFollower(Long userId);
    List<Follow> findFollowing(Long userId);
    
}
