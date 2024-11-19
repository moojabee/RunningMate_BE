package com.lswr.demo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import com.lswr.demo.model.dto.ChatMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatMessageController {
	
    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat/message") // 클라이언트가 "/pub/chat/message"로 발행
    public void sendMessage(@Payload ChatMessage message) {
        if(message.getMessageType().equals("ENTER")) {
        	message.setContent(message.getUserId()+"님이 입장하셨습니다.");
        }
    	messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}
