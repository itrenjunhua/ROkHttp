package com.android.okhttptest.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.okhttptest.R;
import com.android.okhttptest.application.Constant;
import com.renj.myokhttp.MyOkHttpUtil;
import com.renj.myokhttp.response.StringResponseHandler;

import java.io.File;

import okhttp3.Call;

/**
 * 带进度条上传文件
 */
public class UpFileActivity extends AppCompatActivity {
    private Button btSingle, btMultiple;
    private TextView stringResult, stringResult2;
    private ProgressBar singleBar, MultipleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_file);

        btSingle = (Button) findViewById(R.id.bt_single);
        btMultiple = (Button) findViewById(R.id.bt_multiple);
        stringResult = (TextView) findViewById(R.id.tv_result1);
        stringResult2 = (TextView) findViewById(R.id.tv_result2);
        singleBar = (ProgressBar) findViewById(R.id.single_progressBar);
        MultipleBar = (ProgressBar) findViewById(R.id.multiple_progressBar);

        btSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleFile();
            }
        });

        btMultiple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multipleFile();
            }
        });
    }

    // 单个文件
    private void singleFile() {
        File directory = Environment.getExternalStorageDirectory();
        File file = new File(directory,"Pictures/upload1.jpg");
        MyOkHttpUtil.upLoadFileRequest()
                .url(Constant.FILE_UP)
                .file("filename",file)
                .enqueue(new StringResponseHandler(){
                    @Override
                    public void onSucceed(Call call, String result) {
                        stringResult.setText(result);
                    }

                    @Override
                    public void onProgress(long completeLength, long totalLength, boolean isFinish) {
                        // 更新进度
                        singleBar.setMax((int) (totalLength / 100));
                        singleBar.setProgress((int)(completeLength / 100));

                        if(isFinish){
                            Toast.makeText(UpFileActivity.this, "单个文件上传完成", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // 多个文件和参数
    private void multipleFile() {
        File directory = Environment.getExternalStorageDirectory();
        File file1 = new File(directory,"Pictures/upload1.jpg");
        File file2 = new File(directory,"Pictures/upload2.jpg");
        MyOkHttpUtil.upLoadFileRequest()
                .url(Constant.FILE_UP)
                .param("username", "abced")
                .param("psw", "123456")
                .file("filename", file1)
                .addFile("filename", file2)
                .enqueue(new StringResponseHandler() {
                    @Override
                    public void onSucceed(Call call, String result) {
                        stringResult2.setText(result);
                    }

                    @Override
                    public void onProgress(long completeLength, long totalLength, boolean isFinish) {
                        MultipleBar.setMax((int) (totalLength / 100));
                        MultipleBar.setProgress((int) (completeLength / 100));

                        if (isFinish) {
                            Toast.makeText(UpFileActivity.this, "多个文件上传完成", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
