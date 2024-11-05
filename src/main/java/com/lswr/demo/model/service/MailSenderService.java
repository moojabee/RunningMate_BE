package com.lswr.demo.model.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.lswr.demo.model.dto.EmailDto;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final JavaMailSender mailSender;
    
    public void sendEmail(EmailDto email) throws MessagingException {

        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            
            // 받는 사람 이메일 
            helper.setTo(email.getEmail());
            // 이메일 제목
            helper.setSubject("MimeMessageHelper Subject");
            // 메일 내용
            helper.setText("this is MimeMessageHelper test");

            // 첨부 파일 추가 코드가 필요하면 여기 추가
        };

        try {
            this.mailSender.send(preparator);
        } catch (MailException e) {
            log.info("ERROR: Message 전송 실패");
            throw e;
        }
    }
}

