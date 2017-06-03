package com.renj.myokhttp.request;

import android.support.annotation.NonNull;

import com.renj.myokhttp.MyOkHttpRequest;
import com.renj.myokhttp.MyOkHttpResponseHandler;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-03-13   22:29
 * <p>
 * 描述：创建一个MyOkHttp的POST方式提交String类型的数据请求
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class PostStringRequest extends MyOkHttpRequest<PostStringRequest> {
    private final MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/plain; charset=utf-8");
    private String mString;

    public PostStringRequest string(@NonNull String string) {
        this.mString = string;
        return this;
    }

    @Override
    protected <E> void putParams(Request.Builder builder, MyOkHttpResponseHandler<E> mMyOkHttpResponseHandler) {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_TEXT, mString);
        builder.post(requestBody);
    }
}
