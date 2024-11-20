package com.lswr.demo.model.dto;


import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
	private Long messageId;
	private String roomId;
	private Long userId; 	// userId
	private LocalDateTime sendedDate;
	private String content;
	private String messageType; 
}