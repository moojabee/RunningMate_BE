package com.lswr.demo.model.service;

import java.util.List;

import com.lswr.demo.model.dto.Board;

public interface BoardService {
	
	// 게시글 리스트 - 팔로워
	public List<Board> getFollowBoardList(Long userId);
	
	// 게시글 리스트 - 동네
	public List<Board> getNeighborBoardList(Long userId);
	
	// 게시글 댓글 개수
	public int getBoardCommentCount(Long boardId);
	
	// 게시글 좋아요 개수
	public int getBoardLikeCount(Long boardId);
	
	// 게시글 좋아요
	public void getBoardLike(Long userId, Long boardId);
	
	// 게시글 좋아요 취소
	public void getBoardDislike(Long userId, Long boardId);
	
	// 게시글 생성
	public void createBoard(Long userId, String content, List<String> boardImages);
	
	// 게시글 수정
	public void updateBoard(Long boardId, String content, List<String> boardImages);
	
	// 게시글 삭제
	public void delteBoard(Long boardId);
	
}
