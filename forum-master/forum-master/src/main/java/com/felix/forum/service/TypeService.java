package com.felix.forum.service;

import com.felix.forum.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TypeService {

    int saveType(Type type);

    Type getType(Long id);

    Type getTypeByName(String name);

    List<Type> listType(Map<String, Object> params);
    
    
    int count(Map<String, Object> params);

    List<Type> listType();

    List<Type> listTypeTop(Integer size);

    int updateType(Long id, Type type);

    void deleteType(Long id);
}
