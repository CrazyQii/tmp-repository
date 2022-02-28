package com.felix.forum.web;

import com.felix.forum.po.Comment;
import com.felix.forum.po.User;
import com.felix.forum.service.ArticleService;
import com.felix.forum.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @author Administrator
 * @date 2022/02/23 09:32
 **/
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{articleId}")
    public String comments(@PathVariable Long articleId, Model model) {
        model.addAttribute("comments", commentService.listCommentByArticleId(articleId));
        return "article :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        Long articleId = comment.getArticle().getId();
        comment.setArticle(articleService.getArticle(articleId));
        User user = (User) session.getAttribute("user");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
//            comment.setNickname(user.getNickname());
        } else {
            comment.setAvatar(avatar);
        }
        comment.setArticleId(articleId);
        // comment.setAvatar(avatar);
        commentService.saveComment(comment);
        return "redirect:/comments/" + articleId;
    }
}
