package com.example.studentauth.service;

import com.example.studentauth.dto.ReachPointExecution;
import com.example.studentauth.entity.ReachPoint;
import com.example.studentauth.excptions.ExcelOperationException;
import com.example.studentauth.excptions.ReachPointOperationException;

import java.util.List;

public interface ReachPointService {
    /**
     * 查询表单的评价信息
     *
     * @param excelId
     * @return
     */
    List<ReachPoint> queryReachPointList(long userId);

    /**
     * 批量添加
     *
     * @param reachPointList
     * @return
     * @throws ReachPointOperationException
     */
    ReachPointExecution batchAddReachPoint(List<ReachPoint> reachPointList) throws ReachPointOperationException;

    /**
     * 删除
     *
     * @param pointId
     * @param excelId
     * @return
     * @throws ReachPointOperationException
     */
    ReachPointExecution deleteReachPoint(int reachPointId, long userId) throws ReachPointOperationException;

    /**
     * 通过pointId查询
     *
     * @param reachPoint
     * @return
     * @throws ExcelOperationException
     */
    ReachPoint getReachPoint(int reachPointId) throws ExcelOperationException;

    /**
     * 修改信息
     *
     * @param reachPoint
     * @return
     * @throws ExcelOperationException
     */
    ReachPointExecution modifyReachPoint(ReachPoint reachPoint) throws ExcelOperationException;

    /**
     * 查询
     */
    ReachPoint getReachPointByExcelId(long excelId);

    /**
     * 更新表成绩点
     *
     * @param reachPoint
     * @return
     * @throws ExcelOperationException
     */
    ReachPointExecution modifyReachPointForScore(ReachPoint reachPoint) throws ExcelOperationException;
}
