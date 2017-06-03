package com.android.okhttptest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.okhttptest.activity.DownLoadActivity;
import com.android.okhttptest.activity.GetRequestActivity;
import com.android.okhttptest.activity.PostGetDataActivity;
import com.android.okhttptest.activity.PostSubmitActivity;
import com.android.okhttptest.activity.UpFileActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button get, post, post2, upfile, downfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        get = (Button) findViewById(R.id.bt_get);
        post = (Button) findViewById(R.id.bt_post);
        post2 = (Button) findViewById(R.id.bt_post2);
        upfile = (Button) findViewById(R.id.bt_upfile);
        downfile = (Button) findViewById(R.id.bt_downfile);

        get.setOnClickListener(this);
        post.setOnClickListener(this);
        post2.setOnClickListener(this);
        upfile.setOnClickListener(this);
        downfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int vId = v.getId();
        switch (vId) {
            case R.id.bt_get: // get方式获取数据
                intent = new Intent(this, GetRequestActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_post: // post方式获取数据
                intent = new Intent(this, PostGetDataActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_post2: // post方式提交数据
                intent = new Intent(this, PostSubmitActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_upfile: // 上传文件
                intent = new Intent(this, UpFileActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_downfile: // 下载文件
                intent = new Intent(this, DownLoadActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
