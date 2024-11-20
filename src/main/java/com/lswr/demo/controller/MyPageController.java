package com.lswr.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> getUserBoard(@PathVariable Long userId) {
        MyPage userBoard = myPageService.getUserBoard(userId);
        return new ResponseEntity<>(userBoard, HttpStatus.OK);
    }

    // 유저 런닝 기록 조회
    @GetMapping("/userRun/{userId}")
    public ResponseEntity<?> getUserRun(@PathVariable Long userId) {
    	List<Run> runs = myPageService.getUserRun(userId);
        return new ResponseEntity<>(runs, HttpStatus.OK);
    }


	
}
