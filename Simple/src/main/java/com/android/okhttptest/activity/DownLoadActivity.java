package com.android.okhttptest.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.okhttptest.R;
import com.android.okhttptest.application.Constant;
import com.renj.myokhttp.MyOkHttpException;
import com.renj.myokhttp.MyOkHttpUtil;
import com.renj.myokhttp.response.DownLoadResponseHandler;

import java.io.File;

import okhttp3.Call;

/**
 * 带进度条文件下载
 */
public class DownLoadActivity extends AppCompatActivity {
    private Button btDownFile;
    private TextView stringResult;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);

        btDownFile = (Button) findViewById(R.id.bt_downfile);
        stringResult = (TextView) findViewById(R.id.tv_result);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btDownFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downFile();
            }
        });
    }

    private void downFile() {
        File directory = Environment.getExternalStorageDirectory();
        File file = new File(directory,"Pictures/downfile.png");
        MyOkHttpUtil.downloadFileRequest()
                .url(Constant.FILE_DOWN)
//                .fileDir(new File(directory,"Pictures"))
//                .fileName("downfile.png")
                // 设置了filePath()，那么设置fileDir()和fileName()的值无效
                .filePath(file)
                .enqueue(new DownLoadResponseHandler() {
                    @Override
                    public void onSucceed(Call call, String result) {
                        stringResult.setText("文件保存路径：" + result);
                    }

                    @Override
                    public void onProgress(long completeLength, long totalLength, boolean isFinish) {
                        // 更新进度
                        progressBar.setMax((int) (totalLength / 100));
                        progressBar.setProgress((int)(completeLength / 100));

                        if(isFinish){
                            Toast.makeText(DownLoadActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFilePathException(MyOkHttpException myOkHttpException) {
                        // 指定的文件保存路径有问题
                        Log.e("DownLoadActivity", myOkHttpException + "");
                    }
                });
    }
}
