package com.renj.myokhttp;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-03-12   0:16
 * <p>
 * 描述：MyOkHttp异常类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class MyOkHttpExecption extends Exception {
    public MyOkHttpExecption() {
        super();
    }

    public MyOkHttpExecption(String message) {
        super(message);
    }

    public MyOkHttpExecption(String message, Throwable cause) {
        super(message, cause);
    }

    public MyOkHttpExecption(Throwable cause) {
        super(cause);
    }
}
