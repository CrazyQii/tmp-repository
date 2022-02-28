package com.felix.forum.dao;

import com.felix.forum.po.Type;

import org.apache.ibatis.annotations.Mapper;


import java.util.List;
import java.util.Map;

@Mapper
public interface TypeRepository{

    Type findByName(String name);


	
	List<Type> findTop(Map<String,Object> map);
	
	
    Type get(Long cid);
    
	int count(Map<String,Object> map);
	
	List<Type> findAll(Map<String,Object> map);
	
	
	int save(Type content);
	
	int update(Type content);
	
	int remove(Map<String,Object> map);

}
