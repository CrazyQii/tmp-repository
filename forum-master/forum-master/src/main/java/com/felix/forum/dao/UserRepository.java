package com.felix.forum.dao;

import com.felix.forum.po.Type;
import com.felix.forum.po.User;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

@Mapper
public interface UserRepository{


    User findByUsernameAndPassword(Map<String, Object> map);

    User getu(Long id);

    User getUsername(String username);

    int insertUser(User user);

    int count(Map<String, Object> params);

    List<User> listUser(Map<String, Object> params);

    int deleteUser(Long id);
}
