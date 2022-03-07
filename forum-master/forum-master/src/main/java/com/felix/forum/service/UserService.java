package com.felix.forum.service;

import org.apache.ibatis.annotations.Mapper;

import com.felix.forum.po.Tag;
import com.felix.forum.po.User;

import java.util.List;
import java.util.Map;


public interface UserService {
    User checkUser(String username, String password);

    User getUserInfo(Long id);

    User getUsername(String username);

    int insertUser(User user);

    int count(Map<String, Object> params);

    List<User> listUser(Map<String, Object> params);

    int deleteUser(Long id);
}
