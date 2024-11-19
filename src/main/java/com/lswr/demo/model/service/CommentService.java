package com.lswr.demo.model.service;

import java.util.List;

import com.lswr.demo.model.dto.Comment;

public interface CommentService {
	
	// 댓글 리스트 조회
    List<Comment> getCommentsByBoardId(Long boardId);

    // 댓글 작성
    Comment createComment(Comment comment);

    // 댓글 삭제
    void deleteComment(Long commentId, Long userId);
	
}
