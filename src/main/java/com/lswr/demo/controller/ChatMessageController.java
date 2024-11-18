package com.lswr.demo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import com.lswr.demo.model.dto.ChatMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatMessageController {
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/message") // 클라이언트가 "/pub/chat/message"로 발행
    public void sendMessage(@Payload ChatMessage message) {
        log.info("Message received: {}", message);

        // 메시지를 /sub/chat/room/{roomId} 경로로 전송
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}
