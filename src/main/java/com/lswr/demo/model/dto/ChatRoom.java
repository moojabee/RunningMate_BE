package com.lswr.demo.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
	enum RoomType {
	    OPEN, PRIVATE;
	}
	
	private String roomId;
    private String roomName;
    private RoomType roomType;
    private LocalDateTime createdDate;
}

