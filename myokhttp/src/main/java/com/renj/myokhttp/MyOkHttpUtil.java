package com.renj.myokhttp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.renj.myokhttp.request.DownloadFileRequest;
import com.renj.myokhttp.request.GetRequest;
import com.renj.myokhttp.request.PostFormRequest;
import com.renj.myokhttp.request.PostJsonRequest;
import com.renj.myokhttp.request.PostKeyValueRequest;
import com.renj.myokhttp.request.PostStringRequest;
import com.renj.myokhttp.request.UploadFileRequest;

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
 * 描述：OkHttp网络工具类MyOkHttp操作类，获取不同的MyOkHttp请求和管理请求
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public class MyOkHttpUtil {
    private MyOkHttpUtil() {
    }

/*----------------------------------------- 初始化方法 -----------------------------------------*/

    /**
     * 初始化MyOkHttpUtil，只需在Application类中调用
     *
     * @param context 上下文
     */
    @org.jetbrains.annotations.Contract("null -> fail")
    public static void initMyOkHttpUtil(@NonNull Context context) {
        MyOkHttp.initMyOkHttpUtil(context);
    }

    /**
     * 初始化MyOkHttpUtil，使用自定义的OkHttpClient，只需在Application类中调用
     *
     * @param context      上下文
     * @param okHttpClient 自定义的OkHttpClient
     */
    @org.jetbrains.annotations.Contract("null, _ -> fail")
    public static void initMyOkHttpUtil(@NonNull Context context, @Nullable OkHttpClient okHttpClient) {
        MyOkHttp.initMyOkHttpUtil(context, okHttpClient);
    }

/*----------------------------------------- 获取请求方法 -----------------------------------------*/

    /**
     * 获取GET方式请求
     *
     * @return GetRequest 对象
     */
    @NonNull
    public static GetRequest getRequest() {
        return new GetRequest();
    }

    /**
     * 获取POST方式提交键值对的形式请求
     *
     * @return PostKeyValueRequest 对象
     */
    @NonNull
    public static PostKeyValueRequest postKeyValueRequest() {
        return new PostKeyValueRequest();
    }

    /**
     * 获取POST方式提交STRING数据的请求
     *
     * @return PostStringRequest 对象
     */
    @NonNull
    public static PostStringRequest postStringRequest() {
        return new PostStringRequest();
    }

    /**
     * 获取POST方式提交JSON数据的请求
     *
     * @return PostJsonRequest 对象
     */
    @NonNull
    public static PostJsonRequest postJsonRequest() {
        return new PostJsonRequest();
    }

    /**
     * 获取POST方式提交表单数据的请求
     *
     * @return PostFormRequest 对象
     */
    @NonNull
    public static PostFormRequest postFormRequest() {
        return new PostFormRequest();
    }

    /**
     * 获取带进度的上传文件请求
     *
     * @return UploadFileRequest 对象
     */
    @NonNull
    public static UploadFileRequest upLoadFileRequest() {
        return new UploadFileRequest();
    }

    /**
     * 获取带进度的下载文件请求<br/><br/>
     * <b>提示：在调用 enqueue(MyOkHttpResponseHandler mMyOkHttpResponseHandler) 方法时所传的
     * MyOkHttpResponseHandler 对象应为它的子类 DownLoadResponseHandler(针对下载文件的Handler) 对象</b>
     *
     * @return DownloadFileRequest 对象
     */
    @NonNull
    public static DownloadFileRequest downloadFileRequest() {
        return new DownloadFileRequest();
    }

/*----------------------------------------- 管理请求方法 -----------------------------------------*/

    /**
     * 获取请求队列，返回所有加入到请求管理中并未取消的请求集合
     *
     * @return 所有加入到请求管理中并未取消的请求集合
     */
    @Contract(pure = true)
    public static LinkedList<CallEntity> requestQueue() {
        return MyOkHttpRequestManager.requestQueue();
    }

    /**
     * MyOkHttpRequestManager类中保存的请求数
     *
     * @param requestCount 请求数
     */
    public static void requestCount(int requestCount) {
        MyOkHttpRequestManager.requestCount(requestCount);
    }

    /**
     * 清空MyOkHttpRequestManager类中保存的所有请求
     */
    public static void clearRequestManager() {
        MyOkHttpRequestManager.clearRequestManager();
    }

    /**
     * 根据请求编号获取一个CallEntity实体
     *
     * @param callNo 请求编号
     * @return 返回一个 CallEntity 或者 null
     */
    @org.jetbrains.annotations.Contract(pure = true)
    public static CallEntity getCallEntity(long callNo) {
        return MyOkHttpRequestManager.getCallEntity(callNo);
    }

    /**
     * 根据Call对象获取一个CallEntity实体
     *
     * @param call Call对象
     * @return 返回一个 CallEntity 或者 null
     */
    @org.jetbrains.annotations.Contract("null -> null")
    public static CallEntity getCallEntity(Call call) {
        return MyOkHttpRequestManager.getCallEntity(call);
    }

    /**
     * 根据Request对象的Tag获取一个CallEntity实体集合(相同的Tag)
     *
     * @param tag Request对象的Tag
     * @return List集合
     */
    public static List<CallEntity> getCallEntitys(Object tag) {
        return MyOkHttpRequestManager.getCallEntitys(tag);
    }

    /**
     * 根据请求编号取消请求
     *
     * @param callNo 请求编号
     */
    public static void cancel(long callNo) {
        MyOkHttpRequestManager.cancel(callNo);
    }

    /**
     * 根据请Call对象取消请求
     *
     * @param call Call对象
     */
    public static void cancel(Call call) {
        MyOkHttpRequestManager.cancel(call);
    }

    /**
     * 根据请Request对象取消请求
     *
     * @param request Request对象
     */
    public static void cancel(Request request) {
        MyOkHttpRequestManager.cancel(request);
    }

    /**
     * 根据请Request对象的Tag取消相同Tag的请求
     *
     * @param tag Request对象的Tag
     */
    public static void cancel(Object tag) {
        MyOkHttpRequestManager.cancel(tag);
    }

    /**
     * 根据CallEntity对象取消一个请求
     *
     * @param callEntity CallEntity对象
     */
    public static void cancel(CallEntity callEntity) {
        MyOkHttpRequestManager.cancel(callEntity);
    }

    /**
     * 取消所有未取消并且当前没有执行的请求
     */
    public static void cancelAll() {
        MyOkHttpRequestManager.cancelAll();
    }
}
