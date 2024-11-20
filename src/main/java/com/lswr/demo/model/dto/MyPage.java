package com.lswr.demo.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPage {
	private Long userId;
	private String nickname;
	private String userDist;
	private String userPace;
	private String userImg;
	private Long boardNum;
	private Long followerNum;
	private Long followingNum;
	
    private List<Board> boardList;
    
    private List<Run> runList;
    
}
