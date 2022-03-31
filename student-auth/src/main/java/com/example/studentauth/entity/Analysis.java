package com.example.studentauth.entity;

public class Analysis {
    private Integer analysisId;
    private String assessmentAnalysis;
    private String termAnalysis;
    private String courseAnalysis;
    private String graduationAnalysis;
    private String edcationAnalysis;
    private Integer excelId;
    private long userId;
    private String excelName;

    public Integer getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(Integer analysisId) {
        this.analysisId = analysisId;
    }

    public String getTermAnalysis() {
        return termAnalysis;
    }

    public void setTermAnalysis(String termAnalysis) {
        this.termAnalysis = termAnalysis;
    }

    public Integer getExcelId() {
        return excelId;
    }

    public void setExcelId(Integer excelId) {
        this.excelId = excelId;
    }

    public String getAssessmentAnalysis() {
        return assessmentAnalysis;
    }

    public void setAssessmentAnalysis(String assessmentAnalysis) {
        this.assessmentAnalysis = assessmentAnalysis;
    }

    public String getCourseAnalysis() {
        return courseAnalysis;
    }

    public void setCourseAnalysis(String courseAnalysis) {
        this.courseAnalysis = courseAnalysis;
    }

    public String getGraduationAnalysis() {
        return graduationAnalysis;
    }

    public void setGraduationAnalysis(String graduationAnalysis) {
        this.graduationAnalysis = graduationAnalysis;
    }

    public String getEdcationAnalysis() {
        return edcationAnalysis;
    }

    public void setEdcationAnalysis(String edcationAnalysis) {
        this.edcationAnalysis = edcationAnalysis;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }

}
