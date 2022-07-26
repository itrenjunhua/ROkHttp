package com.renj.okhttp.request;

import android.support.annotation.NonNull;

import com.renj.okhttp.ROkHttpException;
import com.renj.okhttp.ROkHttpRequest;
import com.renj.okhttp.ROkHttpResponse;
import com.renj.okhttp.body.DownloadResponseBody;
import com.renj.okhttp.response.DownLoadResponse;

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
public class DownloadFileRequest extends ROkHttpRequest<DownloadFileRequest> {
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

    public DownloadFileRequest() {
        super();
    }

    public DownloadFileRequest(OkHttpClient okHttpClient) {
        super(okHttpClient);
    }

    /**
     * 指定下载文件保存目录<br/><br/>
     * <b>注意：<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 如果同时设置fileDir(@NonNull String fileDir)和fileDir(@NonNull File fileD)<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 以fileDir(@NonNull File fileD)设置的值为准</b>
     *
     * @param fileDir 文件保存目录
     * @return 本身对象，方便链式调用
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
     * @return 本身对象，方便链式调用
     */
    public DownloadFileRequest fileDir(@NonNull File fileD) {
        this.mFileD = fileD;
        return this;
    }

    /**
     * 设置保存的文件名
     *
     * @param fileName 保存的文件名
     * @return 本身对象，方便链式调用
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
     * @return 本身对象，方便链式调用
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
     * @return 本身对象，方便链式调用
     */
    public DownloadFileRequest filePath(@NonNull File fileP) {
        this.mFileP = fileP;
        return this;
    }

    /**
     * 下载文件不需要拼接参数，这里执行对目标目录进行检查
     *
     * @param builder          Request.Builder对象
     * @param mROkHttpResponse ROkHttpResponseHandler抽象类的实现类对象
     * @param <E>              泛型
     */
    @Override
    protected <E> void putParams(Request.Builder builder, ROkHttpResponse<E> mROkHttpResponse) {
        if (mROkHttpResponse instanceof DownLoadResponse) {
            // 是否为下载Handler
            DownLoadResponse handler = (DownLoadResponse) mROkHttpResponse;
            // 对目标目录进行检查
            try {
                handler.checkFilePath(mFilePath, mFileP, mFileDir, mFileD, mFileName);
            } catch (ROkHttpException rOkHttpException) {
                rOkHttpException.printStackTrace();
                handler.onFilePathException(rOkHttpException);
            }
        }
    }

    /**
     * 从原 OkHttpClient 构建一个带拦截器的 OkHttpClient
     *
     * @param mROkHttpResponse 原OkHttpClient
     * @param <E>              泛型
     * @return 本身对象，方便链式调用
     */
    @Override
    protected <E> OkHttpClient getOkHttpClient(final ROkHttpResponse<E> mROkHttpResponse) {
        // 克隆，增加拦截器
        return mOkHttpClient.newBuilder().addNetworkInterceptor(chain -> {
            // 拦截
            Response proceedResponse = chain.proceed(chain.request());
            // 包装响应体并返回
            return proceedResponse.newBuilder().body(new DownloadResponseBody(proceedResponse.body(),
                            (completeLength, totalLength, isFinish) -> mHandler.post(
                                    () -> mROkHttpResponse.onProgress(completeLength, totalLength, isFinish))
                    )
            ).build();
        }).build();
    }

    /**
     * 进度监听接口
     */
    public interface DownloadProgressListener {
        void onProgress(long completeLength, long totalLength, boolean isFinish);
    }
}
