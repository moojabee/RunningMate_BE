package com.lswr.demo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lswr.demo.model.dao.BoardDao;
import com.lswr.demo.model.dto.Board;
import com.lswr.demo.model.dto.BoardImg;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardDao boardDao;

	// 팔로워 게시글 조회
	@Override
	public List<Board> getFollowBoardList(Long userId) {
		List<Long> userIds = boardDao.getFollowingUserIds(userId);
        return boardDao.getBoardsByUserIds(userIds);
	}

	// 동네 게시글 조회
	@Override
	public List<Board> getNeighborBoardList(Long userId) {
		String address = boardDao.getAddress(userId);
		List<Long> userIds = boardDao.getUserIdsByAddress(address);
		
		// 유저 공개 시 조회할 게시글 목록에 본인id추가
		int privateStatus = boardDao.getPrivateStatus(userId);
		if (privateStatus == 1) {
            userIds.add(userId);
        }
		
		return boardDao.getBoardsByUserIds(userIds);
	}


	// 게시글 생성
	@Override
    public void createBoard(Board board) {
        boardDao.insertBoard(board);
        if (board.getBoardImg() != null) {
            for (BoardImg img : board.getBoardImg()) {
                img.setBoardId(board.getBoardId());
                boardDao.insertBoardImg(img);
            }
        }
    }

	// 게시글 수정
    @Override
    public void updateBoard(Board board) {
        boardDao.updateBoard(board); // 기본 게시글 내용 업데이트

        // 새 이미지가 있을 경우
        if (board.getBoardImg() != null && !board.getBoardImg().isEmpty()) {
            boardDao.deleteBoardImg(board.getBoardId());  // 기존 이미지 삭제
            for (BoardImg img : board.getBoardImg()) {
                img.setBoardId(board.getBoardId());
                boardDao.insertBoardImg(img);  // 새 이미지 추가
            }
        }
    }

    // 게시글 삭제
    @Override
    public void deleteBoard(Long boardId, Long userId) {
        boardDao.deleteBoard(boardId, userId);
    }
	
    
    @Override
    public void getBoardLike(Long userId, Long boardId) {
    	boardDao.likeBoard(userId, boardId);
    }
    
    @Override
    public void getBoardDislike(Long userId, Long boardId) {
    	boardDao.dislikeBoard(userId, boardId);
    }
	
}
