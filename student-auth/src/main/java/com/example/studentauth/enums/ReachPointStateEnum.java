package com.example.studentauth.enums;

public enum ReachPointStateEnum {
    SUCCESS(1, "添加成功"), INNER_ERROR(-1001, "操作失败"), EMPTY_LIST(-1002, "添加数少于1");
    private int state;
    private String stateInfo;

    private ReachPointStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    /**
     * 依据传入的static返回相应enum值
     */
    public static ReachPointStateEnum stateOf(int state) {
        for (ReachPointStateEnum stateEnum : values()) {
            if (stateEnum.getState() == state) {
                return stateEnum;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
