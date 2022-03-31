package com.example.studentauth.dao;


import com.example.studentauth.entity.course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CourseDao {
    /**
     * 列出课程列表
     *
     * @return areaList
     */
    List<course> queryCourse();
}
