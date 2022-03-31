package com.example.studentauth.excptions;

public class ExcelOperationException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = -2182224508020338351L;

    //封装业务的异常意义在于看到异常时能认识到和excel相关
    public ExcelOperationException(String msg) {
        super(msg);//相当于构造函数
    }
}
