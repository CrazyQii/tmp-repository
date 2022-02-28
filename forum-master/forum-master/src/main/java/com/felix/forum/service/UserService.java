package com.felix.forum.service;

import org.apache.ibatis.annotations.Mapper;

import com.felix.forum.po.Tag;
import com.felix.forum.po.User;



public interface UserService {
    User checkUser(String username, String password);

    User getUserInfo(Long id);
}
