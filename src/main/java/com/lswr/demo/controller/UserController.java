package com.lswr.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lswr.demo.model.dto.User;
import com.lswr.demo.model.service.UserService;

@RestController
@RequestMapping("")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public ResponseEntity<?> getUserList() {
		List<User> list = userService.getUserList();
		return ResponseEntity.ok(list);
	}
}
