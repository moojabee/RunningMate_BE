package com.lswr.demo.model.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comment {
	private Long commentId;
	private Long boardId;
	private Long userId;
    private String content;
    private LocalDateTime postedDate;
}
