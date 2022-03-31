
package com.example.studentauth.excptions;

public class AnalysisOperationException extends RuntimeException {
    private static final long serialVersionUID = 933948561638527969L;

    //封装业务的异常意义在于看到异常时能认识到和excel相关
    public AnalysisOperationException(String msg) {
        super(msg);
    }
}
