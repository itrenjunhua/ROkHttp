package com.renj.okhttp;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.renj.okhttp.request.DownloadFileRequest;
import com.renj.okhttp.request.GetRequest;
import com.renj.okhttp.request.PostByteArrayRequest;
import com.renj.okhttp.request.PostFormRequest;
import com.renj.okhttp.request.PostJsonRequest;
import com.renj.okhttp.request.PostKeyValueRequest;
import com.renj.okhttp.request.PostStringRequest;
import com.renj.okhttp.request.UploadFileRequest;

import java.util.LinkedList;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-03-13    13:56
 * <p/>
 * 描述：<b>ROkHttp初始化类，使用 ROkHttpManager 工具时需要在 Application 类中调用该类的初始化方法</b>
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
/*public*/ class ROkHttpManager {
    /**
     * 绑定主线程Looper对象的Handler
     */
    final static Handler mHandler = new Handler(Looper.getMainLooper());
    /**
     * 保存所有CallEntity实体的集合
     */
    final static LinkedList<CallEntity> mAllCall = new LinkedList<>();
    /**
     * 全局OkHttpClient对象
     */
    static volatile OkHttpClient mOkHttpClient;
    /**
     * Call的编号
     */
    static long mCallNo = 1;
    /**
     * 缓存的请求数，默认50个
     */
    static int mRequestCount = 50;
    static Application mContext;

    private ROkHttpManager() {
    }

    /**
     * 初始化初始化ROkHttpUtil
     *
     * @param context 上下文
     */
    static void initROkHttp(@NonNull Application context) {
        initROkHttp(context, null);
    }

    /**
     * 初始化ROkHttpUtil，使用自定义的OkHttpClient
     *
     * @param context      上下文
     * @param okHttpClient 自定义的OkHttpClient
     */
    static void initROkHttp(@NonNull Application context, @Nullable OkHttpClient okHttpClient) {
        if (Objects.isNull(context))
            throw new NullPointerException("初始化 ROkHttpManager 失败：Context 不能为 null 。");
        if (ROkHttpManager.mOkHttpClient == null) {
            synchronized (ROkHttp.class) {
                if (ROkHttpManager.mOkHttpClient == null) {
                    ROkHttpManager.mContext = context;
                    if (okHttpClient != null) {
                        ROkHttpManager.mOkHttpClient = okHttpClient;
                    } else {
                        OkHttpClient.Builder builder = new OkHttpClient.Builder();
                        if (RLog.isShowLog())
                            builder.addInterceptor(new HttpLoggingInterceptor());
                        ROkHttpManager.mOkHttpClient = builder.build();
                    }
                }
            }
        }
    }

    /**
     * 获取GET方式请求
     *
     * @return GetRequest 对象
     */
    @NonNull
    static GetRequest getRequest() {
        return new GetRequest();
    }

    /**
     * 获取GET方式请求
     *
     * @param okHttpClient 自定义的 OkHttpClient
     * @return GetRequest 对象
     */
    @NonNull
    static GetRequest getRequest(OkHttpClient okHttpClient) {
        return new GetRequest(okHttpClient);
    }

    /**
     * 获取POST方式提交键值对的形式请求
     *
     * @return PostKeyValueRequest 对象
     */
    @NonNull
    static PostKeyValueRequest postKeyValueRequest() {
        return new PostKeyValueRequest();
    }

    /**
     * 获取POST方式提交键值对的形式请求
     *
     * @param okHttpClient 自定义的 OkHttpClient
     * @return PostKeyValueRequest 对象
     */
    @NonNull
    static PostKeyValueRequest postKeyValueRequest(OkHttpClient okHttpClient) {
        return new PostKeyValueRequest(okHttpClient);
    }

    /**
     * 获取POST方式提交STRING数据的请求
     *
     * @return PostStringRequest 对象
     */
    @NonNull
    static PostStringRequest postStringRequest() {
        return new PostStringRequest();
    }

    /**
     * 获取POST方式提交STRING数据的请求
     *
     * @param okHttpClient 自定义的 OkHttpClient
     * @return PostStringRequest 对象
     */
    @NonNull
    static PostStringRequest postStringRequest(OkHttpClient okHttpClient) {
        return new PostStringRequest(okHttpClient);
    }

    /**
     * 获取POST方式提交JSON数据的请求
     *
     * @return PostJsonRequest 对象
     */
    @NonNull
    static PostJsonRequest postJsonRequest() {
        return new PostJsonRequest();
    }

    /**
     * 获取POST方式提交JSON数据的请求
     *
     * @param okHttpClient 自定义的 OkHttpClient
     * @return PostJsonRequest 对象
     */
    @NonNull
    static PostJsonRequest postJsonRequest(OkHttpClient okHttpClient) {
        return new PostJsonRequest(okHttpClient);
    }

    /**
     * 获取POST方式提交表单数据的请求
     *
     * @return PostFormRequest 对象
     */
    @NonNull
    static PostFormRequest postFormRequest() {
        return new PostFormRequest();
    }

    /**
     * 获取POST方式提交表单数据的请求
     *
     * @param okHttpClient 自定义的 OkHttpClient
     * @return PostFormRequest 对象
     */
    @NonNull
    static PostFormRequest postFormRequest(OkHttpClient okHttpClient) {
        return new PostFormRequest(okHttpClient);
    }

    /**
     * 获取POST方式提交byte数组的请求
     *
     * @return PostByteArrayRequest 对象
     */
    @NonNull
    static PostByteArrayRequest postByteArrayRequest() {
        return new PostByteArrayRequest();
    }

    /**
     * 获取POST方式提交byte数组的请求
     *
     * @param okHttpClient 自定义的 OkHttpClient
     * @return PostByteArrayRequest 对象
     */
    @NonNull
    static PostByteArrayRequest postByteArrayRequest(OkHttpClient okHttpClient) {
        return new PostByteArrayRequest(okHttpClient);
    }

    /**
     * 获取带进度的上传文件请求
     *
     * @return UploadFileRequest 对象
     */
    @NonNull
    static UploadFileRequest upLoadFileRequest() {
        return new UploadFileRequest();
    }

    /**
     * 获取带进度的上传文件请求
     *
     * @param okHttpClient 自定义的 OkHttpClient
     * @return UploadFileRequest 对象
     */
    @NonNull
    static UploadFileRequest upLoadFileRequest(OkHttpClient okHttpClient) {
        return new UploadFileRequest(okHttpClient);
    }

    /**
     * 获取带进度的下载文件请求<br/><br/>
     * <b>提示：在调用 enqueue(ROkHttpResponse mROkHttpResponseHandler) 方法时所传的
     * ROkHttpResponse 对象应为它的子类 DownLoadResponse(针对下载文件的Handler) 对象</b>
     *
     * @return DownloadFileRequest 对象
     */
    @NonNull
    static DownloadFileRequest downloadFileRequest() {
        return new DownloadFileRequest();
    }

    /**
     * 获取带进度的下载文件请求<br/><br/>
     * <b>提示：在调用 enqueue(ROkHttpResponse mROkHttpResponseHandler) 方法时所传的
     * ROkHttpResponse 对象应为它的子类 DownLoadResponse(针对下载文件的Handler) 对象</b>
     *
     * @param okHttpClient 自定义的 OkHttpClient
     * @return DownloadFileRequest 对象
     */
    @NonNull
    static DownloadFileRequest downloadFileRequest(OkHttpClient okHttpClient) {
        return new DownloadFileRequest(okHttpClient);
    }
}
