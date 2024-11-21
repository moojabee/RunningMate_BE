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
import com.lswr.demo.model.dto.Comment;
import com.lswr.demo.model.dto.User;
import com.lswr.demo.util.S3Uploader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDao boardDao;

	@Autowired
	private S3Uploader s3Uploader;

	// 팔로워 게시글 조회
	@Override
	public List<Board> getFollowBoardList(Long userId) {
		List<Long> userIds = boardDao.getFollowingUserIds(userId);
		List<Board> boardList = boardDao.getBoardsByUserIds(userIds);

		// 각 게시글에 대해 likeCheck 설정
		for (Board board : boardList) {
			boolean isLiked = boardDao.isLikedByUser(userId, board.getBoardId());
			board.setLikeCheck(isLiked ? 1 : 0);
		}

		return boardList;
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

		List<Board> boardList = boardDao.getBoardsByUserIds(userIds);

		// 각 게시글에 대해 likeCheck 설정
		for (Board board : boardList) {
			boolean isLiked = boardDao.isLikedByUser(userId, board.getBoardId());
			board.setLikeCheck(isLiked ? 1 : 0);
		}

		return boardList;
	}

	// 게시글 생성
	@Override
	public void createBoard(Board board, List<MultipartFile> files) {
		boardDao.insertBoard(board); // 게시글 내용 저장
		try {
			// 이미지 파일이 있는 경우 처리
			if (files != null && !files.isEmpty()) {
				List<BoardImg> boardImgs = new ArrayList<>();
				for (MultipartFile file : files) {
					String imageUrl = s3Uploader.upload(file); // S3에 업로드 후 URL 반환
					BoardImg boardImg = new BoardImg();
					boardImg.setBoardId(board.getBoardId());
					boardImg.setBoardUrl(imageUrl);
					boardImgs.add(boardImg);
				}
				// 이미지 URL을 DB에 저장
				for (BoardImg img : boardImgs) {
					boardDao.insertBoardImg(img);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to upload image", e); // 커스텀 예외 처리
		}
	}

	// 게시글 수정
	@Override
	public void updateBoard(Board board, List<Long> deleteImgIds, List<MultipartFile> files) {
		boardDao.updateBoard(board); // 게시글 내용 업데이트

		// 삭제 요청 이미지 삭제
		if (!deleteImgIds.isEmpty()) {
			boardDao.deleteBoardImgByIds(deleteImgIds);
		}

		// 새로운 이미지 추가
		if (files != null && !files.isEmpty()) {
			List<BoardImg> boardImgs = new ArrayList<>();
			for (MultipartFile file : files) {
				try {
					String imageUrl = s3Uploader.upload(file); // S3 업로드
					BoardImg boardImg = new BoardImg();
					boardImg.setBoardId(board.getBoardId());
					boardImg.setBoardUrl(imageUrl);
					boardImgs.add(boardImg);
				} catch (IOException e) {
					throw new RuntimeException("Failed to upload image", e);
				}
			}

			for (BoardImg img : boardImgs) {
				boardDao.insertBoardImg(img);
			}
		}
	}

	// 게시글 삭제
	@Override
	public void deleteBoard(Long boardId, Long userId) {
		boardDao.deleteBoard(boardId, userId);
	}

	// 게시글 좋아요 변경
	@Override
	public int toggleLike(Long userId, Long boardId) {
		// 사용자가 이미 좋아요를 눌렀는지 확인
		boolean isLiked = boardDao.isLikedByUser(userId, boardId);

		if (isLiked) {
			// 이미 좋아요를 눌렀다면 제거
			boardDao.removeLike(userId, boardId);
			return 0; // 좋아요 제거 완료
		} else {
			// 좋아요가 없다면 추가
			boardDao.addLike(userId, boardId);
			return 1; // 좋아요 추가 완료
		}
	}

}
