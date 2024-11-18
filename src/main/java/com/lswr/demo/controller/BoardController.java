package com.lswr.demo.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.lswr.demo.model.dto.Comment;
import com.lswr.demo.model.dto.User;
import com.lswr.demo.model.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;

	// 팔로워 게시글 조회
	@GetMapping("/follow")
	public ResponseEntity<?> followList(@RequestAttribute("userId") String id) { // ResquestAttribute로 수정
		long userId = Long.parseLong(id);
		List<Board> list = boardService.getFollowBoardList(userId);
		return new ResponseEntity<List<Board>>(list, HttpStatus.OK);
	}

	// 동네 게시글 조회
	@GetMapping("/neighbor")
	public ResponseEntity<?> neighborList(@RequestAttribute("userId") String id) {
		long userId = Long.parseLong(id);
		List<Board> list = boardService.getNeighborBoardList(userId);
		return new ResponseEntity<List<Board>>(list, HttpStatus.OK);
	}

	// 게시글 작성
	@PostMapping("/create")
	public ResponseEntity<?> createBoard(@RequestAttribute("userId") String id,
	                                     @RequestParam("content") String content,
	                                     @RequestParam(value = "boardImg", required = false) List<MultipartFile> files) {
		long userId = Long.parseLong(id);
	    Board board = new Board();
	    board.setUserId(userId);
	    board.setContent(content);
	    
	    List<MultipartFile> safeFiles = files != null ? files : Collections.emptyList();
	    
	    boardService.createBoard(board, safeFiles);  // files를 따로 전달
	    return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	// 게시글 수정
	@PutMapping("/update/{boardId}")
	public ResponseEntity<?> updateBoard(
	        @RequestAttribute("userId") String id,
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

	    boardService.updateBoard(board, safeDeleteImgIds, safeFiles);
	    return ResponseEntity.ok().build();
	}


	// 게시글 삭제
	@DeleteMapping("/delete/{boardId}")
	public ResponseEntity<?> deleteBoard(@RequestAttribute("userId") String id, @PathVariable Long boardId) {
		long userId = Long.parseLong(id);
	    boardService.deleteBoard(boardId, userId);
	    return ResponseEntity.ok().build();
	}
	
	// 게시글 작성자 로그인 사용자 확인
	@GetMapping("/userCheck")
	public ResponseEntity<Boolean> userCheck(@RequestAttribute("userId") String id, 
											 @RequestParam("writerId") Long writerId) {
        long userId = Long.parseLong(id);
        boolean check = userId == writerId;
        return ResponseEntity.ok(check);
    }

	// 좋아요 상태 변경
    @PostMapping("/like/{boardId}")
    public ResponseEntity<Integer> toggleLike(@RequestAttribute("userId") String id, 
								             @PathVariable Long boardId) {
        long userId = Long.parseLong(id);
        int likeCheck = boardService.toggleLike(userId, boardId);
        return ResponseEntity.ok(likeCheck);
    }
	
	
}
