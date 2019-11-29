package com.android.okhttptest.application;

import android.app.Application;

import com.renj.okhttp.ROkHttp;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-03-12   5:43
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ROkHttp.initROkHttp(this);
    }
}
