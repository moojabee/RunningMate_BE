package com.lswr.demo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lswr.demo.model.dao.CommentDao;
import com.lswr.demo.model.dto.Comment;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentDao commentDao;
	
	@Override
    public List<Comment> getCommentsByBoardId(Long boardId) {
        return commentDao.getCommentsByBoardId(boardId);
    }

    @Override
    public Comment createComment(Comment comment) {
        commentDao.insertComment(comment);
        return comment; // 생성된 댓글 반환
    }

    @Override
    public void deleteComment(Long commentId, Long userId) {
        commentDao.deleteComment(commentId, userId);
    }


}
