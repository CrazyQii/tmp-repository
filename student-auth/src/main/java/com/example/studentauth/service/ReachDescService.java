package com.example.studentauth.service;

import com.example.studentauth.dto.ReachDescExecution;
import com.example.studentauth.entity.ReachDesc;
import org.apache.ibatis.annotations.Param;

public interface ReachDescService {
    /**
     * 通过id查询改进
     *
     * @param excelId
     * @return
     */
    ReachDesc getReachDescList(long excelId);

    /**
     * 通过id删除考试命题
     *
     * @param subjectId
     * @param excelId
     * @return
     */
    ReachDescExecution deleteReachDescById(@Param("wordId") Integer wordId, @Param("excelId") long excelId);

    /**
     * 更新考试命题
     *
     * @param gradeSubject
     * @return
     */
    ReachDescExecution modifyReachDesc(ReachDesc reachDesc);

    /**
     * 通过id查询考试命题
     *
     * @param gradeSubjectId
     * @return
     */
    ReachDesc getByReachDescId(int wordId);

    /**
     * 增加考试命题
     *
     * @param gradeSubject
     * @return
     */
    ReachDescExecution InsertReachDesc(ReachDesc reachDesc);
}
