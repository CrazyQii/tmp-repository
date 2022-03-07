package com.felix.forum.dao;

import com.felix.forum.po.Article;
import com.felix.forum.po.Tag;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleRepository{

	
	List<Article> findAll(Map<String,Object> map);
	
	
	
    List<String> findGroupYear(Long userId);


    List<Article> findByYear(Map<String,Object> map);
    
    
    Article getOne(Long cid);
    
	int save(Article content);
	
	int update(Article content);
	
	int remove(Map<String,Object> map);
	
	
	
	
	int count();
	
    List<Article> findTop();
    
    
    List<Article> findByQuery(Map<String,Object> map);
    
    int updateViews(Long id);
    
    
    int count(Map<String,Object> map);
    
    List<Article> listArticlebytype(Map<String,Object> map);
    
    List<Article> listArticlebytag(Map<String,Object> map);
}
