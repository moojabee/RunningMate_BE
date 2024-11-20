package com.lswr.demo.model.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lswr.demo.model.dao.MyPageDao;
import com.lswr.demo.model.dto.Board;
import com.lswr.demo.model.dto.BoardImg;
import com.lswr.demo.model.dto.MyPage;
import com.lswr.demo.model.dto.Run;
import com.lswr.demo.util.S3Uploader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MyPageServiceImpl implements MyPageService {

	@Autowired
	private MyPageDao myPageDao;

	@Autowired
	private S3Uploader s3Uploader;

	// 유저 정보
	@Override
	public MyPage getUserInfo(Long userId) {
		return myPageDao.getUserInfo(userId);
	}

	@Override
	public List<Board> getUserBoard(Long userId, Long currUserId) {
	    List<Board> boardList = myPageDao.getUserBoards(userId);
	    for (Board board : boardList) {
	        boolean isLiked = myPageDao.isLikedMyBoard(currUserId, board.getBoardId());
	        board.setLikeCheck(isLiked ? 1 : 0);
	    }
	    return boardList;
	}

	// 유저 런닝 기록
	@Override
	public List<Run> getUserRun(Long userId) {
		return myPageDao.getUserRuns(userId);
	}

	// 게시글 수정
	@Override
	public void updateBoard(Board board, List<Long> deleteImgIds, List<MultipartFile> files) {
		myPageDao.updateMyBoard(board); // 게시글 내용 업데이트

		// 삭제 요청 이미지 삭제
		if (!deleteImgIds.isEmpty()) {
			myPageDao.deleteMyBoardImgByIds(deleteImgIds);
		}

		// 새로운 이미지 추가
		if (files != null && !files.isEmpty()) {
			List<BoardImg> boardImgs = new ArrayList<>();
			for (MultipartFile file : files) {
				try {
					String imageUrl = s3Uploader.upload(file); // S3 업로드
					BoardImg boardImg = new BoardImg();
					boardImg.setBoardId(board.getBoardId());
					boardImg.setFileName(imageUrl);
					boardImgs.add(boardImg);
				} catch (IOException e) {
					throw new RuntimeException("Failed to upload image", e);
				}
			}

			for (BoardImg img : boardImgs) {
				myPageDao.insertMyBoardImg(img);
			}
		}
	}

	// 게시글 삭제
	@Override
	public void deleteBoard(Long boardId, Long userId) {
		myPageDao.deleteMyBoard(boardId, userId);
	}

	// 게시글 좋아요 변경
	@Override
	public int toggleLike(Long userId, Long boardId) {
		// 사용자가 이미 좋아요를 눌렀는지 확인
		boolean isLiked = myPageDao.isLikedMyBoard(userId, boardId);

		if (isLiked) {
			// 이미 좋아요를 눌렀다면 제거
			myPageDao.removeLikeMyBoard(userId, boardId);
			return 0; // 좋아요 제거 완료
		} else {
			// 좋아요가 없다면 추가
			myPageDao.addLikeMyBoard(userId, boardId);
			return 1; // 좋아요 추가 완료
		}
	}
	
	// 비공개 확인
	@Override
	public boolean isPrivate(Long userId) {
		return myPageDao.isPrivate(userId) == 1;
	}

	// 팔로워 확인
	@Override
	public boolean isFollower(Long userId, Long followerId) {
		return myPageDao.isFollower(userId, followerId) > 0;
	}

}
