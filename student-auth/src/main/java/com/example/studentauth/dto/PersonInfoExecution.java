package com.example.studentauth.dto;

import com.example.studentauth.entity.PersonInfo;
import com.example.studentauth.enums.PersonInfoStateEnum;

import java.util.List;

public class PersonInfoExecution {
    //结果状态
    private int state;

    //状态标识
    private String stateInfo;

    //表数量
    private int count;

    //操作的表(增删改查);
    private PersonInfo personInfo;

    //excel列表(查询表列表时使用)
    private List<PersonInfo> personInfoList;

    public PersonInfoExecution() {

    }

    //表操作失败的构造器
    public PersonInfoExecution(PersonInfoStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    //表操作成功的构造器
    public PersonInfoExecution(PersonInfoStateEnum stateEnum, PersonInfo personInfo) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.personInfo = personInfo;
    }

    //表操作成功的构造器
    public PersonInfoExecution(PersonInfoStateEnum stateEnum, List<PersonInfo> personInfoList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.personInfoList = personInfoList;
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

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

    public List<PersonInfo> getPersonInfoList() {
        return personInfoList;
    }

    public void setPersonInfoList(List<PersonInfo> personInfoList) {
        this.personInfoList = personInfoList;
    }

}
