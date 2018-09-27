package com.renj.myokhttp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.LinkedList;

import okhttp3.OkHttpClient;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-03-13    13:56
 * <p/>
 * 描述：<b>MyOkHttp初始化类，使用 MyOkHttp 工具时需要在 Application 类中调用该类的初始化方法</b>
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
/*public*/ class MyOkHttp {
    /**
     * 绑定主线程Looper对象的Handler
     */
    final static Handler mHandler = new Handler(Looper.getMainLooper());
    /**
     * 保存所有CallEntity实体的集合
     */
    final static LinkedList<CallEntity> mAllCall = new LinkedList<CallEntity>();
    /**
     * 全局OkHttpClient对象
     */
    static OkHttpClient mOkHttpClient;
    /**
     * Call的编号
     */
    static long mCallNo = 1;
    /**
     * 缓存的请求数，默认50个
     */
    static int mRequestCount = 50;
    static Context mContext;

    private MyOkHttp() {
    }

    /**
     * 初始化初始化MyOkHttpUtil
     *
     * @param context 上下文
     */
    @org.jetbrains.annotations.Contract("null -> fail")
    static void initMyOkHttpUtil(@NonNull Context context) {
        initMyOkHttpUtil(context, null);
    }

    /**
     * 初始化MyOkHttpUtil，使用自定义的OkHttpClien
     *
     * @param context      上下文
     * @param okHttpClient 自定义的OkHttpClient
     */
    @org.jetbrains.annotations.Contract("null, _ -> fail")
    static void initMyOkHttpUtil(@NonNull Context context, @Nullable OkHttpClient okHttpClient) {
        if (context == null) throw new NullPointerException("初始化 MyOkHttp 失败：Context 不能为 null 。");
        if (MyOkHttp.mOkHttpClient == null) {
            synchronized (MyOkHttpUtil.class) {
                if (MyOkHttp.mOkHttpClient == null) {
                    MyOkHttp.mContext = context;
                    if (okHttpClient != null) {
                        MyOkHttp.mOkHttpClient = okHttpClient;
                    } else {
                        OkHttpClient.Builder builder = new OkHttpClient.Builder();
                        MyOkHttp.mOkHttpClient = builder.build();
                    }
                }
            }
        }
    }
}
