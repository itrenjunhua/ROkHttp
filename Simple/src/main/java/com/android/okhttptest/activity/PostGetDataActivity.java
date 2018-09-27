package com.android.okhttptest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.okhttptest.R;
import com.android.okhttptest.application.Constant;
import com.android.okhttptest.bean.WeatherBean;
import com.renj.myokhttp.MyOkHttpUtil;
import com.renj.myokhttp.response.BeanResponseHandler;
import com.renj.myokhttp.response.JsonObjectResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * POST获取数据
 */
public class PostGetDataActivity extends AppCompatActivity {
    private Button btJson, btBean;
    private TextView objResult, beanResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_get_data);

        btJson = (Button) findViewById(R.id.bt_json);
        btBean = (Button) findViewById(R.id.bt_bean);
        objResult = (TextView) findViewById(R.id.tv_result1);
        beanResult = (TextView) findViewById(R.id.tv_result2);

        btJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonObject();
            }
        });

        btBean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beanData();
            }
        });
    }

    // 获取jsonObject结果类型数据
    private void jsonObject() {
        MyOkHttpUtil.postKeyValueRequest()
                .url(Constant.ALL_URL)
                .enqueue(new JsonObjectResponseHandler() {
                    @Override
                    public void onSucceed(Call call, JSONObject result) {
                        try {
                            objResult.setText(result.getJSONObject("weatherinfo").getString("city"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("PostGetDataActivity", e + "");
                        }
                    }
                });
    }

    // post方式提交键值对参数获取Bean结果类型数据
    private void beanData() {
        MyOkHttpUtil.postKeyValueRequest().url(Constant.BASE_URL)
                .param("cityCode", "101040100") // 不使用完整连接，添加参数
                .param("weatherType", "1")
                .enqueue(new BeanResponseHandler<WeatherBean>() {
                    @Override
                    public void onSucceed(Call call, WeatherBean result) {
                        WeatherBean.WeatherinfoEntity weatherinfo = result.getWeatherinfo();
                        beanResult.setText(weatherinfo.getCity() + weatherinfo.getWD());
                    }
                });
    }
}
