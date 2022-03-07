package com.felix.forum.dao;

import com.felix.forum.po.Discuss;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @program: DiscussRepository
 * @author: hanLinQi
 * @create: 2022-03-04 17:12
 **/
@Mapper
public interface DiscussRepository {

    /**
     * 批量查找评论
     * @param discuss
     * @return
     */
    List<Discuss> findDiscusses(Map<String,Object> map);

    /**
     * 根据id查找话题
     * @param id
     * @return
     */
    Discuss findDiscussById(Long id);

    /**
     * 添加话题
     * @param discuss
     */
    void saveDiscuss(Discuss discuss);

    /**
     * 删除话题
     * @param id
     */
    void remove(Long id);

    /**
     * 话题总数
     * @return
     */
    int count(Map<String,Object> map);
}
