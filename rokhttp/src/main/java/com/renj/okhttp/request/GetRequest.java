package com.renj.okhttp.request;


import com.renj.okhttp.ROkHttpResponse;

import java.util.Set;

import okhttp3.Request;

/**
 * ======================================================================
 * 作者：Renj
 * <p/>
 * 创建时间：2017-03-12   2:51
 * <p/>
 * 描述：创建一个ROkHttp的GET请求
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
     * @param mROkHttpResponse ROkHttpResponseHandler抽象类的实现类对象
     */
    @Override
    protected <E> void postParams(Request.Builder builder, ROkHttpResponse<E> mROkHttpResponse) {
        if (!mUrl.endsWith("?")) {
            if (!mUrl.endsWith("&")) {
                if (mUrl.contains("?")) {
                    mUrl = mUrl + "&";
                } else {
                    mUrl = mUrl + "?";
                }
            }
        }
        addParams();
    }

    private void addParams() {
        Set<String> keySet = mParams.keySet();
        for (String key : keySet) {
            mUrl = mUrl + key + "=" + mParams.get(key) + "&";
        }
        mUrl = mUrl.substring(0, mUrl.length() - 1);
    }
}
