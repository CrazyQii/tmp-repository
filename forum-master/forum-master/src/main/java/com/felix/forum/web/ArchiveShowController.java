package com.felix.forum.web;

//import com.felix.forum.po.Article;
import com.felix.forum.service.ArticleService;
//import com.sun.applet2.AppletParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @date 2022/02/24 14:22
 **/
@Controller
@RequestMapping(value = "/admin")
public class ArchiveShowController {

    private static final Logger logger = LoggerFactory.getLogger(ArchiveShowController.class);

    @Autowired
    private ArticleService articleService;

    @GetMapping("/archives/{userId}/{year}")
    public String archives(@RequestParam Map<String, Object> params,
                           @PathVariable(value = "year") String year,
                           @PathVariable(value = "userId") String userId, Model model) {
        if(params.get("page")==null) {
            params.put("offset", 0);
        }else {
            params.put("offset", (Integer.parseInt((String)params.get("page"))-1)*8);
        }
        int offset = Integer.parseInt(params.get("offset").toString());
        if(params.get("limit")==null) {
            params.put("limit", 8);
        }
        Map<String,Object> page = new HashMap<String,Object>();
        List<String> years = new ArrayList<>();
        years = articleService.findYears(Long.parseLong(userId));
        params.put("createTime", year);
        if (years.isEmpty()) {
            page.put("count", 0);
            page.put("years", new ArrayList<>());
            page.put("content", null);
            page.put("totalPages", 0);
        } else {
            params.put("userId", Long.parseLong(userId));
            int limit = Integer.parseInt(params.get("limit").toString());
            page.put("content", articleService.listArticle(params));
            page.put("count", articleService.count(params));
            page.put("number", params.get("page")==null?1:Integer.parseInt((String)params.get("page")));
            page.put("totalPages", articleService.count(params) / limit + 1);
            page.put("limit", limit);
            page.put("years", years);
        }
        model.addAttribute("page", page);
        return "archives";
    }
}
