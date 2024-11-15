package com.lswr.demo.model.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lswr.demo.model.dao.BoardDao;
import com.lswr.demo.model.dto.Board;
import com.lswr.demo.model.dto.BoardImg;
import com.lswr.demo.util.S3Uploader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private S3Uploader s3Uploader;

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
    public void createBoard(Board board, List<MultipartFile> files) {
        boardDao.insertBoard(board);  // 게시글 내용 저장
        try {
	        // 이미지 파일이 있는 경우 처리
	        if (files != null && !files.isEmpty()) {
	            List<BoardImg> boardImgs = new ArrayList<>();
	            for (MultipartFile file : files) {
	                String imageUrl = s3Uploader.upload(file);  // S3에 업로드 후 URL 반환
	                BoardImg boardImg = new BoardImg();
	                boardImg.setBoardId(board.getBoardId());
	                boardImg.setFileName(imageUrl);
	                boardImgs.add(boardImg);
	            }
	            // 이미지 URL을 DB에 저장
	            for (BoardImg img : boardImgs) {
	                boardDao.insertBoardImg(img);
	            }
	        }
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);  // 커스텀 예외 처리
        }
    }

	// 게시글 수정
    @Override
    public void updateBoard(Board board, List<MultipartFile> files) {
        boardDao.updateBoard(board); // 게시글 내용 업데이트
        boardDao.deleteBoardImg(board.getBoardId());  // 기존 이미지 삭제
        try {
        	// 기존 이미지 삭제 후 새로운 이미지 저장
        	if (files != null && !files.isEmpty()) {
        		List<BoardImg> boardImgs = new ArrayList<>();
        		for (MultipartFile file : files) {
        			String imageUrl = s3Uploader.upload(file);  // S3에 업로드 후 URL 반환
        			BoardImg boardImg = new BoardImg();
        			boardImg.setBoardId(board.getBoardId());
        			boardImg.setFileName(imageUrl);
        			boardImgs.add(boardImg);
        		}
        		// 새로운 이미지 URL을 DB에 저장
        		for (BoardImg img : boardImgs) {
        			boardDao.insertBoardImg(img);
        		}
        	}
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);  // 일반 예외 처리
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
