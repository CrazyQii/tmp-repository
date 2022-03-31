package com.example.studentauth.dao;


import com.example.studentauth.entity.GradeSubject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface GradeSubjectDao {
    /**
     * 通过id查询所有考试命题
     *
     * @param excelId
     * @return
     */
    List<GradeSubject> queryGradeSubjectList(long userId);

    /**
     * 通过的excel查询
     *
     * @param excel
     * @return
     */
    List<GradeSubject> queryGradeSubjectListByExcel(long excel);

    /**
     * 通过id删除考试命题
     *
     * @param subjectId
     * @param excelId
     * @return
     */
    int deleteGradeSubject(@Param("subjectId") Integer subjectId, @Param("userId") long userId);

    /**
     * 更新考试命题
     *
     * @param gradeSubject
     * @return
     */
    int updateGradeSubject(GradeSubject gradeSubject);

    /**
     * 通过id查询考试命题
     *
     * @param gradeSubjectId
     * @return
     */
    GradeSubject queryBySubjectId(int gradeSubjectId);

    /**
     * 增加考试命题
     *
     * @param gradeSubject
     * @return
     */
    int batchInsertGradeSubject(GradeSubject gradeSubject);

}
