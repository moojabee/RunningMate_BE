package com.lswr.demo.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lswr.demo.model.dao.ChatDao;
import com.lswr.demo.model.dto.ChatRoom;

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
	public List<ChatRoom> getOpenChatRoomList() {
		List<ChatRoom> list = chatDao.selectOpenChatRoomList();
		return list;
	}

	@Override
	public boolean createChatRoom(ChatRoom chatRoom) {
		int res = chatDao.createChatRoom(chatRoom);
		return res==1;
	}

}
