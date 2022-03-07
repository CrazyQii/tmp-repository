package com.felix.forum.web.admin;

import com.felix.forum.po.User;
import com.felix.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Administrator
 * @date 2022/02/16 15:45
 **/
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

    /**
     * 登录
     * @param username
     * @param password
     * @param session
     * @param attributes
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);
        if (user == null){
            session.removeAttribute("user");
            attributes.addFlashAttribute("message", "用户名和密码错误！");
            return "redirect:/admin";
        }
        if (user.getRole().equals("user") && user !=null) {
            session.setAttribute("user", user);
            return "redirect:/";
        }else {
            session.setAttribute("user", user);
            return "backSystem/_fragments";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

    /**
     * 跳转个人中心
     * @param request
     * @param response
     * @return
     */
    @RequestMapping ("/person")
    public String personCentral(HttpServletRequest request, HttpServletResponse response) {
        return "/admin/index";
    }
}
