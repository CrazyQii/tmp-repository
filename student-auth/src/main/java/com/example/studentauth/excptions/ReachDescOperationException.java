package com.example.studentauth.excptions;

public class ReachDescOperationException extends RuntimeException {
    private static final long serialVersionUID = 934821371638527969L;

    //封装业务的异常意义在于看到异常时能认识到和excel相关
    public ReachDescOperationException(String msg) {
        super(msg);
    }
}
