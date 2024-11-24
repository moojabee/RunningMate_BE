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
import com.lswr.demo.model.dto.Follow;
import com.lswr.demo.model.dto.MyPage;
import com.lswr.demo.model.dto.Run;
import com.lswr.demo.model.dto.User;
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
					boardImg.setBoardUrl(imageUrl);
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
	public boolean isFollower(Long writerId, Long loginId) {
		return myPageDao.isFollower(writerId, loginId) > 0;
	}
	
	// 팔로워 요청 확인
	@Override
	public boolean isFollowRequest(Long writerId, Long loginId) {
	    return myPageDao.isFollowRequest(writerId, loginId) > 0;
	}

	
	// 개인 정보 업데이트
	@Override
    public void updateUserInfo(User user, MultipartFile userImg) {
        user.setPassword(user.getPassword());
        // S3에 이미지 업로드
        if (userImg != null) {
            try {
                String userImgUrl = s3Uploader.upload(userImg); // S3 업로드
                user.setUserImg(userImgUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload image", e);
            }
        }

        // DB 업데이트 요청
        myPageDao.updateUserInfo(user);
    }
	
	
	// 팔로워 리스트
	@Override
    public List<Follow> getFollower(Long userId) {
        return myPageDao.findFollower(userId);
    }

	// 팔로잉 리스트
    @Override
    public List<Follow> getFollowing(Long userId) {
        return myPageDao.findFollowing(userId);
    }

    // 팔로우 추가
    @Override
    public void addFollowing(long loginId, long targetId) {
        Integer isPrivate = myPageDao.isPrivate(targetId);

        // status 결정 (0: 요청 대기 상태, 1: 즉시 팔로우)
        int status = isPrivate == 1 ? 1 : 0;

        myPageDao.insertFollowing(loginId, targetId, status);
    }
    
    // 팔로워 삭제
    @Override
    public void deleteFollower(long targetId, long loginId) {
        myPageDao.deleteFollower(targetId, loginId);
    }

    // 팔로잉 삭제
    @Override
    public void deleteFollowing(long loginId, long targetId) {
        myPageDao.deleteFollowing(loginId, targetId);
    }

    // 팔로워 상태 변경
    @Override
    public void updateFollowStatus(long targetId, long loginId) {
        myPageDao.updateFollowStatus(targetId, loginId);
    }
    
}
