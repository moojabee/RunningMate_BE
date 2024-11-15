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
public class ChatMessage {
	public enum MessageType{
		ENTER,TALK,QUIT
	}
	private Long messageId;
	private String roomId;
	private Long userId; 	// userId
	private LocalDateTime sendedDate;
	private String content;
	private MessageType messageType; 
}