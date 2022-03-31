package com.example.studentauth.dto;

import com.example.studentauth.entity.Student;
import com.example.studentauth.enums.StudentStateEnum;

import java.util.List;

public class StudentExecution {
    //结果状态
    private int state;

    //状态标识
    private String stateInfo;

    //表数量
    private int count;

    //操作的表(增删改查);
    private Student student;

    //excel列表(查询表列表时使用)
    private List<Student> studentList;

    public StudentExecution() {

    }

    //表操作失败的构造器
    public StudentExecution(StudentStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    //表操作成功的构造器
    public StudentExecution(StudentStateEnum stateEnum, Student student) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.student = student;
    }

    //表操作成功的构造器
    public StudentExecution(StudentStateEnum stateEnum, List<Student> studentList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.studentList = studentList;
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

}
