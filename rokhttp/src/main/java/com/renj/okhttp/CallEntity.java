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
    public long callNo;
    public Call call;
    public Request request;
    public Object tag;

    /**
     * 创建一个CallEntity实体
     *
     * @param callNo
     * @param call
     * @param request
     * @param tag
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
