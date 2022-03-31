package com.example.studentauth.dao;


import com.example.studentauth.entity.ClassesGrade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ClassGradeDao {
    /**
     * 通过id查询
     *
     * @param excelId
     * @return
     */
    ClassesGrade queryClassesGrade(long excelId);

    /**
     * 添加成绩
     *
     * @param classesGrade
     * @return
     */
    int insertClassesGrade(ClassesGrade classesGrade);

    /**
     * 更新成绩信息
     *
     * @param classesGrade
     * @return
     */
    int updateClassGrade(ClassesGrade classesGrade);

    /**
     * 更新成绩点
     *
     * @param classesGrade
     * @return
     */
    int updateReachPointForScore(ClassesGrade classesGrade);

    /**
     * 查询成绩列表
     *
     * @param userId
     * @return
     */
    List<ClassesGrade> queryClassGradeList(long userId);

    /**
     * 通过id删除考试命题
     *
     * @param subjectId
     * @param excelId
     * @return
     */
    int deleteClassGrade(@Param("gradeId") Integer gradeId, @Param("excelId") long excelId);
}
