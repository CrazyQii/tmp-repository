package com.example.studentauth.service;

import com.example.studentauth.dto.ExcelExecution;
import com.example.studentauth.entity.Excel;
import com.example.studentauth.excptions.ExcelOperationException;

import java.util.List;

public interface ExcelService {
    /**
     * 根据ExcelExecution返回列表数据
     *
     * @param excelCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    public ExcelExecution getExcelList(Excel excelCondition, int pageIndex, int pageSize);

    /**
     * 通过表获取表信息
     *
     * @param excel
     * @return
     */
    Excel getByExcelId(long excelId);

    /**
     * 更新表信息
     *
     * @param excel
     * @return
     */
    ExcelExecution modifyExcel(Excel excel) throws ExcelOperationException;

    /**
     * 添加表信息
     *
     * @param excel
     * @return
     * @throws ExcelOperationException
     */
    ExcelExecution addExcel(Excel excel) throws ExcelOperationException;

    List<Excel> getExcelListByuser(long userId);

    void delExcelById(long excelId);
}
