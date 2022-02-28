package com.felix.forum.service;

import com.felix.forum.NotFoundException;
import com.felix.forum.dao.TagRepository;
import com.felix.forum.dao.TypeRepository;
import com.felix.forum.po.Article;
import com.felix.forum.po.Tag;
import com.felix.forum.po.Type;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @date 2022/02/17 12:18
 **/
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ArticleService articleService;
    @Transactional
    @Override
    public int saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
    	
    	Tag tag = tagRepository.get(id);
    	
    	Map<String,Object> map1 = new HashMap<String,Object>();
     	map1.put("tagId", id);
     	List<Article> articles = articleService.listArticlebytag(map1);
     	
     	tag.setArticles(articles);
    	
        return tag;
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Transactional
    @Override
    public List<Tag> listTag(Map<String, Object> params) {
    	
    	List<Tag> tagll = tagRepository.findAll(params);
    	 for(Tag type:tagll){
         	Map<String,Object> map1 = new HashMap<String,Object>();
         	map1.put("tagId", type.getId());
         	List<Article> articles = articleService.listArticlebytag(map1);
         	type.setArticles(articles);
         }
        return tagll;
    }



    @Override
    public List<Tag> listTag(String ids) {
    	List<Tag> lt = new ArrayList<Tag>();
    	List<Long> ids1 = converToList(ids);
    	for(Long id:ids1) {
    		Tag tag = tagRepository.get(id);
    		lt.add(tag);
    	}	
        return lt;
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("limit", size);

        List<Tag> tagll = tagRepository.findTop(map);
        
        for(Tag type:tagll){
        	Map<String,Object> map1 = new HashMap<String,Object>();
        	map1.put("tagId", type.getId());
        	List<Article> articles = articleService.listArticlebytag(map1);
        	type.setArticles(articles);
        }
        
        return tagll;
    }

    private List<Long> converToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length; i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    @Transactional
    @Override
    public int updateTag(Long id, Tag tag) {
        Tag t = tagRepository.get(id);
        if (t == null) {
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(tag, t);
        return tagRepository.update(t);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
    	
    	Map<String,Object> map5 = new HashMap<String,Object>();
     	 map5.put("id", id);
        tagRepository.remove(map5);
    }

	@Override
	public int count(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return tagRepository.count(params);
	}

	@Override
	public List<Tag> listTag() {
		Map<String,Object> map = new HashMap<String,Object>();
    	
        return tagRepository.findAll(map);
	}

	@Override
	public List<String> findtags(Map<String, Object> map) {
		// TODO Auto-generated method stub

		
		return tagRepository.findtags(map);
	}

	@Override
	public int saveaTag(Map<String, Object> params) {
		return tagRepository.saveaTag(params);
	}
	

    @Override
    public void deletebyaid(Map<String, Object> params) {
        tagRepository.deletebyaid(params);
    }
}
