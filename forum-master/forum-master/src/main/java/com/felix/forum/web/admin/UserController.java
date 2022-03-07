package com.felix.forum.web.admin;

import com.felix.forum.po.User;
import com.felix.forum.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: UserController
 * @description:
 * @author: hanLinQi
 * @create: 2022-03-07 14:42
 **/
@Controller
@RequestMapping("/admin")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public String article(@PathVariable String id, Model model) {
        Map<String, Object> map = new HashMap<>();
        User user = userService.getUserInfo(Long.parseLong(id));
        if (user.getHobby() != null) {
            map.put("hobby", Arrays.asList(user.getHobby().split(",")));
        }
        if (user.getTech() != null) {
            map.put("tech", Arrays.asList(user.getTech().split(",")));
        }
        map.put("user", user);
        model.addAttribute("user", user);
        return "/admin/about";
    }
}
