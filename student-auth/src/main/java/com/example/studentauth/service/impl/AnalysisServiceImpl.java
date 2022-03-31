package com.example.studentauth.service.impl;

import com.example.studentauth.dao.AnalysisDao;
import com.example.studentauth.dto.AnalysisExecution;
import com.example.studentauth.entity.Analysis;
import com.example.studentauth.enums.AnalysisEnum;
import com.example.studentauth.excptions.AnalysisOperationException;
import com.example.studentauth.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalysisServiceImpl implements AnalysisService {
    @Autowired
    private AnalysisDao analysisDao;

    @Override
    public List<Analysis> getAnalysisList(long userId) {
        return analysisDao.queryAnalysisByExcel(userId);
    }

    @Override
    public Analysis getAnalysisByExcelId(long excelId) {
        return analysisDao.queryAnalysisByExcelId(excelId);
    }

    @Override
    public AnalysisExecution deleteAnalysisById(Integer analysisId, long excelId) {
        try {
            int effectedNum = analysisDao.deleteAnalysis(analysisId, excelId);
            if (effectedNum <= 0) {
                throw new AnalysisOperationException("课程成绩分析信息删除失败");
            } else {
                return new AnalysisExecution(AnalysisEnum.SUCCESS);
            }
        } catch (Exception e) {
            throw new AnalysisOperationException("deleteAnalysis error:" + e.getMessage());
        }
    }

    @Override
    public AnalysisExecution modifyAnalysis(Analysis analysis) {
        if (analysis == null || analysis.getAnalysisId() == null) {
            return new AnalysisExecution(AnalysisEnum.EMPTY_LIST);
        } else {
            try {
                int effectedNum = analysisDao.updateAnalysis(analysis);
                if (effectedNum <= 0) {
                    return new AnalysisExecution(AnalysisEnum.INNER_ERROR);
                } else {
                    analysis = analysisDao.queryByAnalysisId(analysis.getAnalysisId());
                    return new AnalysisExecution(AnalysisEnum.SUCCESS, analysis);
                }
            } catch (Exception e) {
                throw new AnalysisOperationException("modifyAnalysis error" + e.getMessage());
            }
        }
    }

    @Override
    public Analysis getByAnalysisId(int analysisId) {
        return analysisDao.queryByAnalysisId(analysisId);
    }

    @Override
    public AnalysisExecution InsertAnalysis(Analysis analysis) {
        if (analysis != null) {
            try {
                int effectedNum = analysisDao.batchInsertAnalysis(analysis);
                if (effectedNum <= 0) {
                    throw new AnalysisOperationException("成绩分析信息创建失败");
                } else {
                    return new AnalysisExecution(AnalysisEnum.SUCCESS);
                }
            } catch (Exception e) {
                throw new AnalysisOperationException("bathAddReachDesc error:" + e.getMessage());
            }
        } else {
            return new AnalysisExecution(AnalysisEnum.EMPTY_LIST);
        }
    }
}
