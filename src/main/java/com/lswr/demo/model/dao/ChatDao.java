package com.lswr.demo.model.dao;

import java.util.List;

import com.lswr.demo.model.dto.ChatMessage;
import com.lswr.demo.model.dto.ChatRoom;
import com.lswr.demo.model.dto.Party;

public interface ChatDao {
	
	// 유저가 속한 채팅방리스트
	public List<ChatRoom> selectChatRoomList(Long userId);
	
	// 오픈 채팅방 리스트
	public List<ChatRoom> selectOpenChatRoomList(Long userId);
	
	// 채팅방 생성
	public int createChatRoom(ChatRoom chatRoom);
	
	// 채팅방 참여
	public int enterChatRoom(Party party);
	
	// 채팅방 아이디로 채팅방 찾기
	public ChatRoom selectChatRoom(String roomId);
	
	// 채팅방 나가기
	public int deleteUserInChatRoom(Party party);
	
	// 채팅 불러오기
	public List<ChatMessage> selectAllChatting(Party party);
}
