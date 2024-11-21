package com.lswr.demo.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardImg {
	private Long imgId;
	private Long boardId;
    private String boardUrl;
}
