package com.renj.okhttp;

import okhttp3.Call;
import okhttp3.Request;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-03-13    15:46
 * <p/>
 * 描述：Call实体类：包含Call对象、Request对象、Request的Tag以及请求编号
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public class CallEntity {
    private final long callNo;
    private final Call call;
    private final Request request;
    private final Object tag;

    /**
     * 创建一个CallEntity实体
     */
    CallEntity(long callNo, Call call, Request request, Object tag) {
        this.callNo = callNo;
        this.call = call;
        this.request = request;
        this.tag = tag;
    }

    /**
     * 取消当前请求
     *
     * @return true成功，false失败(请求已经取消)
     */
    boolean cancel() {
        if (!call.isCanceled()) {
            call.cancel();
            RLog.v(toString());
            return true;
        }
        return false;
    }

    public long getCallNo() {
        return callNo;
    }

    public Call getCall() {
        return call;
    }

    public Request getRequest() {
        return request;
    }

    public Object getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return "CallEntity{" +
                "callNo=" + callNo +
                ", call=" + call +
                ", request=" + request +
                ", tag=" + tag +
                '}';
    }
}
