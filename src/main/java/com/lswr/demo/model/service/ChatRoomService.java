package com.lswr.demo.model.service;

import java.util.List;

import com.lswr.demo.model.dto.ChatMessage;
import com.lswr.demo.model.dto.ChatRoom;
import com.lswr.demo.model.dto.ChatRoomCreateDto;
import com.lswr.demo.model.dto.Party;

public interface ChatRoomService {

	public List<ChatRoom> getChatRoomList(Long userId);
	
	public List<ChatRoom> getOpenChatRoomList(Long userId);

	public boolean createChatRoom(Long id, ChatRoomCreateDto chatRoomCreateDto);

	public boolean joinChatRoom(Party party);
	
	public ChatRoom findChatRoomById(String id);
	
	public boolean leaveChatRoom(Party party);
	
	public List<ChatMessage> loadChatMessage(Party party);
	
	public Party getParty(Party party);
	
	public boolean hasJoined(Party party);
	
	public boolean sendMessage(ChatMessage chatMessage);
}
