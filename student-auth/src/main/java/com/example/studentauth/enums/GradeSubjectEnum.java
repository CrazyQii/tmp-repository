package com.example.studentauth.enums;

public enum GradeSubjectEnum {
    SUCCESS(1, "添加成功"), INNER_ERROR(-1001, "操作失败"), EMPTY_LIST(-1002, "添加数少于1");
    private int state;
    private String stateInfo;

    private GradeSubjectEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    /**
     * 依据传入的static返回相应enum值
     */
    public static GradeSubjectEnum stateOf(int state) {
        for (GradeSubjectEnum stateEnum : values()) {
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
