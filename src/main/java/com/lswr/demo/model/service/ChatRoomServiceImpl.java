package com.lswr.demo.model.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.lswr.demo.model.dao.ChatDao;
import com.lswr.demo.model.dao.UserDao;
import com.lswr.demo.model.dto.ChatRoom;
import com.lswr.demo.model.dto.ChatRoomCreateDto;
import com.lswr.demo.model.dto.Party;
import com.lswr.demo.model.dto.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

	private final ChatDao chatDao;
	private final UserDao userDao;
	
	@Override
	public List<ChatRoom> getChatRoomList(Long userId) {
		List<ChatRoom> list = chatDao.selectChatRoomList(userId);
		return list;
	}

	@Override
	public List<ChatRoom> getOpenChatRoomList(Long userId) {
		List<ChatRoom> list = chatDao.selectOpenChatRoomList(userId);
		return list;
	}

	@Override
	public boolean createChatRoom(Long id, ChatRoomCreateDto chatRoomCreateDto) {
		log.info("createChat : "+chatRoomCreateDto.toString());
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.setRoomName(chatRoomCreateDto.getRoomName());
		chatRoom.setRoomId(UUID.randomUUID().toString());
		chatRoom.setRoomType(chatRoomCreateDto.getRoomType());
		chatRoom.setCreatedDate(LocalDateTime.now());
		int res = chatDao.createChatRoom(chatRoom);
	
		if(res!=1) return res!=1 ;
	
		Party party = new Party();
		party.setRoomId(chatRoom.getRoomId());
		party.setUserId(id);
		boolean invitedSuccess = joinChatRoom(party);
	
		for (String userEmail : chatRoomCreateDto.getUserList()) {
			party  = new Party();
			party.setRoomId(chatRoom.getRoomId());
			party.setUserId(userDao.selectUserByEmail(userEmail).getUserId());
			invitedSuccess = joinChatRoom(party);
		}
		
		return invitedSuccess;
	}

	@Override
	public boolean joinChatRoom(Party party) {
		log.info("joinChat : "+party.toString());
		party.setPartiedDate(LocalDateTime.now());
		int res = chatDao.enterChatRoom(party);
		return res==1;
	}
}
