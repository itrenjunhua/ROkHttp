package com.renj.okhttp;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-03-12   0:16
 * <p>
 * 描述：ROkHttp异常类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ROkHttpException extends Exception {
    public ROkHttpException() {
        super();
    }

    public ROkHttpException(String message) {
        super(message);
    }

    public ROkHttpException(String message, Throwable cause) {
        super(message, cause);
    }

    public ROkHttpException(Throwable cause) {
        super(cause);
    }
}
