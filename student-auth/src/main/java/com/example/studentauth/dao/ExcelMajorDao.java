package com.example.studentauth.dao;

import com.example.studentauth.entity.major;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ExcelMajorDao {
    List<major> queryExcelMajor();
}
