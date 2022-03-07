package com.felix.forum.web.admin;

import com.felix.forum.po.User;
import com.felix.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 注册功能
 */
@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String registerPage(){
        return "admin/register";
    }

    @RequestMapping (value = "/redirect",method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpSession session, RedirectAttributes attributes) {
        //校验是否存在用户
        User user=userService.getUsername(request.getParameter("username"));
        if (user!=null){
            attributes.addFlashAttribute("message", "该用户已存在！");
            return "redirect:/register";
        }
        if (!request.getParameter("password1").equals(request.getParameter("password2"))){
            attributes.addFlashAttribute("message", "两次密码不一致！");
            return "redirect:/register";
        }
        //创建新用户
        User user1=new User();
        user1.setUsername(request.getParameter("username"));
        user1.setPassword(request.getParameter("password1"));
        user1.setNickname(request.getParameter("nickname"));
        user1.setEmail(request.getParameter("email"));
        user1.setRole("user");
        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        user1.setCreateTime(format.format(date));
        user1.setUpdateTime(format.format(date));
        userService.insertUser(user1);
        return "redirect:/admin";
    }

}
