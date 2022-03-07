package com.felix.forum.service;

import com.felix.forum.po.Discuss;
import com.felix.forum.po.Tag;

import java.util.List;
import java.util.Map;

/**
 * @program: DiscussService
 * @author: hanLinQi
 * @create: 2022-03-05 23:26
 **/
public interface DiscussService {

    void saveDiscuss(Discuss discuss);

    Discuss getDiscuss(Long id);

    List<Discuss> getDiscusses(Map<String,Object> map);

    void deleteById(Long id);

    int count(Map<String,Object> map);
}
