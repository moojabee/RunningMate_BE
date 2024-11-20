package com.lswr.demo.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lswr.demo.model.dto.Board;
import com.lswr.demo.model.dto.MyPage;
import com.lswr.demo.model.dto.Run;
import com.lswr.demo.model.service.MyPageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/myPage")
@RequiredArgsConstructor
public class MyPageController {

	private final MyPageService myPageService;

	// 유저 정보 조회
	@GetMapping("/userInfo/{userId}")
	public ResponseEntity<?> getUserInfo(@PathVariable Long userId) {
		MyPage userInfo = myPageService.getUserInfo(userId);
		return new ResponseEntity<>(userInfo, HttpStatus.OK);
	}

	// 유저 게시글 조회
	@GetMapping("/userBoard/{userId}")
	public ResponseEntity<?> getUserBoard(@PathVariable Long userId, 
            							  @RequestAttribute("userId") String currentUserId) {
		long currUserId = Long.parseLong(currentUserId);
		List<Board> boards = myPageService.getUserBoard(userId, currUserId);
		return new ResponseEntity<>(boards, HttpStatus.OK);
	}

	// 유저 런닝 기록 조회
	@GetMapping("/userRun/{userId}")
	public ResponseEntity<?> getUserRun(@PathVariable Long userId) {
		List<Run> runs = myPageService.getUserRun(userId);
		return new ResponseEntity<>(runs, HttpStatus.OK);
	}

	// 게시글 삭제
	@DeleteMapping("/myBoard/delete/{boardId}")
	public ResponseEntity<?> deleteBoard(@RequestAttribute("userId") String id, 
										 @PathVariable Long boardId) {
		long userId = Long.parseLong(id);
		myPageService.deleteBoard(boardId, userId);
		return ResponseEntity.ok().build();
	}

	// 게시글 수정
	@PutMapping("/myBoard/update/{boardId}")
	public ResponseEntity<?> updateBoard(@RequestAttribute("userId") String id, 
										 @PathVariable long boardId,
										 @RequestParam("content") String content,
										 @RequestParam(value = "deleteImgIds", required = false) List<Long> deleteImgIds,
										 @RequestParam(value = "boardImg", required = false) List<MultipartFile> files) {

		long userId = Long.parseLong(id);
		Board board = new Board();
		board.setBoardId(boardId);
		board.setUserId(userId);
		board.setContent(content);

		List<Long> safeDeleteImgIds = deleteImgIds != null ? deleteImgIds : Collections.emptyList();
		List<MultipartFile> safeFiles = files != null ? files : Collections.emptyList();

		myPageService.updateBoard(board, safeDeleteImgIds, safeFiles);
		return ResponseEntity.ok().build();
	}

	// 좋아요 상태 변경
	@PostMapping("/myBoard/like/{boardId}")
	public ResponseEntity<Integer> toggleLike(@RequestAttribute("userId") String id, 
											  @PathVariable Long boardId) {
		long userId = Long.parseLong(id);
		int likeCheck = myPageService.toggleLike(userId, boardId);
		return ResponseEntity.ok(likeCheck);
	}
	
	// 특정 유저의 계정 공개 여부 확인
    @GetMapping("/isPrivate/{userId}")
    public ResponseEntity<Boolean> isPrivateAccount(@PathVariable Long userId) {
        boolean isPrivate = myPageService.isPrivate(userId);
        return ResponseEntity.ok(isPrivate);
    }

    // 팔로워 여부 확인
    @GetMapping("/isFollower/{userId}")
    public ResponseEntity<Boolean> isFollower(@PathVariable Long userId, 
    										  @RequestAttribute("userId") String id) {
    	long followerId = Long.parseLong(id);
        boolean isFollower = myPageService.isFollower(userId, followerId);
        return ResponseEntity.ok(isFollower);
    }
    
	// 게시글 작성자 로그인 사용자 확인
	@GetMapping("/userCheck")
	public ResponseEntity<Boolean> userCheck(@RequestAttribute("userId") String id, 
											 @RequestParam("writerId") Long writerId) {
        long userId = Long.parseLong(id);
        boolean check = userId == writerId;
        return ResponseEntity.ok(check);
    }

}
