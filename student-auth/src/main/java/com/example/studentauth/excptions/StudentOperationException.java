package com.example.studentauth.excptions;

public class StudentOperationException extends RuntimeException {

    private static final long serialVersionUID = 1345563717589527539L;

    //封装业务的异常意义在于看到异常时能认识到和excel相关
    public StudentOperationException(String msg) {
        super(msg);
    }
}
