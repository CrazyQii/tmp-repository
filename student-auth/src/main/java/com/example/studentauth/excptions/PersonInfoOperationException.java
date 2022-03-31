package com.example.studentauth.excptions;

public class PersonInfoOperationException extends RuntimeException {
    private static final long serialVersionUID = 118316497299527969L;

    //封装业务的异常意义在于看到异常时能认识到和excel相关
    public PersonInfoOperationException(String msg) {
        super(msg);
    }
}
