package com.renj.myokhttp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-03-13    15:51
 * <p/>
 * 描述：MyOkHttp请求管理类
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
/*public*/ class MyOkHttpRequestManager {
    private MyOkHttpRequestManager() {
    }

    /**
     * 将一个请求添加到请求的缓存中
     *
     * @param call
     * @param request
     * @param tag
     */
    static void putRequest(Call call, Request request, Object tag) {
        synchronized (MyOkHttpRequestManager.class) {
            CallEntity callEntity = new CallEntity(MyOkHttp.mCallNo, call, request, tag);
            MyOkHttp.mCallNo += 1;
            putRequest(callEntity);
        }
    }

    /**
     * 将一个CallEntity实体添加到请求集合中
     *
     * @param callEntity
     */
    private static void putRequest(CallEntity callEntity) {
        MyOkHttp.mAllCall.add(callEntity);
        while (MyOkHttp.mAllCall.size() > MyOkHttp.mRequestCount) {
            MyOkHttp.mAllCall.removeFirst();
        }
    }

    /**
     * MyOkHttpRequestManager类中保存的请求数
     *
     * @param requestCount 请求数
     */
    static void requestCount(int requestCount) {
        MyOkHttp.mRequestCount = requestCount;
    }

    /**
     * 清空MyOkHttpRequestManager类中保存的所有请求
     */
    static void clearRequestManager() {
        MyOkHttp.mCallNo = 1;
        MyOkHttp.mAllCall.clear();
    }

    /**
     * 根据请求编号获取一个CallEntity实体
     *
     * @param callNo 请求编号
     * @return
     */
    static CallEntity getCallEntity(long callNo) {
        if (callNo <= 0) return null;
        for (CallEntity callEntity : MyOkHttp.mAllCall) {
            if (callNo == callEntity.callNo) return callEntity;
        }
        return null;
    }

    /**
     * 根据Call对象获取一个CallEntity实体
     *
     * @param call Call对象
     * @return
     */
    static CallEntity getCallEntity(Call call) {
        if (null == call) return null;
        for (CallEntity callEntity : MyOkHttp.mAllCall) {
            if (call.equals(callEntity.call)) return callEntity;
        }
        return null;
    }

    /**
     * 根据Request对象的Tag获取一个CallEntity实体集合(相同的Tag)
     *
     * @param tag Request对象的Tag
     * @return
     */
    static List<CallEntity> getCallEntitys(Object tag) {
        List<CallEntity> callEntities = new ArrayList<CallEntity>();
        if (null == tag) {
            for (CallEntity callEntity : MyOkHttp.mAllCall) {
                if (null == callEntity.tag) callEntities.add(callEntity);
            }
        } else {
            for (CallEntity callEntity : MyOkHttp.mAllCall) {
                if (tag.equals(callEntity.tag)) callEntities.add(callEntity);
            }
        }
        return callEntities;
    }

    /**
     * 根据请求编号取消请求
     *
     * @param callNo 请求编号
     */
    static void cancel(long callNo) {
        if (callNo <= 0) return;
        for (CallEntity callEntity : MyOkHttp.mAllCall) {
            if (callNo == callEntity.callNo) cancelCall(callEntity);
        }
    }

    /**
     * 根据请Call对象取消请求
     *
     * @param call Call对象
     */
    static void cancel(Call call) {
        if (null == call) return;
        for (CallEntity callEntity : MyOkHttp.mAllCall) {
            if (call.equals(callEntity.call)) cancelCall(callEntity);
        }
    }

    /**
     * 根据请Request对象取消请求
     *
     * @param request Request对象
     */
    static void cancel(Request request) {
        if (null == request) return;
        for (CallEntity callEntity : MyOkHttp.mAllCall) {
            if (request.equals(callEntity.request)) cancelCall(callEntity);
        }
    }

    /**
     * 根据请Request对象的Tag取消相同Tag的请求
     *
     * @param tag Request对象的Tag
     */
    static void cancel(Object tag) {
        if (null == tag) {
            for (CallEntity callEntity : MyOkHttp.mAllCall) {
                if (null == callEntity.tag) cancelCall(callEntity);
            }
        } else {
            for (CallEntity callEntity : MyOkHttp.mAllCall) {
                if (tag.equals(callEntity.tag)) cancelCall(callEntity);
            }
        }
    }

    /**
     * 根据CallEntity对象取消一个请求
     *
     * @param callEntity CallEntity对象
     */
    static void cancel(CallEntity callEntity) {
        if (null == callEntity) return;
        cancelCall(callEntity);
    }

    /**
     * 取消所有未取消并且当前没有执行的请求
     */
    static void cancelAll() {
        for (CallEntity callEntity : MyOkHttp.mAllCall) {
            cancelCall(callEntity);
        }
    }

    /**
     * 获取请求队列，返回所有加入到请求管理中并未取消的请求集合
     *
     * @return 所有加入到请求管理中并未取消的请求集合
     */
    static LinkedList<CallEntity> requestQueue() {
        return MyOkHttp.mAllCall;
    }

    /**
     * 取消请求方法
     *
     * @param callEntity
     */
    private static void cancelCall(CallEntity callEntity) {
        if (callEntity.cancel()) MyOkHttp.mAllCall.remove(callEntity);
    }
}
