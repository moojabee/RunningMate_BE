package com.lswr.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
	enum ChatRoomType {
	    OPEN, PRIVATE;
	}
	
	private String roomId;
    private String roomName;
    private ChatRoomType chatRoomtype;
}

