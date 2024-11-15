package com.lswr.demo.model.service;

import java.util.List;

import com.lswr.demo.model.dto.ChatRoom;

public interface ChatRoomService {

	public List<ChatRoom> getChatRoomList(Long userId);
	
	public List<ChatRoom> getOpenChatRoomList();

	public boolean createChatRoom(ChatRoom chatRoom);
}
