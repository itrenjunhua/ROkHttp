package com.renj.myokhttp.request;

import android.support.annotation.NonNull;

import com.renj.myokhttp.MyOkHttpException;
import com.renj.myokhttp.MyOkHttpRequest;
import com.renj.myokhttp.MyOkHttpResponseHandler;
import com.renj.myokhttp.body.DownloadResponseBody;
import com.renj.myokhttp.response.DownLoadResponseHandler;

import java.io.File;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-03-17   1:19
 * <p>
 * 描述：创建一个执行文件下载的请求
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class DownloadFileRequest extends MyOkHttpRequest<DownloadFileRequest> {
    /**
     * 文件dir，如果同时设置mFileDir和mFileD，以mFileD为准
     */
    private String mFileDir = "";
    /**
     * 文件dir，如果同时设置mFileDir和mFileD，以mFileD为准
     */
    private File mFileD;
    /**
     * 文件名
     */
    private String mFileName = "";
    /**
     * 文件路径(如果设置该字段则上面2个就不需要)<br/>
     * 如果同时设置mFilePath和mFileP，以mFileP为准
     */
    private String mFilePath = "";
    /**
     * 文件路径(如果设置该字段则上面2个就不需要)<br/>
     * 如果同时设置mFilePath和mFileP，以mFileP为准
     */
    private File mFileP;

    /**
     * 指定下载文件保存目录<br/><br/>
     * <b>注意：<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 如果同时设置fileDir(@NonNull String fileDir)和fileDir(@NonNull File fileD)<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 以fileDir(@NonNull File fileD)设置的值为准</b>
     *
     * @param fileDir 文件保存目录
     * @return
     */
    public DownloadFileRequest fileDir(@NonNull String fileDir) {
        this.mFileDir = fileDir;
        return this;
    }

    /**
     * 指定下载文件保存目录<br/><br/>
     * <b>注意：<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 如果同时设置fileDir(@NonNull String fileDir)和fileDir(@NonNull File fileD)<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 以fileDir(@NonNull File fileD)设置的值为准</b>
     *
     * @param fileD 文件保存目录
     * @return
     */
    public DownloadFileRequest fileDir(@NonNull File fileD) {
        this.mFileD = fileD;
        return this;
    }

    /**
     * 设置保存的文件名
     *
     * @param fileName 保存的文件名
     * @return
     */
    public DownloadFileRequest fileName(@NonNull String fileName) {
        this.mFileName = fileName;
        return this;
    }

    /**
     * 设置下载文件保存路径，如果指定了filePath，那么上面是定的fileDir和fileName失效<br/><br/>
     * <b>注意：<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 如果同时设置filePath(@NonNull String filePath)和filePath(@NonNull File fileP)<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 以filePath(@NonNull File fileP)设置的值为准</b>
     *
     * @param filePath 文件路径
     * @return
     */
    public DownloadFileRequest filePath(@NonNull String filePath) {
        this.mFilePath = filePath;
        return this;
    }

    /**
     * 设置下载文件保存路径，如果指定了filePath，那么上面是定的fileDir和fileName失效<br/><br/>
     * <b>注意：<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 如果同时设置filePath(@NonNull String filePath)和filePath(@NonNull File fileP)<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 以filePath(@NonNull File fileP)设置的值为准</b>
     *
     * @param fileP 文件路径
     * @return
     */
    public DownloadFileRequest filePath(@NonNull File fileP) {
        this.mFileP = fileP;
        return this;
    }

    /**
     * 下载文件不需要拼接参数，这里执行对目标目录进行检查
     *
     * @param builder                  Request.Builder对象
     * @param mMyOkHttpResponseHandler MyOkHttpResponseHandler抽象类的实现类对象
     * @param <E>
     */
    @Override
    protected <E> void putParams(Request.Builder builder, MyOkHttpResponseHandler<E> mMyOkHttpResponseHandler) {
        if (mMyOkHttpResponseHandler instanceof DownLoadResponseHandler) {
            // 是否为下载Handler
            DownLoadResponseHandler handler = (DownLoadResponseHandler) mMyOkHttpResponseHandler;
            // 对目标目录进行检查
            try {
                handler.checkFilePath(mFilePath, mFileP, mFileDir, mFileD, mFileName);
            } catch (MyOkHttpException myOkHttpException) {
                myOkHttpException.printStackTrace();
                handler.onFilePathException(myOkHttpException);
            }
        }
    }

    /**
     * 从原 OkHttpClient 构建一个带拦截器的 OkHttpClient
     *
     * @param mMyOkHttpResponseHandler
     * @param <E>
     * @return
     */
    @Override
    protected <E> OkHttpClient getOkHttpClient(final MyOkHttpResponseHandler<E> mMyOkHttpResponseHandler) {
        // 克隆，增加拦截器
        return mOkHttpClient.newBuilder().addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                // 拦截
                Response proceedResponse = chain.proceed(chain.request());
                // 包装响应体并返回
                return proceedResponse.newBuilder().body(new DownloadResponseBody(proceedResponse.body(), new DownloadProgressListener() {
                    @Override
                    public void onProgress(final long completeLength, final long totalLength, final boolean isFinish) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mMyOkHttpResponseHandler.onProgress(completeLength, totalLength, isFinish);
                            }
                        });
                    }
                })).build();
            }
        }).build();
    }

    /**
     * 进度监听接口
     */
    public interface DownloadProgressListener {
        void onProgress(long completeLength, long totalLength, boolean isFinish);
    }
}
