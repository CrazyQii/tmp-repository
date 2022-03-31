package com.example.studentauth.dto;

import com.example.studentauth.entity.ClassesGrade;
import com.example.studentauth.enums.StudentStateEnum;

import java.util.List;

public class ClassGradeExecution {
    //结果状态
    private int state;

    //状态标识
    private String stateInfo;

    //表数量
    private int count;

    //操作的表(增删改查);
    private ClassesGrade classesGrade;

    //excel列表(查询表列表时使用)
    private List<ClassesGrade> classesGradeList;

    public ClassGradeExecution() {

    }

    //表操作失败的构造器
    public ClassGradeExecution(StudentStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    //表操作成功的构造器
    public ClassGradeExecution(StudentStateEnum stateEnum, ClassesGrade classesGrade) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.classesGrade = classesGrade;
    }

    //表操作成功的构造器
    public ClassGradeExecution(StudentStateEnum stateEnum, List<ClassesGrade> classesGradeList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.classesGradeList = classesGradeList;
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

    public ClassesGrade getClassesGrade() {
        return classesGrade;
    }

    public void setClassesGrade(ClassesGrade classesGrade) {
        this.classesGrade = classesGrade;
    }

    public List<ClassesGrade> getClassesGradeList() {
        return classesGradeList;
    }

    public void setClassesGradeList(List<ClassesGrade> classesGradeList) {
        this.classesGradeList = classesGradeList;
    }

}
