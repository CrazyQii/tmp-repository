package com.felix.forum.service;

import com.felix.forum.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TagService {

    int saveTag(Tag tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    List<Tag> listTag(Map<String, Object> params);
    
    int count(Map<String, Object> params);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    List<Tag> listTagTop(Integer size);

    int updateTag(Long id, Tag tag);

    void deleteTag(Long id);
    
    
    List<String> findtags(Map<String,Object> map);
    
    
    
    int saveaTag(Map<String, Object> params);

	void deletebyaid(Map<String, Object> params);
}
