package com.example.studentauth.service.impl;

import com.example.studentauth.dao.CourseDao;
import com.example.studentauth.entity.course;
import com.example.studentauth.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServicelImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;

    @Override
    public List<course> getCourseList() {
        // TODO Auto-generated method stub
        return courseDao.queryCourse();
    }
}
