package com.lswr.demo.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Follow {
	private Long userId;
    private String nickname;
    private String userDist;
    private String userPace;
    private int status;
}