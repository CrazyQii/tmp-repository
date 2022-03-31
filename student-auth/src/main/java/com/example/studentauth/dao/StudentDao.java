package com.example.studentauth.dao;


import com.example.studentauth.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface StudentDao {
    /**
     * 通过id查询所有达成度表
     *
     * @param excelId
     * @return
     */
    List<Student> queryStudentList(long excelId);

    /**
     * 批量增加学生成绩
     *
     * @param reachPointList
     * @return
     */
    int batchInsertStudent(List<Student> studentList);

    /**
     * 通过id删除表
     *
     * @param pointId
     * @param excelId
     * @return
     */
    int deleteStudent(@Param("exeStudentId") int student, @Param("userId") long userId);

    /**
     * 更新学生成绩
     *
     * @param reachPoint
     * @return
     */
    int updateStudent(Student student);

    /**
     * 通过id学生
     *
     * @param reachPointId
     * @return
     */
    Student queryByexestudentId(int exeStudentId);

    /**
     * 查询试卷100到90
     *
     * @param excelId
     * @return
     */
    int queryCountLastbest(long excelId);

    /**
     * 试卷90到80
     *
     * @param excelId
     * @return
     */
    int queryCountLast2(long excelId);

    /**
     * 试卷80到70
     *
     * @param excelId
     * @return
     */
    int queryCountLast3(long excelId);

    /**
     * 试卷70到60
     *
     * @param excelId
     * @return
     */
    int queryCountLast4(long excelId);

    /**
     * 试卷60以下
     *
     * @param excelId
     * @return
     */
    int queryCountLast5(long excelId);

    /**
     * 总分100到90
     *
     * @param excelId
     * @return
     */
    int queryCountallbest(long excelId);

    /**
     * 总分90到80
     *
     * @param excelId
     * @return
     */
    int queryCountall2(long excelId);

    /**
     * 总分80到70
     *
     * @param excelId
     * @return
     */
    int queryCountall3(long excelId);

    /**
     * 总分70到60
     *
     * @param excelId
     * @return
     */
    int queryCountall4(long excelId);

    /**
     * 总分60以下
     *
     * @param excelId
     * @return
     */
    int queryCountall5(long excelId);

    /**
     * 查询试卷最高分
     *
     * @param excelId
     * @return
     */
    double querymaxlast(long excelId);

    /**
     * 查询试卷最低分
     *
     * @param excelId
     * @return
     */
    double queryminlast(long excelId);

    /**
     * 查询总分最高分
     *
     * @param excelId
     * @return
     */
    double querymaxall(long excelId);

    /**
     * 查询总分最低分
     *
     * @param excelId
     * @return
     */
    double queryminall(long excelId);

    /**
     * 通过excelid查询
     *
     * @param excelId
     * @return
     */
    Student queryStudentByExcelId(long excelId);

    /**
     * 通过的excel查询列表
     *
     * @param excel
     * @return
     */
    List<Student> queryStudentListByExcel(long excel);
}
