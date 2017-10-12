package com.renj.myokhttp.response;

import com.google.gson.Gson;
import com.renj.myokhttp.MyOkHttpExecption;
import com.renj.myokhttp.MyOkHttpResponseHandler;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

/**
 * ======================================================================
 * 作者：Renj
 * <p/>
 * 创建时间：2017-03-13   0:27
 * <p/>
 * 描述：将返回结果解析成Bean类型数据，继承MyOkHttpResponseHandler<T>类，需要指定泛型
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public abstract class BeanResponseHandler<T> extends MyOkHttpResponseHandler<T> {
    private Class<T> mClazz;

    public BeanResponseHandler() {
        super();
        // 通过反射获取泛型的Class
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            try {
                mClazz = (Class<T>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mClazz == null)
            throw new NullPointerException("BeanResponseHandler 泛型异常：" + "使用 BeanResponseHandler 时必须指定正确的泛型。");
    }

    /**
     * 重写方法，解析成对应的数据
     *
     * @param call     Call对象
     * @param response 服务器的响应结果
     */
    @Override
    protected void parseResponse(Call call, Response response) {
        Gson gson = new Gson();
        try {
            String json = response.body().string();
            // 调用onSucceed()方法并传递解析结果
            onPraseSucceed(call, gson.fromJson(json, mClazz));
        } catch (IOException e) {
            e.printStackTrace();
            // 发生异常，onOkHttpError()方法
            onOkHttpError(call, new MyOkHttpExecption(e));
        } catch (Exception e) {
            onOkHttpError(call, new MyOkHttpExecption(e));
        }
    }
}
