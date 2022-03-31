package com.example.studentauth.excptions;

public class ReachPointOperationException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1182563719599527969L;

    //封装业务的异常意义在于看到异常时能认识到和excel相关
    public ReachPointOperationException(String msg) {
        super(msg);
    }
}
