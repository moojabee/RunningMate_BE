package com.lswr.demo.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Like {
	private Long likeId;
	private Long userId;
	private Long boardId;
}
