package com.renj.okhttp.request;

import android.support.annotation.NonNull;

import com.renj.okhttp.ROkHttpRequest;
import com.renj.okhttp.ROkHttpResponse;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * ======================================================================
 * 作者：Renj
 * <p/>
 * 创建时间：2017-03-13   23:01
 * <p/>
 * 描述：参数可以以键值对形式表现的请求超类
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public abstract class KeyValueRequest<T extends KeyValueRequest> extends ROkHttpRequest<T> {
    protected Map<String, String> mParams;

    /**
     * 添加一个键值对参数
     *
     * @param key   键名
     * @param value 值
     * @return
     */
    public T param(@NonNull String key, @NonNull String value) {
        addParam(key, value);
        return (T) this;
    }

    /**
     * 新增键值对参数
     *
     * @param key   键名
     * @param value 值
     * @return
     */
    private T addParam(@NonNull String key, @NonNull String value) {
        if (this.mParams == null) this.mParams = new HashMap<String, String>();
        mParams.put(key, value);
        return (T) this;
    }

    /**
     * 增加一个参数集合
     *
     * @param params 参数集合
     * @return
     */
    public T params(Map<String, String> params) {
        if (params != null && !params.isEmpty()) {
            if (this.mParams == null) this.mParams = new LinkedHashMap<String, String>();
            this.mParams.putAll(params);
        }
        return (T) this;
    }

    /**
     * 实现父类方法，向请求中添加不同类型参数
     *
     * @param builder                  Request.Builder对象
     * @param mROkHttpResponse ROkHttpResponseHandler抽象类的实现类对象
     */
    @Override
    protected <E> void putParams(Request.Builder builder, ROkHttpResponse<E> mROkHttpResponse) {
        if (mParams == null || mParams.isEmpty()) return;
        postParams(builder, mROkHttpResponse);
    }

    /**
     * 抽象方法，拼接参数<br/>
     * 即使参数能以键值对的形式表示，但提交参数的方式不同，进行不同的拼接，所以由具体子类实现
     *
     * @param builder                  Request.Builder对象
     * @param mROkHttpResponse
     */
    protected abstract <E> void postParams(Request.Builder builder, ROkHttpResponse<E> mROkHttpResponse);
}
