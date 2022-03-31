package com.example.studentauth.excptions;

public class GradeSubjectOperationException extends RuntimeException {
    private static final long serialVersionUID = 2597663375199648969L;

    //封装业务的异常意义在于看到异常时能认识到和excel相关
    public GradeSubjectOperationException(String msg) {
        super(msg);
    }
}
