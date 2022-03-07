package com.felix.forum.service;

import com.felix.forum.dao.UserRepository;
import com.felix.forum.po.User;

import java.util.HashMap;
import java.util.List;
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
		return userRepository.getu(id);
	}

	@Override
	public User getUsername(String username) {
		return userRepository.getUsername(username);
	}

	@Override
	public int insertUser(User user) {
		return userRepository.insertUser(user);
	}

	@Override
	public int count(Map<String, Object> params) {
		return userRepository.count(params);
	}

	@Override
	public List<User> listUser(Map<String, Object> params) {
		return userRepository.listUser(params);
	}

	@Override
	public int deleteUser(Long id) {
		return userRepository.deleteUser(id);
	}


}
