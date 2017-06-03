package com.android.okhttptest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.okhttptest.R;
import com.android.okhttptest.application.Constant;
import com.renj.myokhttp.MyOkHttpUtil;
import com.renj.myokhttp.response.StringResponseHandler;

import okhttp3.Call;

/**
 * POST提交数据
 */
public class PostSubmitActivity extends AppCompatActivity {
    private Button btForm,btString, btJson;
    private TextView stringResult, stringResult2,stringResult3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_submit_data);

        btForm = (Button) findViewById(R.id.bt_form);
        btJson = (Button) findViewById(R.id.bt_json);
        btString = (Button) findViewById(R.id.bt_string);
        stringResult = (TextView) findViewById(R.id.tv_result1);
        stringResult2 = (TextView) findViewById(R.id.tv_result2);
        stringResult3 = (TextView) findViewById(R.id.tv_result3);

        btForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

        btString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitString();
            }
        });

        btJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitJson();
            }
        });
    }

    // 提交表单数据
    private void submitForm() {
        MyOkHttpUtil.postFormRequest()
                .url(Constant.FILE_UP)
                .param("username", "abced")
                .addParam("psw", "123456")
                .addParam("nick", "hehe")
                .tag("aaa")
                .enqueue(new StringResponseHandler() {
                    @Override
                    public void onSucceed(Call call, String result) {
                        stringResult.setText(result);
                    }
                });
        // 取消请求
        //MyOkHttpUtil.cancel("aaa");
    }

    // 提交STRING数据
    private void submitString() {
        String string = "姓名：张三；年龄：22,；性别：男";
        MyOkHttpUtil.postStringRequest()
                .url(Constant.JSON_URL)
                .string(string)
                .enqueue(new StringResponseHandler() {
                    @Override
                    public void onSucceed(Call call, String result) {
                        stringResult2.setText(result);
                    }
                });
        // 获取所有的未取消的请求集合
//        LinkedList<CallEntity> callEntities = MyOkHttpUtil.requestQueue();
//        for (CallEntity callEntity : callEntities) {
//            LogUtil.i(callEntity.toString());
//        }
    }

    // 提交JSON数据
    private void submitJson() {
        String jsonString = "{\n" +
                "    \"age\": 22,\n" +
                "    \"name\": \"zhangsan\",\n" +
                "    \"nick\": \"hehe\",\n" +
                "    \"sex\": \"man\"\n" +
                "}";
        MyOkHttpUtil.postJsonRequest()
                .url(Constant.JSON_URL)
                .json(jsonString)
                .enqueue(new StringResponseHandler() {
                    @Override
                    public void onSucceed(Call call, String result) {
                        stringResult3.setText(result);
                    }
                });
    }
}
