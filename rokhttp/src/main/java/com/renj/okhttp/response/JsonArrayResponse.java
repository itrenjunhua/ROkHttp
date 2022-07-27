package com.renj.okhttp.response;

import com.renj.okhttp.ROkHttpException;
import com.renj.okhttp.ROkHttpResponse;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-03-13    18:00
 * <p/>
 * 描述：将返回结果解析成JsonArray类型数据，继承ROkHttpResponseHandler<T>类，指定泛型为JSONArray
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public abstract class JsonArrayResponse extends ROkHttpResponse<JSONArray> {
    @Override
    public void parseResponse(Call call, Response response) {
        try {
            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                onOkHttpError(call, new ROkHttpException("响应 Response 对象为 null"));
            } else {
                String responseStr = responseBody.string();
                JSONArray jsonArray = new JSONArray(responseStr);
                onParseSucceed(call, jsonArray);
            }
        } catch (IOException e) {
            e.printStackTrace();
            onOkHttpError(call, new ROkHttpException(e));
        } catch (JSONException e) {
            e.printStackTrace();
            onOkHttpError(call, new ROkHttpException(e));
        } catch (Exception e) {
            onOkHttpError(call, new ROkHttpException(e));
        }
    }
}
