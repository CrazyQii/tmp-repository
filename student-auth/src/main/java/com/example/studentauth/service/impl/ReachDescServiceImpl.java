package com.example.studentauth.service.impl;

import com.example.studentauth.dao.ReachDescDao;
import com.example.studentauth.dto.ReachDescExecution;
import com.example.studentauth.entity.ReachDesc;
import com.example.studentauth.enums.ReachDescEnum;
import com.example.studentauth.excptions.ExcelOperationException;
import com.example.studentauth.excptions.ReachDescOperationException;
import com.example.studentauth.service.ReachDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReachDescServiceImpl implements ReachDescService {
    @Autowired
    private ReachDescDao reachDescDao;

    @Override
    public ReachDesc getReachDescList(long excelId) {
        return reachDescDao.queryReachDescByExcel(excelId);
    }

    @Override
    public ReachDescExecution deleteReachDescById(Integer wordId, long excelId) {
        try {
            int effectedNum = reachDescDao.deleteReachDesc(wordId, excelId);
            if (effectedNum <= 0) {
                throw new ReachDescOperationException("课程的持续改进信息删除失败");
            } else {
                return new ReachDescExecution(ReachDescEnum.SUCCESS);
            }
        } catch (Exception e) {
            throw new ReachDescOperationException("deleteReachDesc error:" + e.getMessage());
        }
    }

    @Override
    public ReachDescExecution modifyReachDesc(ReachDesc reachDesc) {
        if (reachDesc == null || reachDesc.getWordId() == null) {
            return new ReachDescExecution(ReachDescEnum.EMPTY_LIST);
        } else {
            try {
                int effectedNum = reachDescDao.updateReachDesc(reachDesc);
                if (effectedNum <= 0) {
                    return new ReachDescExecution(ReachDescEnum.INNER_ERROR);
                } else {
                    reachDesc = reachDescDao.queryByReachDescId(reachDesc.getWordId());
                    return new ReachDescExecution(ReachDescEnum.SUCCESS, reachDesc);
                }
            } catch (Exception e) {
                throw new ExcelOperationException("modifyReachDesc error" + e.getMessage());
            }
        }
    }

    @Override
    public ReachDesc getByReachDescId(int wordId) {
        return reachDescDao.queryByReachDescId(wordId);
    }

    @Override
    public ReachDescExecution InsertReachDesc(ReachDesc reachDesc) {
        if (reachDesc != null) {
            try {
                int effectedNum = reachDescDao.batchInsertReachDesc(reachDesc);
                if (effectedNum <= 0) {
                    throw new ReachDescOperationException("课程的持续改进表创建失败");
                } else {
                    return new ReachDescExecution(ReachDescEnum.SUCCESS);
                }
            } catch (Exception e) {
                throw new ReachDescOperationException("bathAddReachDesc error:" + e.getMessage());
            }
        } else {
            return new ReachDescExecution(ReachDescEnum.EMPTY_LIST);
        }
    }

}
