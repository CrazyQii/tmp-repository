package com.example.studentauth.dao;


import com.example.studentauth.entity.ReachDesc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface ReachDescDao {
    /**
     * 通过id查询改进
     *
     * @param excelId
     * @return
     */
    ReachDesc queryReachDescByExcel(long excelId);

    /**
     * 通过id删除改进
     *
     * @param wordId
     * @param excelId
     * @return
     */
    int deleteReachDesc(@Param("wordId") Integer wordId, @Param("excelId") long excelId);

    /**
     * 更新改进
     *
     * @param reachDesc
     * @return
     */
    int updateReachDesc(ReachDesc reachDesc);

    /**
     * 通过id查询改进
     *
     * @param wordId
     * @return
     */
    ReachDesc queryByReachDescId(int wordId);

    /**
     * 增加改进
     *
     * @param reachDesc
     * @return
     */
    int batchInsertReachDesc(ReachDesc reachDesc);
}
