package com.example.studentauth.service;

import com.example.studentauth.dto.ClassGradeExecution;
import com.example.studentauth.entity.ClassesGrade;
import com.example.studentauth.excptions.ClassGradeOperationException;
import com.example.studentauth.excptions.ExcelOperationException;
import org.apache.ibatis.annotations.Param;

public interface ClassGradeService {
    /**
     * 添加班级成绩
     *
     * @param excel
     * @return
     * @throws ExcelOperationException
     */
    ClassGradeExecution addClassGrade(ClassesGrade classesGrade) throws ClassGradeOperationException;

    /**
     * 查询
     */
    ClassesGrade getCLassGradeByExcelId(long excelId);

    /**
     * 更新
     *
     * @param excel
     * @return
     */
    ClassGradeExecution modifyClassGrade(ClassesGrade classesGrade) throws ClassGradeOperationException;

    /**
     * 通过id删除考试命题
     *
     * @param subjectId
     * @param excelId
     * @return
     */
    ClassGradeExecution deleteClassGradeById(@Param("gradeId") Integer gradeId, @Param("excelId") long excelId);
}
