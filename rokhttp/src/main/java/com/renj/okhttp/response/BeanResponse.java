package com.renj.okhttp.response;

import com.google.gson.Gson;
import com.renj.okhttp.ROkHttpException;
import com.renj.okhttp.ROkHttpResponse;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * ======================================================================
 * 作者：Renj
 * <p/>
 * 创建时间：2017-03-13   0:27
 * <p/>
 * 描述：将返回结果解析成Bean类型数据，继承ROkHttpResponseHandler<T>类，需要指定泛型
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public abstract class BeanResponse<T> extends ROkHttpResponse<T> {
    private Class<T> mClazz;

    public BeanResponse() {
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
            throw new NullPointerException("BeanResponse 泛型异常：" + "使用 BeanResponse 时必须指定正确的泛型。");
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
            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                onOkHttpError(call, new ROkHttpException("响应 Response 对象为 null"));
            } else {
                String json = responseBody.string();
                // 调用onSucceed()方法并传递解析结果
                onParseSucceed(call, gson.fromJson(json, mClazz));
            }
        } catch (IOException e) {
            e.printStackTrace();
            // 发生异常，onOkHttpError()方法
            onOkHttpError(call, new ROkHttpException(e));
        } catch (Exception e) {
            onOkHttpError(call, new ROkHttpException(e));
        }
    }
}
