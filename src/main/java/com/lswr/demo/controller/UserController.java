package com.lswr.demo.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lswr.demo.model.dto.EmailDto;
import com.lswr.demo.model.dto.LoginDto;
import com.lswr.demo.model.dto.User;
import com.lswr.demo.model.service.MailSenderService;
import com.lswr.demo.model.service.TokenService;
import com.lswr.demo.model.service.UserService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("userAuth")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final MailSenderService mailService;
	private final TokenService tokenService;
	
	@GetMapping("/")
	public ResponseEntity<?> getUserList() {
		List<User> list = userService.getUserList();
		return ResponseEntity.ok(list);
	}
	
	@PostMapping("/find-password")
	public ResponseEntity<String> sendEmail(@RequestBody EmailDto emailDto) throws MessagingException{
		String email = emailDto.getEmail();
		mailService.sendEmail(email);
		return new ResponseEntity<String>("GOOD JOB",HttpStatus.OK);
	}
	
	@PostMapping("/regist")
	public ResponseEntity<String> registUser(@RequestBody User user){
		log.info("loginUser : "+user.toString());
		userService.registUser(user);
		return new ResponseEntity<String>("GOOD JOB",HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<User> loginUser(@RequestBody LoginDto loginDto){
		// 로그인 성공
		if(userService.loginUser(loginDto)) {
			User user = userService.getUser(loginDto.getEmail());
            String token = tokenService.createToken(String.valueOf(user.getUserId())); // JWT 생성
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token); // Authorization 헤더에 JWT 토큰 추가
            user.setPassword("");
            return new ResponseEntity<>(user,headers,HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/email-check")
	public ResponseEntity<?> emailCheck(@RequestParam("email") String email){
		boolean res = userService.isValidEmail(email);
		return ResponseEntity.ok(res);
	}

	@GetMapping("/nickname-check")
	public ResponseEntity<?> nicknameCheck(@RequestParam("nickname") String nickname){
		boolean res = userService.isValidEmail(nickname);
		return ResponseEntity.ok(res);
	}
}
