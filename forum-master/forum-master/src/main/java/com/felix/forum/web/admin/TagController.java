package com.felix.forum.web.admin;

import com.felix.forum.po.Tag;
import com.felix.forum.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

/**
 * @author Administrator
 * @date 2022/02/17 12:32
 **/
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tagManage")
    public String tagManage(@PageableDefault(size = 8,sort = {"id"},direction = Sort.Direction.DESC)
    @RequestParam Map<String, Object> params, Model model){
    	
    	
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
		
    	params.put("published", 1);
    	Map<String,Object> page = new HashMap<String,Object>();
    	page.put("content", tagService.listTag(params));
    	page.put("count", tagService.count(params));
    	
    	page.put("number", params.get("page")==null?1:Integer.parseInt((String)params.get("page")));
    	page.put("totalPages", tagService.count(params) / limit + 1);
    	page.put("limit", limit);
    	

    	model.addAttribute("page", page);
    	

        
        
        return "admin/tagManage";
    }

    @GetMapping("/tagManage/input")
    public String input(Model model){
        model.addAttribute("tag", new Tag());
        return "admin/tagInput";
    }

    @GetMapping("/tagManage/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tagInput";
    }

    @PostMapping("/tagManage")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name", "nameError","该标签已存在，不能重复添加！");
        }
        if (result.hasErrors()){
            return "admin/tagInput";
        }
        int tag2 = tagService.saveTag(tag);
        if (tag2 >0) {
        	attributes.addFlashAttribute("message", "新增成功！");
            
        }else {
        	attributes.addFlashAttribute("message", "新增失败！");
        }
        return "redirect:/admin/tagManage";
    }

    @PostMapping("/tagManage/{id}")
    public String editPost(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name", "nameError","该标签已存在，不能重复添加！");
        }
        if (result.hasErrors()){
            return "admin/tagInput";
        }
        int tag2 = tagService.updateTag(id,tag);
        if (tag2 > 0) {
        	attributes.addFlashAttribute("message", "更新成功！");
        }else {
            
            attributes.addFlashAttribute("message", "更新失败！");
        }
        return "redirect:/admin/tagManage";
    }

    @GetMapping("/tagManage/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功！");
        return "redirect:/admin/tagManage";
    }

}
