package com.lswr.demo.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatRoom {
	private String roomId;
    private String roomName;
    private String roomType;
    private LocalDateTime createdDate;
    private Long lastChatId;
    private ChatMessage lastChat;
    private List<User> userList;
}

