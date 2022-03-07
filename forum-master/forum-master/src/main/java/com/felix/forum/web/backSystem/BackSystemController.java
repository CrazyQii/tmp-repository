package com.felix.forum.web.backSystem;

import com.felix.forum.po.Article;
import com.felix.forum.po.User;
import com.felix.forum.service.ArticleService;
import com.felix.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/system")
public class BackSystemController {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;
    /**
     * 用户列表
     * @param params
     * @param model
     * @return
     */
    @RequestMapping("/userManger")
    public String  userManger(@RequestParam Map<String, Object> params, Model model){
        if(params.get("page")==null) {
            params.put("offset", 0);
        }else {
            params.put("offset", (Integer.parseInt((String)params.get("page"))-1)*8);
        }
        int offset = Integer.parseInt(params.get("offset").toString());
        if(params.get("limit")==null) {
            params.put("limit", 8);
        }
        int limit = Integer.parseInt(params.get("limit").toString());
        Map<String,Object> page = new HashMap<String,Object>();
        page.put("content", userService.listUser(params));
        page.put("count", userService.count(params));
        page.put("number", params.get("page")==null?1:Integer.parseInt((String)params.get("page")));
        page.put("totalPages", userService.count(params) / limit + 1);
        page.put("limit", limit);
        model.addAttribute("page", page);
        return "backSystem/userManger";
    }

    /**
     * 删除用户
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/userManage/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        userService.deleteUser(id);
        attributes.addFlashAttribute("message", "删除成功！");
        return "redirect:/system/userManager";
    }

    @PostMapping("/userManage/search")
    public String search(@RequestParam Map<String, Object> params, Model model){
        if(params.get("page")==null) {
            params.put("offset", 0);
        }else {

            if(Integer.parseInt((String)params.get("page"))==0) {
                params.put("offset", 0);
            }else {
                params.put("offset", (Integer.parseInt((String)params.get("page"))-1)*8);
            }
        }
        int offset = Integer.parseInt(params.get("offset").toString());
        if(params.get("limit")==null) {
            params.put("limit", 8);
        }
        int limit = Integer.parseInt(params.get("limit").toString());
        Map<String,Object> page = new HashMap<String,Object>();
        page.put("content", userService.listUser(params));
        page.put("count", userService.count(params));
        page.put("number", params.get("page")==null?1:Integer.parseInt((String)params.get("page")));
        page.put("totalPages", userService.count(params) / limit + 1);
        page.put("limit", limit);
        model.addAttribute("page", page);
        return "backSystem/userManger :: userList";
    }
    @GetMapping("/viewArticle/{id}/{user}")
    public String articleMange(Model model, @PathVariable Long id, @PathVariable String user){
        if (user.equals("admin")) {
            Article article=articleService.getAndConvert(id);
            model.addAttribute("article", article);
            return "backSystem/article";
        }else{
            return "error/404";
        }
    }
}