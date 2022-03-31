package com.example.studentauth.excptions;

public class ClassGradeOperationException extends RuntimeException {
    private static final long serialVersionUID = 4545763717953525739L;

    //封装业务的异常意义在于看到异常时能认识到和excel相关
    public ClassGradeOperationException(String msg) {
        super(msg);
    }
}
