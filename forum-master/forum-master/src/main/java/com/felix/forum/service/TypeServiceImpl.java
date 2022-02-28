package com.felix.forum.service;

import com.felix.forum.NotFoundException;
import com.felix.forum.dao.TypeRepository;
import com.felix.forum.po.Article;
import com.felix.forum.po.Type;
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
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;
    
    
    @Autowired
    private ArticleService articleService;

    public int saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
    	
    	Type type = typeRepository.get(id);
    	Map<String,Object> map1 = new HashMap<String,Object>();
    	map1.put("type_id", type.getId());
    	List<Article> articles = articleService.listArticlebytype(map1);
    	type.setArticles(articles);
    	
        return type;
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Transactional
    @Override
    public List<Type> listType(Map<String, Object> params) {

    	List<Type>  li = typeRepository.findAll(params);
    	for(Type type:li){
        	Map<String,Object> map1 = new HashMap<String,Object>();
        	map1.put("type_id", type.getId());
        	List<Article> articles = articleService.listArticlebytype(map1);
        	type.setArticles(articles);
        }
    	
        return li;
    }

    @Override
    public List<Type> listType() {
    	Map<String,Object> map = new HashMap<String,Object>();
    	
        return typeRepository.findAll(map);
    }

    @Override
    public List<Type> listTypeTop(Integer size) {

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("limit", size);
        
        List<Type> li = typeRepository.findTop(map);
        for(Type type:li){
        	Map<String,Object> map1 = new HashMap<String,Object>();
        	map1.put("type_id", type.getId());
        	List<Article> articles = articleService.listArticlebytype(map1);
        	type.setArticles(articles);
        }
        
        return li;
    }

    @Transactional
    @Override
    public int updateType(Long id, Type type) {
        Type t = typeRepository.get(id);
        if (t == null) {
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type, t);
        return typeRepository.update(t);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
    	
    	Map<String,Object> map5 = new HashMap<String,Object>();
     	 map5.put("id", id);
    	
        typeRepository.remove(map5);
    }

	@Override
	public int count(Map<String, Object> params) {

		return typeRepository.count(params);
	}
}
