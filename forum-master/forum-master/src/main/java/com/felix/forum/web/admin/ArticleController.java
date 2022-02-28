package com.felix.forum.web.admin;

import com.felix.forum.po.Article;
import com.felix.forum.po.Tag;
import com.felix.forum.po.Type;
import com.felix.forum.po.User;
import com.felix.forum.service.ArticleService;
import com.felix.forum.service.TagService;
import com.felix.forum.service.TypeService;
import com.felix.forum.vo.ArticleQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * @author Administrator
 * @date 2022/02/16 16:43
 **/
@Controller
@RequestMapping("/admin")
public class ArticleController {

    private static final String INPUT = "admin/articleInput";
    private static final String LIST = "admin/articleManage";
    private static final String REDIRECT_LIST = "redirect:admin/articleManage";

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/articleManage")
    public String articleManage(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
    		@RequestParam Map<String, Object> params, Model model) {
        model.addAttribute("types",typeService.listType());
        
        
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
        
        
        
        return "admin/articleManage";
    }

    @PostMapping("/articleManage/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
    		@RequestParam Map<String, Object> params, Model model) {
    	
    	
    	
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
    	page.put("content", articleService.listArticle(params));
    	page.put("count", articleService.count(params));
    	
    	page.put("number", params.get("page")==null?1:Integer.parseInt((String)params.get("page")));
    	page.put("totalPages", articleService.count(params) / limit + 1);
    	page.put("limit", limit);
        
        model.addAttribute("page", page);
        
        
        
        return "admin/articleManage :: articleList";
    }

    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }

    @GetMapping("/articleManage/input")
    public String input(Model model) {
        setTypeAndTag(model);
        model.addAttribute("article", new Article());
        return "admin/articleInput";
    }

    @GetMapping("/articleManage/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        setTypeAndTag(model);
        Article article = articleService.getArticle(id);
        
        
        
        Type ty = typeService.getType(Long.parseLong(article.getTypeId()));
        article.setType(ty);
        
        List<Tag> tags = new ArrayList<>();
    	String ts="";
    	Map<String,Object> map3 = new HashMap<String,Object>();
    	map3.put("articles_id", article.getId());
    	List<String> tagids =  tagService.findtags(map3);     
    	for(String aa : tagids) {
    		Tag tag = tagService.getTag(Long.parseLong(aa));
    		tags.add(tag);
    		ts=ts+tag.getId()+",";
    	}
    	article.setTagIds(ts);
    	
    	article.setTags(tags);
        
    	model.addAttribute("type", ty);
    	model.addAttribute("tags", tags);
    	
    	
        article.init();
        model.addAttribute("article", article);
        return "admin/articleInput";
    }

    @PostMapping("/articleManage")
    public String post(Article article, RedirectAttributes attributes, HttpSession session) {
        article.setUser((User) session.getAttribute("user"));
      
        article.setTags(tagService.listTag(article.getTagIds()));
        int article1 = 0 ;
        if (article.getId() == null) {
        	
        	
        	article.setTypeId(String.valueOf(article.getTypeId()));
        	
        	List<Tag> tgs = tagService.listTag(article.getTagIds());
  
        	article.setUserId("1");
        
            article1 = articleService.saveArticle(article,tgs);
        } else {
        	List<Tag> tgs = tagService.listTag(article.getTagIds());
            article1 = articleService.updateArticle(article.getId(), article,tgs);
        }
        if (article1 > 0) {
        	attributes.addFlashAttribute("message", "操作成功！");
            
        } else {
        	attributes.addFlashAttribute("message", "操作失败！");
        }
        return "redirect:/admin/articleManage";
    }

    @GetMapping("/articleManage/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        articleService.deleteArticle(id);
        attributes.addFlashAttribute("message", "删除成功！");
        return "redirect:/admin/articleManage";
    }
}
