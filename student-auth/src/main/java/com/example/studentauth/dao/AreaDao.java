package com.example.studentauth.dao;


import com.example.studentauth.entity.Area;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface AreaDao {
    /**
     * 列出区域列表
     *
     * @return areaList
     */
    List<Area> queryArea();
}
