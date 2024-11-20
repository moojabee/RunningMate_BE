package com.lswr.demo.controller;

import java.time.LocalDateTime;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import com.lswr.demo.model.dto.ChatMessage;
import com.lswr.demo.model.dto.Party;
import com.lswr.demo.model.service.ChatRoomService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatMessageController {

	private final ChatRoomService chatRoomService;
	private final SimpMessageSendingOperations messagingTemplate;

	@MessageMapping("/chat/message") // 클라이언트가 "/pub/chat/message"로 발행
	public void sendMessage(@Payload ChatMessage message) {
		message.setSendedDate(LocalDateTime.now());
		
		if (message.getMessageType().equals("ENTER")) {
			Party party = new Party();
			party.setRoomId(message.getRoomId());
			party.setUserId(message.getMessageId());
			if (!chatRoomService.hasJoined(party)) {
				message.setContent(message.getUserId() + "님이 입장하셨습니다.");
				messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
				chatRoomService.sendMessage(message);
			}
		} 
		else {
			chatRoomService.sendMessage(message);
			messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
		}
	}
}
