package com.renj.okhttp;

import android.support.annotation.Nullable;

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
 * 描述：ROkHttp请求管理类
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
/*public*/ class ROkHttpRequestManager {
    private ROkHttpRequestManager() {
    }

    /**
     * 将一个请求添加到请求的缓存中
     *
     * @param call
     * @param request
     * @param tag
     */
    static void putRequest(Call call, Request request, Object tag) {
        synchronized (ROkHttpRequestManager.class) {
            CallEntity callEntity = new CallEntity(ROkHttpManager.mCallNo, call, request, tag);
            ROkHttpManager.mCallNo += 1;
            putRequest(callEntity);
        }
    }

    /**
     * 将一个CallEntity实体添加到请求集合中
     *
     * @param callEntity
     */
    private static void putRequest(CallEntity callEntity) {
        ROkHttpManager.mAllCall.add(callEntity);
        while (ROkHttpManager.mAllCall.size() > ROkHttpManager.mRequestCount) {
            ROkHttpManager.mAllCall.removeFirst();
        }
    }

    /**
     * ROkHttpRequestManager类中保存的请求数
     *
     * @param requestCount 请求数
     */
    static void requestCount(int requestCount) {
        ROkHttpManager.mRequestCount = requestCount;
    }

    /**
     * 清空ROkHttpRequestManager类中保存的所有请求
     */
    static void clearRequestManager() {
        ROkHttpManager.mCallNo = 1;
        ROkHttpManager.mAllCall.clear();
    }

    /**
     * 根据请求编号获取一个CallEntity实体
     *
     * @param callNo 请求编号
     * @return
     */
    @Nullable
    @org.jetbrains.annotations.Contract(pure = true)
    static CallEntity getCallEntity(long callNo) {
        if (callNo <= 0) return null;
        for (CallEntity callEntity : ROkHttpManager.mAllCall) {
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
    @Nullable
    @org.jetbrains.annotations.Contract("null -> null")
    static CallEntity getCallEntity(Call call) {
        if (null == call) return null;
        for (CallEntity callEntity : ROkHttpManager.mAllCall) {
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
    static List<CallEntity> getCallEntities(Object tag) {
        List<CallEntity> callEntities = new ArrayList<CallEntity>();
        if (null == tag) {
            for (CallEntity callEntity : ROkHttpManager.mAllCall) {
                if (null == callEntity.tag) callEntities.add(callEntity);
            }
        } else {
            for (CallEntity callEntity : ROkHttpManager.mAllCall) {
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
        for (CallEntity callEntity : ROkHttpManager.mAllCall) {
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
        for (CallEntity callEntity : ROkHttpManager.mAllCall) {
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
        for (CallEntity callEntity : ROkHttpManager.mAllCall) {
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
            for (CallEntity callEntity : ROkHttpManager.mAllCall) {
                if (null == callEntity.tag) cancelCall(callEntity);
            }
        } else {
            for (CallEntity callEntity : ROkHttpManager.mAllCall) {
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
        for (CallEntity callEntity : ROkHttpManager.mAllCall) {
            cancelCall(callEntity);
        }
    }

    /**
     * 获取请求队列，返回所有加入到请求管理中并未取消的请求集合
     *
     * @return 所有加入到请求管理中并未取消的请求集合
     */
    @org.jetbrains.annotations.Contract(pure = true)
    static LinkedList<CallEntity> requestQueue() {
        return ROkHttpManager.mAllCall;
    }

    /**
     * 取消请求方法
     *
     * @param callEntity
     */
    private static void cancelCall(CallEntity callEntity) {
        if (callEntity.cancel()) ROkHttpManager.mAllCall.remove(callEntity);
    }
}
