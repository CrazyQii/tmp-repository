package com.example.studentauth.dao;


import com.example.studentauth.entity.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface gradeDao {
    /**
     * 新增成绩
     *
     * @param Student
     * @return
     */
    int insertStudent(Student Student);
}
