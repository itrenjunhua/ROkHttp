package com.renj.myokhttp.response;

import com.renj.myokhttp.MyOkHttpException;
import com.renj.myokhttp.MyOkHttpResponseHandler;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * ======================================================================
 * 作者：Renj
 * <p/>
 * 创建时间：2017-03-13   0:25
 * <p/>
 * 描述：将返回结果解析成String类型数据，继承MyOkHttpResponseHandler<T>类，指定泛型为String
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public abstract class StringResponseHandler extends MyOkHttpResponseHandler<String> {

    @Override
    public void parseResponse(Call call, Response response) {
        try {
            onPraseSucceed(call, response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
            onOkHttpError(call, new MyOkHttpException(e));
        }catch (Exception e){
            onOkHttpError(call,new MyOkHttpException(e));
        }
    }
}
