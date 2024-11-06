package com.lswr.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lswr.demo.model.dto.EmailDto;
import com.lswr.demo.model.dto.LoginDto;
import com.lswr.demo.model.dto.User;
import com.lswr.demo.model.service.MailSenderService;
import com.lswr.demo.model.service.UserService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final MailSenderService mailService;
	
	@GetMapping("/")
	public ResponseEntity<?> getUserList() {
		List<User> list = userService.getUserList();
		return ResponseEntity.ok(list);
	}
	
	@PostMapping("/send")
	public ResponseEntity<String> sendEmail(@RequestBody EmailDto email) throws MessagingException{
		log.info("이메일 요청을 보냄");
		mailService.sendEmail(email);
		return new ResponseEntity<String>("GOOD JOB",HttpStatus.OK);
	}
	
	@PostMapping("/regist")
	public ResponseEntity<String> registUser(@RequestBody User user){
		if(userService.isEmailDuplicated(user.getEmail())) {
			// log.info("이메일 중복");
			return new ResponseEntity<String>("Duplicate Email",HttpStatus.BAD_REQUEST);
		}
		if(userService.isNicknameDuplicated(user.getNickname())) {
			// log.info("닉네임 중복");
			return new ResponseEntity<String>("Duplicate Nickname",HttpStatus.BAD_REQUEST);
		}
		// log.info("가입 완료");
		userService.registUser(user);
		return new ResponseEntity<String>("GOOD JOB",HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody LoginDto loginDto){
		if(userService.loginUser(loginDto)) {
			return new ResponseEntity<String>("GOOD JOB",HttpStatus.OK);
		}
		return new ResponseEntity<String>("Not User",HttpStatus.BAD_REQUEST);
	}
}
