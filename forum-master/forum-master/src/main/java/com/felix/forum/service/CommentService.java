package com.felix.forum.service;

import com.felix.forum.po.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByArticleId(Long articleId);

    Comment saveComment(Comment comment);


    int delete(int commentId);

    int queryById(int commentId);

    List<Comment> queryByParentId(Long parentComentId);

    int count(Long parentCommentId);
}
