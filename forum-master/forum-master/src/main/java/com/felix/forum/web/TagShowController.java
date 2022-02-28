package com.felix.forum.web;

import com.felix.forum.po.Tag;
import com.felix.forum.service.ArticleService;
import com.felix.forum.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @date 2022/02/24 11:08
 **/
@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleService articleService;

    @GetMapping("/tags/{id}")
    public String types(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model) {
        List<Tag> tags = tagService.listTagTop(10000);
        if (id == -1) {
            id = tags.get(0).getId();

        }
        model.addAttribute("tags", tags);
        
        
        
        
       Map<String, Object> params = new HashMap<String, Object>();

       params.put("tagId", id);
        
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
    	page.put("content", articleService.listArticle(params));
    	page.put("count", articleService.count(params));
    	
    	page.put("number", params.get("page")==null?1:Integer.parseInt((String)params.get("page")));
    	page.put("totalPages", articleService.count(params) / limit + 1);
    	page.put("limit", limit);
        
        model.addAttribute("page", page);

        
        
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
