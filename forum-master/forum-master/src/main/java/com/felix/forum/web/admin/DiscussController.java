package com.felix.forum.web.admin;

import com.felix.forum.po.Discuss;
import com.felix.forum.po.User;
import com.felix.forum.service.DiscussService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: DiscussController
 * @description:
 * @author: hanLinQi
 * @create: 2022-03-05 23:24
 **/
@Controller
@RequestMapping(value = "/admin")
public class DiscussController {

    private Logger logger = LoggerFactory.getLogger(DiscussController.class);

    @Autowired
    private DiscussService discussService;

    @GetMapping("/discussManage/{parentId}")
    public String getDiscuss(@PageableDefault(value = 0, size = 8) Pageable pageable,
                             @RequestParam Map<String, Object> params,
                             @PathVariable(value = "parentId") String parentId,
                             Model model) {

        params.put("offset", pageable.getPageNumber());
        params.put("limit", pageable.getPageSize());
        params.put("sort", "create_time");
        params.put("direction", "DESC");
        params.put("parentId", parentId);

        Discuss discuss = discussService.getDiscuss(Long.parseLong(parentId));
        List<Discuss> discussList =  discussService.getDiscusses(params);
        int _sum = discussService.count(params);

        Map<String, Object> result = new HashMap<>();
        result.put("sum", _sum);
        result.put("pDiscuss", discuss);
        result.put("sDiscuss", discussList);
        model.addAttribute("data", result);

        logger.info(result.toString());
        return "/discussDetail";
    }

    @PostMapping("/discussManage/input")
    public String getDiscuss(@RequestParam Map<String, Object> params, HttpSession session, Model model) {

        logger.info(params.toString());
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "/admin/login";
        }
        Discuss discuss = new Discuss();
        discuss.setUserId(user.getId());
        discuss.setContent((String) params.get("content"));
        discuss.setCreateTime(new Date());
        discuss.setParentId(Long.parseLong((String) params.get("parentId")));
        discussService.saveDiscuss(discuss);
        model.addAttribute("data", discuss);

        return "redirect:/admin/discussManage/" + discuss.getParentId();
    }

    @GetMapping("/discussManage/delete/{parentId}/{id}")
    public String deleteDiscuss(@PathVariable(value = "parentId") String parentId,
                                @PathVariable(value = "id") String id) {
        discussService.deleteById(Long.parseLong(id));
        return "redirect:/admin/discussManage/" + parentId;
    }
}
