package com.example.studentauth.dao;


import com.example.studentauth.entity.PersonInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;
@Mapper
public interface PersonInfoDao {
    public PersonInfo findLogin(PersonInfo vo) throws SQLException;

    int insertPersonInfo(PersonInfo personInfo);

    int updatePersonInfo(PersonInfo personInfo);

    int deletePersonInfo(long userId);

    PersonInfo queryByPersonInfoId(long userId);

    List<PersonInfo> queryPersonInfoList(@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    int queryPersonCount();
}
