package com.lswr.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lswr.demo.model.dto.ChatMessage;
import com.lswr.demo.model.dto.ChatRoom;
import com.lswr.demo.model.dto.ChatRoomCreateDto;
import com.lswr.demo.model.dto.Party;
import com.lswr.demo.model.service.ChatRoomService;

import jakarta.mail.Part;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("chat")
@RequiredArgsConstructor
public class ChatRoomController {

	private final SimpMessageSendingOperations messagingTemplate;
	private final ChatRoomService chatRoomSerivce;
	
	// 0. 채팅방 찾기
    @GetMapping("/room/{roomId}")
    public ResponseEntity<ChatRoom> getRoom(@PathVariable("roomId") String roomId) {
        ChatRoom room = chatRoomSerivce.findChatRoomById(roomId);
        return ResponseEntity.ok(room);
    }
	
    // 1. 유저가 속한 채팅방 목록 조회
    @GetMapping("/room-list")
    public ResponseEntity<?> getChatRoomsByUserId(@RequestAttribute("userId") String userId) {
    	Long id = Long.parseLong(userId);
        List<ChatRoom> list = chatRoomSerivce.getChatRoomList(id);
        return new ResponseEntity<List<ChatRoom>>(list,HttpStatus.OK);
    }
    
    // 2. 오픈 채팅방 목록 조회
    @GetMapping("/room-list/open")
    public ResponseEntity<?> getOpenChatRoom(@RequestAttribute("userId") String userId){
    	Long id = Long.parseLong(userId);
    	List<ChatRoom> list = chatRoomSerivce.getOpenChatRoomList(id);
    	return new ResponseEntity<List<ChatRoom>>(list,HttpStatus.OK);
    }
    
    // 3. 채팅방 생성
    @PostMapping("/new-room")
    public ResponseEntity<?> createChatRoom(@RequestAttribute("userId") String userId, @RequestBody ChatRoomCreateDto chatRoomCreateDto){
    	
    	log.info("log: "+ chatRoomCreateDto.getUserList().toString());
    	Long id = Long.parseLong(userId);
    	boolean res = chatRoomSerivce.createChatRoom(id, chatRoomCreateDto);
    	
    	// 참여 성공
    	if(res) return ResponseEntity.ok(res);
    	
    	// 참여 실패
    	return ResponseEntity.badRequest().build();
    }
    
    // 4. 채팅방 참여
    @PostMapping("/join-room")
    public ResponseEntity<?> joinChatRoom(@RequestAttribute("userId") String userId, @RequestBody Party party){
    	Long id = Long.parseLong(userId);
    	party.setUserId(id);
    	log.info(party.getRoomId());
    	boolean res = chatRoomSerivce.joinChatRoom(party);
    	if(res) return ResponseEntity.ok(res);
    	return ResponseEntity.badRequest().build();
    }
    
    // 5. 채팅방 나가기
    @DeleteMapping("/leave-room")
    public ResponseEntity<?> leaveChatRoom(@RequestAttribute("userId") String userId, @RequestBody Party party){
    	Long id = Long.parseLong(userId);
    	party.setUserId(id);
    	boolean res = chatRoomSerivce.leaveChatRoom(party);
    	if(res) return ResponseEntity.ok(res);
    	return ResponseEntity.badRequest().build();
    }
    
    // 6. 채팅방 데이터 저장
//    @PostMapping("/add-chatting")
//    public ResponseEntity<?> addChatting(@RequestAttribute("userId") String userId, @RequestBody ChatMessage chatting){
//    	Long id = Long.parseLong(userId);
//    	chatting.setUserId(id);
//    	
//    }
    
    // 7. 채팅방 데이터 불러오기
    @GetMapping("/load-chatting/{roomId}")
    public ResponseEntity<?> loadChat(@RequestAttribute("userId") String userId, @PathVariable("roomId") String roomId){
    	log.info("호출됨");
    	Long id = Long.parseLong(userId);
    	Party party = new Party();
    	party.setUserId(id);
    	party.setRoomId(roomId);
    	List<ChatMessage> list = chatRoomSerivce.loadChatMessage(party);
    	log.info("요청 : "+list.toString());
    	return ResponseEntity.ok(list);
    }
}
