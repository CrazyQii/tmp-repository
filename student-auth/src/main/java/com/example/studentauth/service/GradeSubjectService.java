package com.example.studentauth.service;

import com.example.studentauth.dto.GradeSubjectExecution;
import com.example.studentauth.entity.GradeSubject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GradeSubjectService {
    /**
     * 通过id查询所有考试命题
     *
     * @param excelId
     * @return
     */
    List<GradeSubject> getGradeSubjectList(long userId);

    /**
     * 通过excel查询
     *
     * @param excelId
     * @return
     */
    List<GradeSubject> getGradeSubjectListByExcel(long excelId);

    /**
     * 通过id删除考试命题
     *
     * @param subjectId
     * @param excelId
     * @return
     */
    GradeSubjectExecution deleteGradeSubjectById(@Param("subjectId") Integer subjectId, @Param("userId") long userId);

    /**
     * 更新考试命题
     *
     * @param gradeSubject
     * @return
     */
    GradeSubjectExecution modifyGradeSubject(GradeSubject gradeSubject);

    /**
     * 通过id查询考试命题
     *
     * @param gradeSubjectId
     * @return
     */
    GradeSubject getBySubjectId(int gradeSubjectId);

    /**
     * 增加考试命题
     *
     * @param gradeSubject
     * @return
     */
    GradeSubjectExecution InsertGradeSubject(GradeSubject gradeSubject);
}
