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
public class ChatRoomCreateDto {
	private String roomId;
    private String roomName;
    private String roomType;
    private LocalDateTime createdDate;
    private List<String> userList;
}

