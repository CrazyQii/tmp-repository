package com.felix.forum.dao;

import com.felix.forum.po.Comment;
import com.felix.forum.po.Tag;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;


@Mapper
public interface CommentRepository{
    List<Comment> findByArticleIdAndParentCommentNull(Map<String, Object> map);


    Comment get(Map<String, Object> map);


    int save(Comment content);

    int delete(int commentId);

    int queryById(int commentId);

    List<Comment> queryByParentId(Long parentCommentId);

    int count(Long parentCommentId);
}
