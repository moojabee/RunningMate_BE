package com.lswr.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lswr.demo.model.dto.ChatRoom;
import com.lswr.demo.model.dto.Party;
import com.lswr.demo.model.service.ChatRoomService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("chat")
@RequiredArgsConstructor
public class ChatRoomController {

	private final ChatRoomService chatRoomSerivce;
	
    // 1. 유저가 속한 채팅방 목록 조회
    @GetMapping("/room-list")
    public ResponseEntity<?> getChatRoomsByUserId(@RequestAttribute("userId") String userId) {
    	Long id = Long.parseLong(userId);
        List<ChatRoom> list = chatRoomSerivce.getChatRoomList(id);
        return new ResponseEntity<List<ChatRoom>>(list,HttpStatus.OK);
    }
    
    // 2. 오픈 채팅방 목록 조회
    @GetMapping("/room-list/open")
    public ResponseEntity<?> getOpenChatRoom(@RequestAttribute("userId")String userId){
    	Long id = Long.parseLong(userId);
    	List<ChatRoom> list = chatRoomSerivce.getOpenChatRoomList(id);
    	return new ResponseEntity<List<ChatRoom>>(list,HttpStatus.OK);
    }
    
    // 3. 채팅방 생성
    @PostMapping("/new-room")
    public ResponseEntity<?> createChatRoom(@RequestAttribute("userId") String userId, @RequestBody ChatRoom chatRoom){
    	Long id = Long.parseLong(userId);
    	boolean res = chatRoomSerivce.createChatRoom(chatRoom);
    	
    	// 생성 실패
    	if(!res) return ResponseEntity.badRequest().build();
    	
    	// 생성 성공
    	Party party = new Party();
    	party.setRoomId(chatRoom.getRoomId());
    	party.setUserId(id);
    	res = chatRoomSerivce.joinChatRoom(party);
    	
    	// 참여 성공
    	if(res) return ResponseEntity.ok(res);
    	
    	// 참여 실패
    	return ResponseEntity.badRequest().build();
    }
    
    // 4. 채팅방 참여
    @PostMapping("/join-room")
    public ResponseEntity<?> joinChatRoom(@RequestBody Party party){
    	boolean res = chatRoomSerivce.joinChatRoom(party);
    	if(res) return ResponseEntity.ok(res);
    	return ResponseEntity.badRequest().build();
    }
}
