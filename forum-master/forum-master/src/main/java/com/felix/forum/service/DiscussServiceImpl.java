package com.felix.forum.service;

import com.felix.forum.dao.DiscussRepository;
import com.felix.forum.po.Discuss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: DiscussServiceImpl
 * @description:
 * @author: hanLinQi
 * @create: 2022-03-05 23:29
 **/
@Service
public class DiscussServiceImpl implements DiscussService {

    @Autowired
    private DiscussRepository repository;

    @Override
    public void saveDiscuss(Discuss discuss) {
        repository.saveDiscuss(discuss);
    }

    @Override
    public Discuss getDiscuss(Long id) {
        return repository.findDiscussById(id);
    }

    @Override
    public List<Discuss> getDiscusses(Map<String,Object> map) {
        return repository.findDiscusses(map);
    }

    @Override
    public void deleteById(Long id) {
        repository.remove(id);
    }

    @Override
    public int count(Map<String,Object> map) {
        return repository.count(map);
    }
}
