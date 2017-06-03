package com.renj.myokhttp.response;


import com.renj.myokhttp.MyOkHttpExecption;
import com.renj.myokhttp.MyOkHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-03-13    18:00
 * <p/>
 * 描述：将返回结果解析成JSONObject类型数据，继承MyOkHttpResponseHandler<T>类，指定泛型为JSONObject
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public class JsonObjectResponseHandler extends MyOkHttpResponseHandler<JSONObject> {

    @Override
    public void parseResponse(Call call, Response response) {
        try {
            String responseStr = response.body().string();
            JSONObject jsonObject = new JSONObject(responseStr);
            onPraseSucceed(call, jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
            onOkHttpError(call, new MyOkHttpExecption(e));
        } catch (JSONException e) {
            e.printStackTrace();
            onOkHttpError(call, new MyOkHttpExecption(e));
        }catch (Exception e){
            onOkHttpError(call,new MyOkHttpExecption(e));
        }
    }
}
