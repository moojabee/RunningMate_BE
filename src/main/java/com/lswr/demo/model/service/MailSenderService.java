package com.lswr.demo.model.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Random;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.lswr.demo.model.dto.EmailDto;
import com.lswr.demo.model.dto.User;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailSenderService {

	private final JavaMailSender mailSender;
	private final UserService userService;

	public void sendEmail(String email) throws MessagingException {
	    MimeMessagePreparator preparator = mimeMessage -> {
	        
	        // HTML 템플릿 로드
	        String htmlContent = loadHtmlTemplate("static/email-template.html");
	        
	        // 비밀번호 변경 후 템플릿에 값 치환
	        String newPassword = changePassword(email);
	        String userName = userService.getUser(email).getName() + "님 안녕하세요";

	        // {{number}}와 {{name}} 치환
	        htmlContent = htmlContent.replace("{{number}}", newPassword)
	                                 .replace("{{name}}", userName);

	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
	        
	        // 받는 사람 이메일
	        helper.setTo(email);
	        // 이메일 제목
	        helper.setSubject("비밀번호 변경 알림");
	        // 메일 내용
	        helper.setText(htmlContent, true);  // HTML 형식으로 설정
	    };

	    try {
	        log.info(email);
	        this.mailSender.send(preparator);
	    } catch (MailException e) {
	        log.info("ERROR: Message 전송 실패");
	        throw e;
	    }
	}	
	
	private String changePassword(String email) {
		User user = userService.getUser(email);
		log.info("changePassword : " + user);
		String newPassword = randomString();
		user.setPassword(newPassword);
		userService.updateUser(user);
		return newPassword;
	}

	private String randomString() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder password = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < 10; i++) {
			int index = random.nextInt(chars.length());
			password.append(chars.charAt(index));
		}

		return password.toString();
	}
	
	public String loadHtmlTemplate(String filePath) throws IOException	 {
	    ClassPathResource resource = new ClassPathResource(filePath);
	    byte[] bytes = Files.readAllBytes(resource.getFile().toPath());
	    return new String(bytes, StandardCharsets.UTF_8);
	}
}
