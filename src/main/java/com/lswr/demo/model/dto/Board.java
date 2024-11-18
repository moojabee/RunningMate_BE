package com.lswr.demo.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Board {
	private Long userId;
	private String nickname;
	private String userDist;
	private String userPace;
	private String userImg;
	
	private Long boardId;
    private String content;
    private LocalDateTime postedDate;
    
    private List<BoardImg> boardImg;
    private List<Comment> comment;
    private List<Like> like;
    
    private Integer likeCheck;
}
