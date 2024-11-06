package com.lswr.demo.model.dao;

import java.util.List;

import com.lswr.demo.model.dto.Board;

public interface BoardDao {
	
	List<Board> selectFollowAll(Long userId);
	List<Board> selectNeighborAll(Long userId);
	
	void insertBoard(Board board);
	void updateBoard(Board board);
	void deleteBoard(Board board);
	
	void likeBoard(Long userId, Long boardId);
	void dislikeBoard(Long userId, Long boardId);
	
	int countLike(Long boardId);
	int countComment(Long boardId);
	
	
}
