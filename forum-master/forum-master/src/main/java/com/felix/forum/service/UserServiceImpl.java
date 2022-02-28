package com.felix.forum.service;

import com.felix.forum.dao.UserRepository;
import com.felix.forum.po.User;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @date 2022/02/16 15:18
 **/
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("username", username);
    	map.put("password", password);
        User user = userRepository.findByUsernameAndPassword(map);
        return user;
    }

	@Override
	public User getUserInfo(Long id) {
		Map<String,Object> map = new HashMap<String,Object>();
    	map.put("id", id);
		 return userRepository.getu(map);
	}

  
}
