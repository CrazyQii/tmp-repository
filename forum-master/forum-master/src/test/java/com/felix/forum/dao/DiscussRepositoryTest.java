package com.felix.forum.dao;

import com.felix.forum.po.Discuss;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class DiscussRepositoryTest {

    @Autowired
    private DiscussRepository discussRepository;

    @Test
    void findDiscussesByUser() {
//        Discuss discuss = new Discuss();
//        discuss.setUserId(1L);
//        List<Discuss> discusses = discussRepository.findDiscusses(discuss);
//        Stream.of(discusses).forEach(x -> System.out.println(discuss));

//        Discuss discuss = discussRepository.findDiscussById(2L);
//        System.out.println(discuss);
//        Discuss discuss = new Discuss();
//        discuss.setTitle("测试题目");
//        discuss.setContent("测试内容");
//        discuss.setUserId(1L);
//        discuss.setCreateTime(new Date());
//        discussRepository.saveDiscuss(discuss);
    }
}