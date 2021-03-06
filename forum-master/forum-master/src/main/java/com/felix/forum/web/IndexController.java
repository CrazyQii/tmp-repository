package com.felix.forum.web;

import com.felix.forum.NotFoundException;
import com.felix.forum.po.Article;
import com.felix.forum.po.Comment;
import com.felix.forum.po.Tag;
import com.felix.forum.po.Type;
import com.felix.forum.po.User;
import com.felix.forum.service.ArticleService;
import com.felix.forum.service.CommentService;
import com.felix.forum.service.TagService;
import com.felix.forum.service.TypeService;
import com.felix.forum.service.UserService;
import com.felix.forum.vo.ArticleQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Administrator
 * @date 2022/02/13 15:58
 **/
@Controller
public class IndexController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;
    
    @Autowired
    private UserService userService;
    
    
    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) @RequestParam Map<String, Object> params,
                        Model model) {
    	
    	int offset = 0;
    	if(params.get("page")==null) {
    		params.put("offset", 0);
    	}else {
    
    		params.put("offset", (Integer.parseInt((String)params.get("page"))-1)*8);

    	}
    	
    	if(params.get("limit")==null) {
    		params.put("limit", 8);
    	}
    	int limit = Integer.parseInt(params.get("limit").toString());
        Map<String,Object> page = new HashMap<String,Object>();
        

        params.put("published", 1);
        List<Article>  list  = articleService.listArticle(params);
        for(Article ar : list) {
        	
        	Type type =typeService.getType(Long.parseLong(ar.getTypeId()));
        	ar.setType(type);
//        	System.out.print(ar.getTypeId());
        	User user = userService.getUserInfo(Long.parseLong(ar.getUserId()));
        	ar.setUser(user);
        	

        	List<Comment>  lco = commentService.listCommentByArticleId(ar.getId());
        	ar.setComments(lco);
        	
        	List<Tag> tags = new ArrayList<>();
        	String ts="";
        	Map<String,Object> map = new HashMap<String,Object>();
        	map.put("articles_id", ar.getId());
        	List<String> tagids =  tagService.findtags(map);     
        	for(String aa : tagids) {
        		Tag tag = tagService.getTag(Long.parseLong(aa));
        		tags.add(tag);
        		ts=ts+tag.getId()+",";
        	}
        	ar.setTagIds(ts);
        	
        	ar.setTags(tags);
        	
        	
        }
        
        
    	page.put("content", list);
    	
    	
    	
    	page.put("count", articleService.count(params));
    	
    	page.put("number", params.get("page")==null?1:Integer.parseInt((String)params.get("page")));
    	page.put("totalPages", articleService.count(params) / limit + 1);
    	page.put("limit", limit);
        
        model.addAttribute("page", page);
        

        
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendArticles", articleService.listRecommendArticleTop(8));
        return "index";
    }

    @GetMapping("/tags")
    public String tags() {
        return "tags";
    }

    @GetMapping("/types")
    public String types() {
        return "types";
    }

//    @GetMapping("/archives")
//    public String archives() {
//        return "archives";
//    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/article/{id}")
    public String article(@PathVariable Long id, Model model) {
        model.addAttribute("article", articleService.getAndConvert(id));
        return "article";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
    		@RequestParam Map<String, Object> params,
                         @RequestParam String query, Model model) {
    	
    	params.put("published", 1);	
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
        params.put("title", query);
    	page.put("content", articleService.listArticle(params));
    	page.put("count", articleService.count(params));
    	
    	page.put("number", params.get("page")==null?1:Integer.parseInt((String)params.get("page")));
    	page.put("totalPages", articleService.count(params) / limit + 1);
    	page.put("limit", limit);
        
        model.addAttribute("page", page);

        
        model.addAttribute("query", query);

        return "search";
    }
}
