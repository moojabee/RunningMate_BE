package com.lswr.demo.model.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.lswr.demo.model.dao.ChatDao;
import com.lswr.demo.model.dto.ChatRoom;
import com.lswr.demo.model.dto.Party;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

	private final ChatDao chatDao;
	
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
	public boolean createChatRoom(ChatRoom chatRoom) {
		chatRoom.setRoomId(UUID.randomUUID().toString());
		chatRoom.setCreatedDate(LocalDateTime.now());
		int res = chatDao.createChatRoom(chatRoom);
		return res==1;
	}

	@Override
	public boolean joinChatRoom(Party party) {
		party.setPartiedDate(LocalDateTime.now());
		int res = chatDao.enterChatRoom(party);
		return res==1;
	}
}
