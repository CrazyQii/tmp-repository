package com.example.studentauth.enums;

public enum StatusCode {
    SUCCESS(1, "添加成功"), Fail(-1, "失败"), Invalid_Param(1001, "无效的参数"), System_Error(1002, "系统错误"), WorkBook_Version_Invalid(1003, "excel版本号不合法");

    private Integer code;
    private String msg;

    private StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 依据传入的static返回相应enum值
     */
    public static StatusCode stateOf(int state) {
        for (StatusCode stateEnum : values()) {
            if (stateEnum.getCode() == state) {
                return stateEnum;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}	
