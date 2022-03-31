package com.example.studentauth.dto;

import com.example.studentauth.entity.ReachPoint;
import com.example.studentauth.enums.ReachPointStateEnum;

import java.util.List;

public class ReachPointExecution {
    //结果状态
    private int state;

    //状态标识
    private String stateInfo;

    //表数量
    private int count;

    //操作的表(增删改查);
    private ReachPoint reachPoint;

    //excel列表(查询表列表时使用)
    private List<ReachPoint> reachPointList;

    public ReachPointExecution() {

    }

    //表操作失败的构造器
    public ReachPointExecution(ReachPointStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    //表操作成功的构造器
    public ReachPointExecution(ReachPointStateEnum stateEnum, ReachPoint reachPoint) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.reachPoint = reachPoint;
    }

    //表操作成功的构造器
    public ReachPointExecution(ReachPointStateEnum stateEnum, List<ReachPoint> reachPointList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.reachPointList = reachPointList;
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

    public ReachPoint getReachPoint() {
        return reachPoint;
    }

    public void setReachPoint(ReachPoint reachPoint) {
        this.reachPoint = reachPoint;
    }

    public List<ReachPoint> getReachPointList() {
        return reachPointList;
    }

    public void setReachPointList(List<ReachPoint> reachPointList) {
        this.reachPointList = reachPointList;
    }

}
