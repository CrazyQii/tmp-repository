package com.example.studentauth.dao;


import com.example.studentauth.entity.Analysis;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AnalysisDao {
    /**
     * 通过id查询分析
     *
     * @param excelId
     * @return
     */
    List<Analysis> queryAnalysisByExcel(long userId);

    /**
     * 通过id删除分析
     *
     * @param analysisId
     * @param excelId
     * @return
     */
    int deleteAnalysis(@Param("analysisId") Integer analysisId, @Param("userId") long userId);

    /**
     * 更新分析
     *
     * @param analysis
     * @return
     */
    int updateAnalysis(Analysis analysis);

    /**
     * 通过id查询分析
     *
     * @param analysisId
     * @return
     */
    Analysis queryByAnalysisId(int analysisId);

    /**
     * 增加分析
     *
     * @param analysis
     * @return
     */
    int batchInsertAnalysis(Analysis analysis);

    /**
     * 通过excelId查询
     *
     * @param excelId
     * @return
     */
    Analysis queryAnalysisByExcelId(long excelId);
}
