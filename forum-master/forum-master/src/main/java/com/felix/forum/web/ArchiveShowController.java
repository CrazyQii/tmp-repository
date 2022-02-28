package com.felix.forum.web;

import com.felix.forum.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Administrator
 * @date 2022/02/24 14:22
 **/
@Controller
public class ArchiveShowController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/archives")
    public String archives(Model model) {
        model.addAttribute("archiveMap", articleService.archiveArticle());
        model.addAttribute("articleCount", articleService.countArticle());
        return "archives";
    }
}
