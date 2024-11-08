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
	@GetMapping("/follow")
	public ResponseEntity<?> followList(Long userId) {
		List<Board> list = boardService.getFollowBoardList(userId);
		
		return new ResponseEntity<List<Board>>(list,HttpStatus.OK);
	}
	
	// 동네 게시글 조회
	@GetMapping("/neighbor")
	public ResponseEntity<?> neighborList(Long userId) {
		List<Board> list = boardService.getNeighborBoardList(userId);
		
		return new ResponseEntity<List<Board>>(list,HttpStatus.OK);
	}
	

//	//게시글 등록(Form 데이터 형식으로 넘어왔다)
//	@PostMapping("/board")
//	public ResponseEntity<?> write(@ModelAttribute Board board){
//		boardService.writeBoard(board); 
//		System.out.println(board);
//		
//		int id = board.getId();
//		return new ResponseEntity<Board>(board, HttpStatus.CREATED);
//	}
//	
//	//게시글 삭제
//	@DeleteMapping("/board/{id}")
//	public ResponseEntity<String> delete(@PathVariable("id") int id){
//		boolean isDeleted = boardService.removeBoard(id);
//		
//		if(isDeleted)
//			return ResponseEntity.status(HttpStatus.OK).body("Board deleted");
//		
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed");
//	}
//	
//	//게시글 수정
//	@PutMapping("/board/{id}")
//	public ResponseEntity<Void> update(@PathVariable("id") int id, @RequestBody Board board){
//		System.out.println(board);
//		board.setId(id);
//		boardService.modifyBoard(board);
//		return new ResponseEntity<Void>(HttpStatus.OK); //
//	}
//	
//	
//	//파일업로드
//	@PostMapping("/upload")
//	public ResponseEntity<Void> fileUpload(@RequestParam("file") MultipartFile file, @ModelAttribute Board board){
//		
//		boardService.fileUpload(file, board);
//		
//		
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
	
	
	
	
	
	
}
