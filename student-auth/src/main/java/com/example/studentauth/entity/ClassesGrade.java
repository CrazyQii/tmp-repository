package com.example.studentauth.entity;

public class ClassesGrade {
    private Integer excelId;
    private Integer gradeId;
    private String classesName;
    private Double avgClassGrade;
    private Double avgWorkGrade;
    private Double avgLastGrade;
    private Double avgAllGrade;
    private Double avgExpGrade;
    private Integer studentCount;
    private Double classPoint;
    private Double workPoint;
    private Double lastPoint;
    private Double expPoint;
    private Double allPoint;
    private long userId;

    public Integer getExcelId() {
        return excelId;
    }

    public void setExcelId(Integer excelId) {
        this.excelId = excelId;
    }

    public String getClassesName() {
        return classesName;
    }

    public void setClassesName(String classesName) {
        this.classesName = classesName;
    }

    public Double getAvgClassGrade() {
        return avgClassGrade;
    }

    public void setAvgClassGrade(Double avgClassGrade) {
        this.avgClassGrade = avgClassGrade;
    }

    public Double getAvgWorkGrade() {
        return avgWorkGrade;
    }

    public void setAvgWorkGrade(Double avgWorkGrade) {
        this.avgWorkGrade = avgWorkGrade;
    }

    public Double getAvgLastGrade() {
        return avgLastGrade;
    }

    public void setAvgLastGrade(Double avgLastGrade) {
        this.avgLastGrade = avgLastGrade;
    }

    public Double getAvgAllGrade() {
        return avgAllGrade;
    }

    public void setAvgAllGrade(Double avgAllGrade) {
        this.avgAllGrade = avgAllGrade;
    }

    public Double getAvgExpGrade() {
        return avgExpGrade;
    }

    public void setAvgExpGrade(Double avgExpGrade) {
        this.avgExpGrade = avgExpGrade;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public Integer getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Double getClassPoint() {
        return classPoint;
    }

    public void setClassPoint(Double classPoint) {
        this.classPoint = classPoint;
    }

    public Double getWorkPoint() {
        return workPoint;
    }

    public void setWorkPoint(Double workPoint) {
        this.workPoint = workPoint;
    }

    public Double getLastPoint() {
        return lastPoint;
    }

    public void setLastPoint(Double lastPoint) {
        this.lastPoint = lastPoint;
    }

    public Double getExpPoint() {
        return expPoint;
    }

    public void setExpPoint(Double expPoint) {
        this.expPoint = expPoint;
    }

    public Double getAllPoint() {
        return allPoint;
    }

    public void setAllPoint(Double allPoint) {
        this.allPoint = allPoint;
    }


}
