package com.example.studentauth.service.impl;

import com.example.studentauth.dao.ReachPointDao;
import com.example.studentauth.dto.ReachPointExecution;
import com.example.studentauth.entity.ReachPoint;
import com.example.studentauth.enums.ReachPointStateEnum;
import com.example.studentauth.excptions.ExcelOperationException;
import com.example.studentauth.excptions.ReachPointOperationException;
import com.example.studentauth.service.ReachPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReachPointServiceImpl implements ReachPointService {

    @Autowired
    private ReachPointDao reachPointDao;

    @Override
    public List<ReachPoint> queryReachPointList(long userId) {
        return reachPointDao.queryReachPointList(userId);
    }

    @Override
    @Transactional
    public ReachPointExecution batchAddReachPoint(List<ReachPoint> reachPointList) throws ReachPointOperationException {
        if (reachPointList != null && reachPointList.size() > 0) {
            try {
                int effectedNum = reachPointDao.batchInsertReachPoint(reachPointList);
                if (effectedNum <= 0) {
                    throw new ReachPointOperationException("达成度表单创建失败");
                } else {
                    return new ReachPointExecution(ReachPointStateEnum.SUCCESS);
                }
            } catch (Exception e) {
                throw new ReachPointOperationException("bathAddReachPoint error:" + e.getMessage());
            }
        } else {
            return new ReachPointExecution(ReachPointStateEnum.EMPTY_LIST);
        }
    }

    @Override
    public ReachPointExecution deleteReachPoint(int reachPointId, long userId) throws ReachPointOperationException {
        try {
            int effectedNum = reachPointDao.deleteReachPoint(reachPointId, userId);
            if (effectedNum <= 0) {
                throw new ReachPointOperationException("达成度表信息删除失败");
            } else {
                return new ReachPointExecution(ReachPointStateEnum.SUCCESS);
            }
        } catch (Exception e) {
            throw new ReachPointOperationException("deleteReachPoint error:" + e.getMessage());
        }
    }

    @Override
    public ReachPoint getReachPoint(int reachPointId) throws ExcelOperationException {
        return reachPointDao.queryByPointId(reachPointId);
    }

    @Override
    public ReachPointExecution modifyReachPoint(ReachPoint reachPoint) throws ExcelOperationException {
        if (reachPoint == null || reachPoint.getReachPointId() == null) {
            return new ReachPointExecution(ReachPointStateEnum.EMPTY_LIST);
        } else {
            try {
                int effectedNum = reachPointDao.updateReachPoint(reachPoint);
                if (effectedNum <= 0) {
                    return new ReachPointExecution(ReachPointStateEnum.INNER_ERROR);
                } else {
                    reachPoint = reachPointDao.queryByPointId(reachPoint.getReachPointId());
                    return new ReachPointExecution(ReachPointStateEnum.SUCCESS, reachPoint);
                }
            } catch (Exception e) {
                throw new ExcelOperationException("modifyReachPoint error" + e.getMessage());
            }
        }
    }

    @Override
    public ReachPoint getReachPointByExcelId(long excelId) {
        return reachPointDao.queryByExcelId(excelId);
    }

    @Override
    public ReachPointExecution modifyReachPointForScore(ReachPoint reachPoint) throws ExcelOperationException {
        if (reachPoint == null || reachPoint.getReachPointId() == null) {
            return new ReachPointExecution(ReachPointStateEnum.EMPTY_LIST);
        } else {
            try {
                int effectedNum = reachPointDao.updateReachPoint(reachPoint);
                if (effectedNum <= 0) {
                    return new ReachPointExecution(ReachPointStateEnum.INNER_ERROR);
                } else {
                    reachPoint = reachPointDao.queryByPointId(reachPoint.getReachPointId());
                    return new ReachPointExecution(ReachPointStateEnum.SUCCESS, reachPoint);
                }
            } catch (Exception e) {
                throw new ExcelOperationException("modifyReachPoint error" + e.getMessage());
            }
        }
    }
}
