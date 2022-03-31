package com.example.studentauth.dto;

import com.example.studentauth.entity.ReachDesc;
import com.example.studentauth.enums.ReachDescEnum;

import java.util.List;

public class ReachDescExecution {
    //结果状态
    private int state;

    //状态标识
    private String stateInfo;

    //表数量
    private int count;

    //操作的表(增删改查);
    private ReachDesc reachDesc;

    //excel列表(查询表列表时使用)
    private List<ReachDesc> reachDescList;

    public ReachDescExecution() {

    }

    //表操作失败的构造器
    public ReachDescExecution(ReachDescEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    //表操作成功的构造器
    public ReachDescExecution(ReachDescEnum stateEnum, ReachDesc reachDesc) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.reachDesc = reachDesc;
    }

    //表操作成功的构造器
    public ReachDescExecution(ReachDescEnum stateEnum, List<ReachDesc> reachDescList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.reachDescList = reachDescList;
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

    public ReachDesc getReachDesc() {
        return reachDesc;
    }

    public void setReachDesc(ReachDesc reachDesc) {
        this.reachDesc = reachDesc;
    }

    public List<ReachDesc> getReachDescList() {
        return reachDescList;
    }

    public void setReachDescList(List<ReachDesc> reachDescList) {
        this.reachDescList = reachDescList;
    }

}
