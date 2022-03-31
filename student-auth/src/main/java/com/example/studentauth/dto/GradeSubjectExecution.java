package com.example.studentauth.dto;

import com.example.studentauth.entity.GradeSubject;
import com.example.studentauth.enums.GradeSubjectEnum;

import java.util.List;

public class GradeSubjectExecution {
    //结果状态
    private int state;

    //状态标识
    private String stateInfo;

    //表数量
    private int count;

    //操作的表(增删改查);
    private GradeSubject gradeSubject;

    //excel列表(查询表列表时使用)
    private List<GradeSubject> gradeSubjectList;

    public GradeSubjectExecution() {

    }

    //表操作失败的构造器
    public GradeSubjectExecution(GradeSubjectEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    //表操作成功的构造器
    public GradeSubjectExecution(GradeSubjectEnum stateEnum, GradeSubject gradeSubject) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.gradeSubject = gradeSubject;
    }

    //表操作成功的构造器
    public GradeSubjectExecution(GradeSubjectEnum stateEnum, List<GradeSubject> gradeSubjectList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.gradeSubjectList = gradeSubjectList;
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

    public GradeSubject getGradeSubject() {
        return gradeSubject;
    }

    public void setGradeSubject(GradeSubject gradeSubject) {
        this.gradeSubject = gradeSubject;
    }

    public List<GradeSubject> getGradeSubjectList() {
        return gradeSubjectList;
    }

    public void setGradeSubjectList(List<GradeSubject> gradeSubjectList) {
        this.gradeSubjectList = gradeSubjectList;
    }

}
