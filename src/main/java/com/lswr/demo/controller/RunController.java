package com.lswr.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lswr.demo.model.dto.RunResultDto;
import com.lswr.demo.model.service.RunService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("run")
@RequiredArgsConstructor
public class RunController {

	private final RunService runService;
	
	// 1. run 기록 저장
	@PostMapping("/record")
	public ResponseEntity<?> saveRunRecord(@RequestAttribute("userId") String userId, @RequestBody RunResultDto runResult){
		Long id = Long.parseLong(userId);
		runResult.setUserId(id);
		boolean res = runService.addRunRecord(runResult);
		if(res) ResponseEntity.ok("Good");
		return ResponseEntity.badRequest().build();
	}
}
