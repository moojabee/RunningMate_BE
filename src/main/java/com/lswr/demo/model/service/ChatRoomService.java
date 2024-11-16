package com.lswr.demo.model.service;

import java.util.List;

import com.lswr.demo.model.dto.ChatRoom;
import com.lswr.demo.model.dto.Party;

public interface ChatRoomService {

	public List<ChatRoom> getChatRoomList(Long userId);
	
	public List<ChatRoom> getOpenChatRoomList(Long userId);

	public boolean createChatRoom(ChatRoom chatRoom);

	public boolean joinChatRoom(Party party);
}
