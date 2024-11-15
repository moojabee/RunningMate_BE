package com.lswr.demo.model.dao;

import java.util.List;

import com.lswr.demo.model.dto.ChatRoom;

public interface ChatDao {
	
	// 유저가 속한 채팅방리스트
	public List<ChatRoom> selectChatRoomList(Long userId);
	
	// 오픈 채팅방 리스트
	public List<ChatRoom> selectOpenChatRoomList();
	
	// 채팅방 생성
	public int createChatRoom(ChatRoom chatRoom);
}
