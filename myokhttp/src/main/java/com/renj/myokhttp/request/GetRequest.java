package com.renj.myokhttp.request;


import com.renj.myokhttp.MyOkHttpResponseHandler;

import java.util.Set;

import okhttp3.Request;

/**
 * ======================================================================
 * 作者：Renj
 * <p/>
 * 创建时间：2017-03-12   2:51
 * <p/>
 * 描述：创建一个MyOkHttp的GET请求
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public class GetRequest extends KeyValueRequest<GetRequest> {
    /**
     * 最终拼接参数
     *
     * @param builder                  Request.Builder对象
     * @param mMyOkHttpResponseHandler MyOkHttpResponseHandler抽象类的实现类对象
     */
    @Override
    protected <E> void postParams(Request.Builder builder, MyOkHttpResponseHandler<E> mMyOkHttpResponseHandler) {
        if (mUrl.endsWith("?")) {
            addParams();
        } else {
            if (!mUrl.endsWith("&")) {
                if (mUrl.contains("?")) {
                    mUrl = mUrl + "&";
                } else {
                    mUrl = mUrl + "?";
                }
            }
            addParams();
        }
    }

    private void addParams() {
        Set<String> keySet = mParams.keySet();
        for (String key : keySet) {
            mUrl = mUrl + key + "=" + mParams.get(key) + "&";
        }
        mUrl = mUrl.substring(0, mUrl.length() - 1);
    }
}
