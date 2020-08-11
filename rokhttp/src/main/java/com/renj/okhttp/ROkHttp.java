package com.renj.okhttp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.renj.okhttp.request.DownloadFileRequest;
import com.renj.okhttp.request.GetRequest;
import com.renj.okhttp.request.PostFormRequest;
import com.renj.okhttp.request.PostJsonRequest;
import com.renj.okhttp.request.PostKeyValueRequest;
import com.renj.okhttp.request.PostStringRequest;
import com.renj.okhttp.request.UploadFileRequest;

import org.jetbrains.annotations.Contract;

import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * ======================================================================
 * 作者：Renj
 * <p/>
 * 创建时间：2017-03-12   1:57
 * <p/>
 * 描述：OkHttp网络工具类ROkHttp操作类，获取不同的ROkHttp请求和管理请求
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public class ROkHttp {
    private final static ROkHttp INSTANCE = new ROkHttp();

    private ROkHttp() {
    }

    public static ROkHttp getInstance() {
        return INSTANCE;
    }

    /* ----------------------- 初始化方法 ----------------------- */

    /**
     * 初始化ROkHttpUtil，只需在Application类中调用
     *
     * @param context 上下文
     */
    @org.jetbrains.annotations.Contract("null -> fail")
    public static void initROkHttp(@NonNull Context context) {
        ROkHttpManager.initROkHttp(context);
    }

    /**
     * 初始化ROkHttpUtil，使用自定义的OkHttpClient，只需在Application类中调用
     *
     * @param context      上下文
     * @param okHttpClient 自定义的OkHttpClient
     */
    @org.jetbrains.annotations.Contract("null, _ -> fail")
    public static void initROkHttp(@NonNull Context context, @Nullable OkHttpClient okHttpClient) {
        ROkHttpManager.initROkHttp(context, okHttpClient);
    }

    /* ------- 获取OkHttpClient.Builder对象,用于添加拦截器 -------- */

    /**
     * 获取一个clone的OkHttpClient.Builder对象,可用于特定接口添加拦截器
     *
     * @return {@link OkHttpClient.Builder}
     */
//    public OkHttpClient.Builder getOkHttpClientBuilder() {
//        return ROkHttpManager.mOkHttpClient.newBuilder();
//    }

    /* ----------------------- 日志管理 ----------------------- */

    /**
     * 设置是否显示日志
     *
     * @param showLog true：显示  false：不显示
     */
    public static void setShowLog(boolean showLog) {
        RLog.setShowLog(showLog);
    }

    /**
     * 设置显示日志的Tag
     *
     * @param logTag 日志的Tag，不能为null和""
     */
    public static void setLogTag(String logTag) {
        if (!TextUtils.isEmpty(logTag))
            RLog.setAppTAG(logTag);
    }

    /**
     * 设置是否显示打印日志位置的全类名
     *
     * @param isFullClassName true：显示  false：不显示
     */
    public static void setFullClassName(boolean isFullClassName) {
        RLog.setFullClassName(isFullClassName);
    }

    /**
     * 设置日志显示级别
     *
     * @param level {@link android.util.Log#VERBOSE} 等
     */
    public static void setLogLevel(int level) {
        RLog.setLogLevel(level);
    }

    /* ----------------------- 获取请求方法 ----------------------- */

    /**
     * 获取GET方式请求
     *
     * @return GetRequest 对象
     */
    @NonNull
    public GetRequest getRequest() {
        return ROkHttpManager.getRequest();
    }

    /**
     * 获取POST方式提交键值对的形式请求
     *
     * @return PostKeyValueRequest 对象
     */
    @NonNull
    public PostKeyValueRequest postKeyValueRequest() {
        return ROkHttpManager.postKeyValueRequest();
    }

    /**
     * 获取POST方式提交STRING数据的请求
     *
     * @return PostStringRequest 对象
     */
    @NonNull
    public PostStringRequest postStringRequest() {
        return ROkHttpManager.postStringRequest();
    }

    /**
     * 获取POST方式提交JSON数据的请求
     *
     * @return PostJsonRequest 对象
     */
    @NonNull
    public PostJsonRequest postJsonRequest() {
        return ROkHttpManager.postJsonRequest();
    }

    /**
     * 获取POST方式提交表单数据的请求
     *
     * @return PostFormRequest 对象
     */
    @NonNull
    public PostFormRequest postFormRequest() {
        return ROkHttpManager.postFormRequest();
    }

    /**
     * 获取带进度的上传文件请求
     *
     * @return UploadFileRequest 对象
     */
    @NonNull
    public UploadFileRequest upLoadFileRequest() {
        return ROkHttpManager.upLoadFileRequest();
    }

    /**
     * 获取带进度的下载文件请求<br/><br/>
     * <b>提示：在调用 enqueue(ROkHttpResponse mROkHttpResponseHandler) 方法时所传的
     * ROkHttpResponse 对象应为它的子类 DownLoadResponse(针对下载文件的Handler) 对象</b>
     *
     * @return DownloadFileRequest 对象
     */
    @NonNull
    public DownloadFileRequest downloadFileRequest() {
        return ROkHttpManager.downloadFileRequest();
    }

    /* ----------------------- 管理请求方法 ----------------------- */

    /**
     * 获取请求队列，返回所有加入到请求管理中并未取消的请求集合
     *
     * @return 所有加入到请求管理中并未取消的请求集合
     */
    @Contract(pure = true)
    public LinkedList<CallEntity> requestQueue() {
        return ROkHttpRequestManager.requestQueue();
    }

    /**
     * ROkHttpRequestManager类中保存的请求数
     *
     * @param requestCount 请求数
     */
    public void requestCount(int requestCount) {
        ROkHttpRequestManager.requestCount(requestCount);
    }

    /**
     * 清空ROkHttpRequestManager类中保存的所有请求
     */
    public void clearRequestManager() {
        ROkHttpRequestManager.clearRequestManager();
    }

    /**
     * 根据请求编号获取一个CallEntity实体
     *
     * @param callNo 请求编号
     * @return 返回一个 CallEntity 或者 null
     */
    @org.jetbrains.annotations.Contract(pure = true)
    public CallEntity getCallEntity(long callNo) {
        return ROkHttpRequestManager.getCallEntity(callNo);
    }

    /**
     * 根据Call对象获取一个CallEntity实体
     *
     * @param call Call对象
     * @return 返回一个 CallEntity 或者 null
     */
    @org.jetbrains.annotations.Contract("null -> null")
    public CallEntity getCallEntity(Call call) {
        return ROkHttpRequestManager.getCallEntity(call);
    }

    /**
     * 根据Request对象的Tag获取一个CallEntity实体集合(相同的Tag)
     *
     * @param tag Request对象的Tag
     * @return List集合
     */
    public List<CallEntity> getCallEntitys(Object tag) {
        return ROkHttpRequestManager.getCallEntities(tag);
    }

    /**
     * 根据请求编号取消请求
     *
     * @param callNo 请求编号
     */
    public void cancel(long callNo) {
        ROkHttpRequestManager.cancel(callNo);
    }

    /**
     * 根据请Call对象取消请求
     *
     * @param call Call对象
     */
    public void cancel(Call call) {
        ROkHttpRequestManager.cancel(call);
    }

    /**
     * 根据请Request对象取消请求
     *
     * @param request Request对象
     */
    public void cancel(Request request) {
        ROkHttpRequestManager.cancel(request);
    }

    /**
     * 根据请Request对象的Tag取消相同Tag的请求
     *
     * @param tag Request对象的Tag
     */
    public void cancel(Object tag) {
        ROkHttpRequestManager.cancel(tag);
    }

    /**
     * 根据CallEntity对象取消一个请求
     *
     * @param callEntity CallEntity对象
     */
    public void cancel(CallEntity callEntity) {
        ROkHttpRequestManager.cancel(callEntity);
    }

    /**
     * 取消所有未取消并且当前没有执行的请求
     */
    public void cancelAll() {
        ROkHttpRequestManager.cancelAll();
    }
}
