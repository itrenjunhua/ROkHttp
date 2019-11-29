package com.renj.okhttp.request;

import android.support.annotation.NonNull;

import com.renj.okhttp.ROkHttpRequest;
import com.renj.okhttp.ROkHttpResponse;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-03-13   22:29
 * <p>
 * 描述：创建一个ROkHttp的POST方式提交JSON类型的数据请求
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class PostJsonRequest extends ROkHttpRequest<PostJsonRequest> {
    private final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private String mJson;

    public PostJsonRequest json(@NonNull String json) {
        this.mJson = json;
        return this;
    }

    @Override
    protected <E> void putParams(Request.Builder builder, ROkHttpResponse<E> mROkHttpResponse) {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, mJson);
        builder.post(requestBody);
    }
}
