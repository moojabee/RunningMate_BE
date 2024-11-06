package com.lswr.demo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lswr.demo.model.dao.BoardDao;
import com.lswr.demo.model.dto.Board;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardDao boardDao;

	@Override
	public List<Board> getFollowBoardList(Long userId) {
		return boardDao.selectFollowAll(userId);
	}

	@Override
	public List<Board> getNeighborBoardList(Long userId) {
		return boardDao.selectNeighborAll(userId);
	}

	@Override
	public int getBoardCommentCount(Long boardId) {
		return boardDao.countComment(boardId);
	}

	@Override
	public int getBoardLikeCount(Long boardId) {
		return boardDao.countLike(boardId);
	}

	@Override
	public void getBoardLike(Long userId, Long boardId) {
		boardDao.likeBoard(userId, boardId);
	}

	@Override
	public void getBoardDislike(Long userId, Long boardId) {
		boardDao.dislikeBoard(userId, boardId);
	}

	@Override
	public void createBoard(Long userId, String content, List<String> boardImages) {
		Board board = new Board();
        board.setUserId(userId);
        board.setContent(content);
        boardDao.insertBoard(board);
        
        
	}

	@Override
	public void updateBoard(Long boardId, String content, List<String> boardImages) {
		Board board = new Board();
        board.setBoardId(boardId);
        board.setContent(content);
        boardDao.updateBoard(board);
        
        
	}

	@Override
	public void delteBoard(Long boardId) {
		Board board = new Board();
        board.setBoardId(boardId);
        boardDao.deleteBoard(board);

		
	}
	
	
	
	
	
}
