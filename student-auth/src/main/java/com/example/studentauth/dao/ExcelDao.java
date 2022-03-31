package com.example.studentauth.dao;


import com.example.studentauth.entity.Excel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ExcelDao {
    /**
     * 分页查询表单，可输入的条件有表名字，区域id，owner
     *
     * @param ExcelCondition
     * @param rowIndex从第几行开始取
     * @param pageSize返回的行数
     * @return
     */
    List<Excel> queryExcelList(@Param("ExcelCondition") Excel ExcelCondition, @Param("rowIndex") int rowIndex
            , @Param("pageSize") int pageSize);

    List<Excel> queryExcelListByUser(long userId);

    /**
     * 返回queryExcel总数
     *
     * @param ExcelCondition
     * @return
     */
    int queryExcelCount(@Param("ExcelCondition") Excel ExcelCondition);

    /**
     * 通过id查询表
     *
     * @param excelId
     * @return excel
     */
    Excel queryByExcelId(long excelId);

    /**
     * 新增成绩表
     *
     * @param Student
     * @return
     */
    int insertExcel(Excel excel);

    /**
     * 更新表信息
     *
     * @param excel
     * @return
     */
    int updateExcel(Excel excel);

    /**
     * 删除表信息
     * @param excel
     */
    void deleteExcel(Excel excel);
}

