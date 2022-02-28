package com.felix.forum.dao;

import com.felix.forum.po.Tag;




import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagRepository{

Tag findByName(String name);


	
	List<Tag> findTop(Map<String,Object> map);
	
	
    Tag get(Long cid);
    
	int count(Map<String,Object> map);
	
	List<Tag> findAll(Map<String,Object> map);
	
	
	int save(Tag content);
	
	int update(Tag content);
	
	int remove(Map<String,Object> map);
	
	
	List<String> findtags(Map<String,Object> map);

	
	
	int saveaTag(Map<String,Object> map);
	
	int deletebyaid(Map<String,Object> map);
	
}
