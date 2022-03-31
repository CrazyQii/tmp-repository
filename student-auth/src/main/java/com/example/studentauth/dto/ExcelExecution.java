package com.example.studentauth.dto;

import com.example.studentauth.entity.Excel;
import com.example.studentauth.enums.ExcelStateEnum;

import java.util.List;

public class ExcelExecution {
    //结果状态
    private int state;

    //状态标识
    private String stateInfo;

    //表数量
    private int count;

    //操作的表(增删改查);
    private Excel excel;

    //excel列表(查询表列表时使用)
    private List<Excel> excelList;

    public ExcelExecution() {

    }

    //表操作失败的构造器
    public ExcelExecution(ExcelStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    //表操作成功的构造器
    public ExcelExecution(ExcelStateEnum stateEnum, Excel excel) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.excel = excel;
    }

    //表操作成功的构造器
    public ExcelExecution(ExcelStateEnum stateEnum, List<Excel> excelList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.excelList = excelList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Excel getExcel() {
        System.out.println(excel.getExcelName() + " " + excel.getExcelId() + " " + excel.getClassesName());
        return excel;
    }

    public void setExcel(Excel excel) {
        this.excel = excel;

    }

    public List<Excel> getExcelList() {
        return excelList;
    }

    public void setExcelList(List<Excel> excelList) {
        this.excelList = excelList;
    }
}
