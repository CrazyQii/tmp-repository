package com.example.studentauth.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;


public class Student {
    @Excel(name = "学生学号", orderNum = "0", type = 10)
    private Integer studentId;
    @Excel(name = "学生姓名", orderNum = "1")
    private String studentName;
    @Excel(name = "课堂成绩", orderNum = "2", type = 10)
    private Double classGrade;
    @Excel(name = "作业成绩", orderNum = "3", type = 10)
    private Double workGrade;
    @Excel(name = "实验成绩", orderNum = "4", type = 10)
    private Double expGrade;
    @Excel(name = "期末成绩", orderNum = "5", type = 10)
    private Double lastGrade;
    @Excel(name = "总成绩", orderNum = "6", type = 10)
    private Double allGrade;
    private Integer excelId;
    private Integer exeStudentId;
    private String excelName;
    private long userId;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Double getClassGrade() {
        return classGrade;
    }

    public void setClassGrade(Double classGrade) {
        this.classGrade = classGrade;
    }

    public Double getWorkGrade() {
        return workGrade;
    }

    public void setWorkGrade(Double workGrade) {
        this.workGrade = workGrade;
    }

    public Double getExpGrade() {
        return expGrade;
    }

    public void setExpGrade(Double expGrade) {
        this.expGrade = expGrade;
    }

    public Double getLastGrade() {
        return lastGrade;
    }

    public void setLastGrade(Double lastGrade) {
        this.lastGrade = lastGrade;
    }

    public Double getAllGrade() {
        return allGrade;
    }

    public void setAllGrade(Double allGrade) {
        this.allGrade = allGrade;
    }

    public Integer getExcelId() {
        return excelId;
    }

    public void setExcelId(Integer excelId) {
        this.excelId = excelId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Integer getExeStudentId() {
        return exeStudentId;
    }

    public void setExeStudentId(Integer exeStudentId) {
        this.exeStudentId = exeStudentId;
    }

    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }


}
