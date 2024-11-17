package com.lswr.demo.model.dao;

import java.util.List;

import com.lswr.demo.model.dto.Comment;

public interface CommentDao {
	
	// 댓글 리스트 조회
    List<Comment> getCommentsByBoardId(Long boardId);

    // 댓글 작성
    void insertComment(Comment comment);

    // 댓글 삭제
    void deleteComment(Long commentId, Long userId);
	
}
