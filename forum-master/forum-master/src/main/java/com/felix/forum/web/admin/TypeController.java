package com.felix.forum.web.admin;


import com.felix.forum.po.Type;
import com.felix.forum.service.TypeService;

import com.felix.forum.util.Query;

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
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/typeManage")
    public String typeManage(@PageableDefault(size = 8,sort = {"id"},direction = Sort.Direction.DESC)
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
		
    	
    	Map<String,Object> page = new HashMap<String,Object>();
    	page.put("content", typeService.listType(params));
    	page.put("count", typeService.count(params));
    	
    	page.put("number", offset+1);
    	page.put("totalPages", typeService.count(params) / limit + 1);
    	page.put("limit", limit);
    	

    	model.addAttribute("page", page);
    	
        return "admin/typeManage";
    }

    @GetMapping("/typeManage/input")
    public String input(Model model){
        model.addAttribute("type", new Type());
        return "admin/typeInput";
    }

    @GetMapping("/typeManage/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/typeInput";
    }

    @PostMapping("/typeManage")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name", "nameError","??????????????????????????????????????????");
        }
        if (result.hasErrors()){
            return "admin/typeInput";
        }
        int type2 = typeService.saveType(type);
        if (type2 >0) {
        	attributes.addFlashAttribute("message", "???????????????");
            
        }else {
        	attributes.addFlashAttribute("message", "???????????????");
        }
        return "redirect:/admin/typeManage";
    }

    @PostMapping("/typeManage/{id}")
    public String editPost(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name", "nameError","??????????????????????????????????????????");
        }
        if (result.hasErrors()){
            return "admin/typeInput";
        }
        int type2 = typeService.updateType(id,type);
        if (type2 >0) {
        	attributes.addFlashAttribute("message", "???????????????");
            
        }else {
        	attributes.addFlashAttribute("message", "???????????????");
        }
        return "redirect:/admin/typeManage";
    }

    @GetMapping("/typeManage/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "???????????????");
        return "redirect:/admin/typeManage";
    }

}
