package com.felix.forum.dao;

import com.felix.forum.po.Type;
import com.felix.forum.po.User;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

@Mapper
public interface UserRepository{


    
    User findByUsernameAndPassword(Map<String,Object> map);
    
    User getu(Map<String,Object> map);
}
