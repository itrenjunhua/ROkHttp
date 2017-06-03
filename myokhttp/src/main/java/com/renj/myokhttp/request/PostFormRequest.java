package com.renj.myokhttp.request;


import com.renj.myokhttp.MyOkHttpResponseHandler;

import okhttp3.MultipartBody;
import okhttp3.Request;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-03-13   21:39
 * <p>
 * 描述：创建一个MyOkHttp的POST方式提交表单形式数据请求
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class PostFormRequest extends KeyValueRequest<PostFormRequest> {
    @Override
    protected <E> void postParams(Request.Builder builder, MyOkHttpResponseHandler<E> mMyOkHttpResponseHandler) {
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        for (String name : mParams.keySet()) {
            multipartBuilder.addFormDataPart(name, mParams.get(name));
        }
        builder.post(multipartBuilder.build());
    }
}
