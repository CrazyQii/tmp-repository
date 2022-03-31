package com.example.studentauth.dao;


import com.example.studentauth.entity.ReachPoint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ReachPointDao {
    /**
     * 通过id查询所有达成度表
     *
     * @param excelId
     * @return
     */
    List<ReachPoint> queryReachPointList(long userId);

    /**
     * 批量增加达成度表评价
     *
     * @param reachPointList
     * @return
     */
    int batchInsertReachPoint(List<ReachPoint> reachPointList);

    /**
     * 通过id删除表
     *
     * @param pointId
     * @param excelId
     * @return
     */
    int deleteReachPoint(@Param("reachPointId") int reachPointId, @Param("userId") long userId);

    /**
     * 更新表
     *
     * @param reachPoint
     * @return
     */
    int updateReachPoint(ReachPoint reachPoint);

    /**
     * 通过id查询表
     *
     * @param reachPointId
     * @return
     */
    ReachPoint queryByPointId(int reachPointId);

    /**
     * 通过id查询
     *
     * @param excelId
     * @return
     */
    ReachPoint queryByExcelId(long excelId);

    /**
     * 更新表成绩点
     *
     * @param reachPoint
     * @return
     */
    int updateReachPointForScore(ReachPoint reachPoint);
}
