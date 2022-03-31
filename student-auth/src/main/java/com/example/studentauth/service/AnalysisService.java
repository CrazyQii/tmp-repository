package com.example.studentauth.service;

import com.example.studentauth.dto.AnalysisExecution;
import com.example.studentauth.entity.Analysis;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AnalysisService {
    /**
     * 通过id查询分析
     *
     * @param excelId
     * @return
     */
    List<Analysis> getAnalysisList(long excelId);

    /**
     * 通过id删除分析
     *
     * @param subjectId
     * @param excelId
     * @return
     */
    AnalysisExecution deleteAnalysisById(@Param("anlysisId") Integer analysisId, @Param("userId") long userId);

    /**
     * 更新分析
     *
     * @param gradeSubject
     * @return
     */
    AnalysisExecution modifyAnalysis(Analysis analysis);

    /**
     * 通过id查询分析
     *
     * @param gradeSubjectId
     * @return
     */
    Analysis getByAnalysisId(int analysisId);

    /**
     * 增加分析
     *
     * @param gradeSubject
     * @return
     */
    AnalysisExecution InsertAnalysis(Analysis analysis);

    /**
     * 查询
     */
    Analysis getAnalysisByExcelId(long excelId);
}
