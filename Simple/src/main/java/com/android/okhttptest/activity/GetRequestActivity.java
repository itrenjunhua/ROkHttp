package com.android.okhttptest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.okhttptest.R;
import com.android.okhttptest.application.Constant;
import com.android.okhttptest.bean.WeatherBean;
import com.renj.okhttp.ROkHttp;
import com.renj.okhttp.ROkHttpException;
import com.renj.okhttp.response.BeanResponse;
import com.renj.okhttp.response.StringResponse;

import okhttp3.Call;

/**
 * GET获取数据
 */
public class GetRequestActivity extends AppCompatActivity {
    private Button btString, btBean;
    private TextView stringResult, beanResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_request);

        btString = (Button) findViewById(R.id.bt_string);
        btBean = (Button) findViewById(R.id.bt_bean);
        stringResult = (TextView) findViewById(R.id.tv_result1);
        beanResult = (TextView) findViewById(R.id.tv_result2);

        btString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStringData();
            }
        });

        btBean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBeanData();
            }
        });

    }

    // 获取STRING结果类型数据
    private void getStringData() {
        ROkHttp.getInstance().getRequest()
                .url(Constant.ALL_URL)
                .enqueue(new StringResponse() {
                    @Override
                    public void onSucceed(Call call, String result) {
                        stringResult.setText(result);
                    }

                    @Override
                    public void onNetWork() {
                        // 没有网络连接时回调
                        Toast.makeText(GetRequestActivity.this, "无网络连接", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Call call, ROkHttpException error) {
                        // 处理错误
                        Log.e("GetRequestActivity",error + "");
                    }
                });
    }

    // 获取Bean结果类型数据
    private void getBeanData() {
        ROkHttp.getInstance().getRequest()
                .url(Constant.BASE_URL)
                .param("cityCode", "101040100") // 不使用完整连接，添加参数
                .param("weatherType", "1")
                .enqueue(new BeanResponse<WeatherBean>() {
                    @Override
                    public void onSucceed(Call call, WeatherBean result) {
                        WeatherBean.WeatherinfoEntity weatherinfo = result.getWeatherinfo();
                        beanResult.setText(weatherinfo.getCity() + weatherinfo.getWD());
                    }
                });
    }
}
