package com.example.studentauth.dto;

import com.example.studentauth.entity.Analysis;
import com.example.studentauth.enums.AnalysisEnum;

import java.util.List;

public class AnalysisExecution {
    //结果状态
    private int state;

    //状态标识
    private String stateInfo;

    //表数量
    private int count;

    //操作的表(增删改查);
    private Analysis analysis;

    //excel列表(查询表列表时使用)
    private List<Analysis> analysisList;

    public AnalysisExecution() {

    }

    //表操作失败的构造器
    public AnalysisExecution(AnalysisEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    //表操作成功的构造器
    public AnalysisExecution(AnalysisEnum stateEnum, Analysis analysis) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.analysis = analysis;
    }

    //表操作成功的构造器
    public AnalysisExecution(AnalysisEnum stateEnum, List<Analysis> analysisList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.analysisList = analysisList;
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

    public Analysis getAnalysis() {
        return analysis;
    }

    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
    }

    public List<Analysis> getAnalysisList() {
        return analysisList;
    }

    public void setAnalysisList(List<Analysis> analysisList) {
        this.analysisList = analysisList;
    }

}
