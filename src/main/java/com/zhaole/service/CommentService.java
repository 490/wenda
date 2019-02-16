package com.zhaole.service;

import com.zhaole.model.Comment;

import java.util.List;

public interface CommentService {
    public List<Comment> getCommentsByEntity(int entityId, int entityType);
    public int addComment(Comment comment);
    public int getCommentCount(int entityId, int entityType);
    public boolean deleteComment(int commentId);
    public Comment getCommentById(int id);
    public int getUserCommentCount(int userId);
}
