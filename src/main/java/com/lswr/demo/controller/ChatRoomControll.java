package com.lswr.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lswr.demo.model.dto.ChatRoom;
import com.lswr.demo.model.service.ChatRoomService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("chat")
@RequiredArgsConstructor
public class ChatRoomControll {

	private final ChatRoomService chatRoomSerivce;
	
    // 1. 유저가 속한 채팅방 목록 조회
    @GetMapping("/room-list")
    public ResponseEntity<?> getChatRoomsByUserId(@RequestAttribute("userId") String userId) {
    	long id = Long.parseLong(userId);
        List<ChatRoom> list = chatRoomSerivce.getChatRoomList(id);
        return new ResponseEntity<List<ChatRoom>>(list,HttpStatus.OK);
    }
    
    // 2. 오픈 채팅방 목록 조회
    @GetMapping("/room-list/open")
    public ResponseEntity<?> getOpenChatRoom(){
    	List<ChatRoom> list = chatRoomSerivce.getOpenChatRoomList();
    	return new ResponseEntity<List<ChatRoom>>(list,HttpStatus.OK);
    }
    
    // 3. 채팅방 생성
    @PostMapping("/new-room")
    public ResponseEntity<?> createChatRoom(@RequestBody ChatRoom chatRoom){
    	chatRoom.setRoomId(UUID.randomUUID().toString());
    	boolean res = chatRoomSerivce.createChatRoom(chatRoom);
    	if(res) return ResponseEntity.ok(res);
    	return ResponseEntity.badRequest().build();
    }
}
