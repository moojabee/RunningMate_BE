package com.lswr.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	@GetMapping("/follow/{userId}")
	public ResponseEntity<?> followList(@PathVariable long userId) { // ResquestAttribute로 수정
		List<Board> list = boardService.getFollowBoardList(userId);
		return new ResponseEntity<List<Board>>(list, HttpStatus.OK);
	}

	// 동네 게시글 조회
	@GetMapping("/neighbor/{userId}")
	public ResponseEntity<?> neighborList(@PathVariable long userId) {
		List<Board> list = boardService.getNeighborBoardList(userId);
		return new ResponseEntity<List<Board>>(list, HttpStatus.OK);
	}

	// 게시글 작성
	@PostMapping("/create/{userId}")
	public ResponseEntity<?> createBoard(@PathVariable Long userId,
	                                     @RequestParam("content") String content,
	                                     @RequestParam("boardImg") List<MultipartFile> files) {
	    Board board = new Board();
	    board.setUserId(userId);
	    board.setContent(content);
	    boardService.createBoard(board, files);  // files를 따로 전달
	    return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	// 게시글 수정
	@PutMapping("/update/{userId}/{boardId}")
	public ResponseEntity<?> updateBoard(@PathVariable Long userId,
										 @PathVariable Long boardId,
	                                     @RequestParam("content") String content,
	                                     @RequestParam("boardImg") List<MultipartFile> files) {
	    Board board = new Board();
	    board.setBoardId(boardId);
	    board.setUserId(userId);
	    board.setContent(content);
	    boardService.updateBoard(board, files);  // files를 따로 전달
	    return ResponseEntity.ok().build();
	}


	// 게시글 삭제
	@DeleteMapping("/delete/{boardId}")
	public ResponseEntity<?> deleteBoard(@PathVariable Long boardId, @RequestAttribute("userId") Long userId) {
		boardService.deleteBoard(boardId, userId);
		return ResponseEntity.ok().build();
	}
	
	
	
	
	
}
