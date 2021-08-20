package com.renj.okhttp.response;

import com.renj.okhttp.ROkHttpException;
import com.renj.okhttp.ROkHttpResponse;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * ======================================================================
 * 作者：Renj
 * <p/>
 * 创建时间：2017-03-13   0:25
 * <p/>
 * 描述：将返回结果解析成byte数组型数据，继承ROkHttpResponseHandler<T>类，指定泛型为byte[]
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public abstract class ByteArrayResponse extends ROkHttpResponse<byte[]> {

    @Override
    public void parseResponse(Call call, Response response) {
        try {
            onParseSucceed(call, response.body().bytes());
        } catch (IOException e) {
            e.printStackTrace();
            onOkHttpError(call, new ROkHttpException(e));
        } catch (Exception e) {
            onOkHttpError(call, new ROkHttpException(e));
        }
    }
}
