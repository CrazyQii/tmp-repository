package com.felix.forum.service;

import com.felix.forum.dao.CommentRepository;
import com.felix.forum.po.Article;
import com.felix.forum.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @date 2022/02/23 09:50
 **/
@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByArticleId(Long articleId) {
        Sort sort = Sort.by("createTime");
        Map<String, Object> map = new HashMap<>();
        map.put("articleId", articleId);
        List<Comment> comments = commentRepository.findByArticleIdAndParentCommentNull(map);
        return eachComment(comments);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {
            Map<String,Object> map = new HashMap<>();
            map.put("id", parentCommentId);
            comment.setParentComment(commentRepository.get(map));
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        commentRepository.save(comment);
        return comment;
    }

    @Override
    public int delete(int commentId) {
        return commentRepository.delete(commentId);
    }

    @Override
    public int queryById(int commentId) {
        return commentRepository.queryById(commentId);
    }

    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     **/
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment comment1 = new Comment();
            BeanUtils.copyProperties(comment, comment1);
            commentsView.add(comment1);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     * 循环每个顶级的评论节点
     * @param comments root根节点，article不为空的对象集合
     * @return
     **/
    private void combineChildren(List<Comment> comments) {
        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for (Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     **/
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合区
        if (comment.getReplyComments().size()>0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0) {
                    recursively(reply);
                }
            }
        }
    }

}
