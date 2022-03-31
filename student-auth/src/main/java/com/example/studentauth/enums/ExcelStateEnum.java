package com.example.studentauth.enums;

public enum ExcelStateEnum {
    CHECK(0, "添加成功"), OFFLINE(-1, "添加失败"), SUCCESS(1, "成功结束"), PASS(2, "通过"), INNER_ERROR(-1001, "内部系统错误"), NULL_SHOP(-1003, "Excel信息为空"), NULL_AREA(-1004, "Area信息为空"), NULL_Person(-1005, "个人信息为空");
    private int state;
    private String stateInfo;

    private ExcelStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    /**
     * 依据传入的static返回相应enum值
     */
    public static ExcelStateEnum stateOf(int state) {
        for (ExcelStateEnum stateEnum : values()) {
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
