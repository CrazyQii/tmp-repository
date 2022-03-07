package com.felix.forum.web;

import com.felix.forum.po.Article;
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
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
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

    /**
     * 显示评论
     * @param articleId
     * @param model
     * @return
     */
    @GetMapping("/comments/{articleId}")
    public String comments(@PathVariable Long articleId, Model model) {
        Article article=articleService.getAndConvert(articleId);
        model.addAttribute("article", article);
        model.addAttribute("comments", commentService.listCommentByArticleId(articleId));
        return "login/article :: commentList";
    }

    @GetMapping("/comments/admin/{articleId}")
    public String comments2(@PathVariable Long articleId, Model model) {
        Article article=articleService.getAndConvert(articleId);
        model.addAttribute("article", article);
        model.addAttribute("comments", commentService.listCommentByArticleId(articleId));
        return "backSystem/article :: commentList";
    }
    /**
     * 发布评论
     * @param comment
     * @param session
     * @return
     */
    @PostMapping ("/comments")
    public String post(Comment comment, HttpSession session,Model model) {
        //获取文章id
        Long articleId = comment.getArticle().getId();
        comment.setArticle(articleService.getArticle(articleId));
        User user = (User) session.getAttribute("user");
        //文章作者类型转化
        int userId=Integer.valueOf(comment.getArticle().getUserId()).intValue();
        if (user != null && user.getId().equals(userId)) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
            comment.setNickname(user.getNickname());
            comment.setEmail(user.getEmail());
        } else {
            comment.setAvatar(avatar);
            comment.setAdminComment(false);
            comment.setNickname(user.getNickname());
            comment.setEmail(user.getEmail());
        }
        comment.setArticleId(articleId);
        commentService.saveComment(comment);
        if (user.getRole().equals("admin")){
            return "redirect:/comments/admin" + articleId;
        }
        return "redirect:/comments/" + articleId;
    }

    @GetMapping("/comment/delete/{id}")
    public String deleteComment(@PathVariable int id){
        int articleId=commentService.queryById(id);
        commentService.delete(id);
        return "redirect:/article/"+articleId;
    }

    @GetMapping("/comment/admin/delete/{id}")
    public String adminComment(@PathVariable int id){
        int articleId=commentService.queryById(id);
        commentService.delete(id);
        return "redirect:/article/admin/"+articleId;
    }
}
