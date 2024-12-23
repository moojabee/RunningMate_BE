package com.lswr.demo.model.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.lswr.demo.model.dao.ChatDao;
import com.lswr.demo.model.dao.UserDao;
import com.lswr.demo.model.dto.ChatMessage;
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
		List<ChatRoom> list = chatDao.selectRecentChatRoomList(userId);
		for(ChatRoom c: list) {
			c.setUserList(chatDao.getUsersByRoomId(c.getRoomId()));
		}
		return list;
	}

	@Override
	public List<ChatRoom> getOpenChatRoomList(Long userId) {
		List<ChatRoom> list = chatDao.selectOpenChatRoomList(userId);
		return list;
	}

	@Override
	public String createChatRoom(Long id, ChatRoomCreateDto chatRoomCreateDto) {
		log.info("createChat : "+chatRoomCreateDto.toString());
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.setRoomName(chatRoomCreateDto.getRoomName());
		chatRoom.setRoomId(UUID.randomUUID().toString());
		chatRoom.setRoomType(chatRoomCreateDto.getRoomType());
		chatRoom.setCreatedDate(LocalDateTime.now());
		int res = chatDao.createChatRoom(chatRoom);
	
		if(res!=1) return null;
	
		Party party = new Party();
		party.setRoomId(chatRoom.getRoomId());
		party.setUserId(id);
		boolean invitedSuccess = joinChatRoom(party);
	
		for (String nickname : chatRoomCreateDto.getUserList()) {
			log.info("nickname : " + nickname);
			party  = new Party();
			party.setRoomId(chatRoom.getRoomId());
			party.setUserId(userDao.selectUserByNickname(nickname).getUserId());
			invitedSuccess = joinChatRoom(party);
		}
		
		if(invitedSuccess) return chatRoom.getRoomId();
		return null;
	}

	@Override
	public boolean joinChatRoom(Party party) {
		log.info("joinChat : "+party.toString());
		party.setPartiedDate(LocalDateTime.now());
		int res = chatDao.enterChatRoom(party);
		return res==1;
	}

	@Override
	public ChatRoom findChatRoomById(String id) {
		return chatDao.selectChatRoom(id);
	}

	@Override
	public boolean leaveChatRoom(Party party) {
		log.info("leaveChat : " + party.toString());
		int res = chatDao.deleteUserInChatRoom(party);
		return res==1;
	}

	@Override
	public List<ChatMessage> loadChatMessage(Party party) {
		List<ChatMessage> list = chatDao.selectAllChatting(party);
		return list;
	}

	@Override
	public Party getParty(Party party) {
		Party findParty = chatDao.findParty(party);
		return findParty;
	}

	@Override
	public boolean hasJoined(Party party) {
		return chatDao.findChatMessage(party) > 0;
	}

	@Override
	public boolean sendMessage(ChatMessage chatMessage) {
		return chatDao.insertChatMessage(chatMessage)==1;
	}

	@Override
	public List<ChatRoom> getOpenChatRoomListByKeyword(String keyword) {
		return chatDao.findOpenChatByKeyword(keyword);
	}

	@Override
	public List<User> getUserByRoomId(String roomId) {
		return chatDao.getUsersByRoomId(roomId);
	}

	@Override
	public ChatRoom getChatRoomInfo(String roomId) {
		return chatDao.getChatRoomInfo(roomId);
	}
}
