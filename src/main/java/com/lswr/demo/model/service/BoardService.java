package com.lswr.demo.model.service;

import java.util.List;

import com.lswr.demo.model.dto.Board;

public interface BoardService {
	
	// 게시글 리스트 - 팔로워
	public List<Board> getFollowBoardList(Long userId);
	
	// 게시글 리스트 - 동네
	public List<Board> getNeighborBoardList(Long userId);
	
	// 게시글 좋아요
	public void getBoardLike(Long userId, Long boardId);
	
	// 게시글 좋아요 취소
	public void getBoardDislike(Long userId, Long boardId);
	
	// 게시글 생성
	public void createBoard(Board board);
	
	// 게시글 수정
	public void updateBoard(Board board);
	
	// 게시글 삭제
	public void deleteBoard(Long boardId, Long userId);
	
}
