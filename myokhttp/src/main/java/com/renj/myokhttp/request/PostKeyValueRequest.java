package com.renj.myokhttp.request;


import com.renj.myokhttp.MyOkHttpResponseHandler;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-03-12   23:59
 * <p>
 * 描述：创建一个MyOkHttp的POST方式提交键值对参数请求
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class PostKeyValueRequest extends KeyValueRequest<PostKeyValueRequest> {

    @Override
    protected <E> void postParams(Request.Builder builder, MyOkHttpResponseHandler<E> mMyOkHttpResponseHandler) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        for (String name : mParams.keySet()) {
            // formBuilder.add(key, mParams.get(name));
            formBuilder.addEncoded(name, mParams.get(name));
        }
        builder.post(formBuilder.build());
    }
}
