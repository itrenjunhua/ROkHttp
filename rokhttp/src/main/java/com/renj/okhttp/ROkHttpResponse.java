package com.renj.okhttp;

import okhttp3.Call;
import okhttp3.Response;

import static com.renj.okhttp.ROkHttpManager.mHandler;


/**
 * ======================================================================
 * 作者：Renj
 * <p/>
 * 创建时间：2017-03-12   4:42
 * <p/>
 * 描述：ROkHttp响应结果处理超类<br/><br/>
 * <b>特别注意：</b><br/>
 * <b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * 在重写方法时，需要注意重写的该方法是否在UI线程</b><br/><br/>
 * <b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * onSucceed()、onError()、onNetWork()以及onProgress() 4 个方法在UI线程中执行，当使用的Handler为DownLoadResponseHandler时，onFilePathException()方法在调用的线程中执行</b><br/><br/>
 * <b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * onParseSucceed()、onOkHttpError()、以及parseResponse() 3 个方法在非UI线程中执行</b><br/>
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public abstract class ROkHttpResponse<T> {
    public ROkHttpResponse() {
    }

    /**
     * 开始访问接口
     */
    public void onStart(String url) {
        RLog.i("【start request】 <== url : " + url + " ==> 【start】");
    }

    /**
     * 解析响应结果之后调用，将解析后的数据作为参数传递，UI线程。<br/>
     * 在使用时重写该方法即可得到响应数据<br/><br/>
     * <b>特别注意：</b><br/>
     * <b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 如果是下载文件，参数 result 表示下载文件保存的位置</b>
     *
     * @param call   Call对象
     * @param result 解析之后的结果，类型为解析时所指定的类型
     */
    public abstract void onSucceed(Call call, T result);

    /**
     * 错误时的回调，UI线程。
     *
     * @param call  Call对象
     * @param error 错误信息
     */
    public void onError(Call call, ROkHttpException error) {
        RLog.e(call.request().url() + " : " + error + "");
    }

    /**
     * 当没有网络连接时回调，UI线程。
     */
    public void onNetWork() {
    }

    /**
     * 上传下载文件时重写可以更新进度，非上传下载时没有作用，UI线程。<br/><br/>
     * <b>特别注意：</b><br/>
     * <b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 参数仅在上传下载文件时有值，在非上传下载时参数为 null，如果使用会报 null 异常</b>
     *
     * @param completeLength 已完成长度
     * @param totalLength    文件总长度
     * @param isFinish       是否已经完成
     */
    public void onProgress(long completeLength, long totalLength, boolean isFinish) {
    }

    /**
     * 解析成功之后的回调，在非UI线程。<br/><br/>
     * <b>特别注意：</b><br/>
     * <b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 此方法的执行线程并非UI线程，onSucceed(Call call, T result)方法的执行线程为UI线程</b>
     */
    protected void onParseSucceed(final Call call, final T result) {
        mHandler.post(() -> onSucceed(call, result));
    }

    /**
     * 失败时的回调，在非UI线程，可以做耗时操作(比如将失败信息写入到文件中保存等)。<br/><br/>
     * <b>特别注意：</b><br/>
     * <b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 此方法的执行线程并非UI线程，onError(Call call, ROkHttpException error)方法的执行线程为UI线程</b>
     */
    protected void onOkHttpError(final Call call, final ROkHttpException error) {
        mHandler.post(() -> onError(call, error));
    }

    /**
     * 解析响应，将响应结果做对应操作，非UI线程。<br/><br/>
     * <b>特别注意：</b><br/>
     * <b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 此方法的执行线程并非UI线程</b>
     *
     * @param call     Call对象
     * @param response 服务器的响应结果
     */
    protected abstract void parseResponse(Call call, Response response);

    /**
     * 结束访问接口
     */
    public void onEnd(String url) {
        RLog.i("【end request】 <== url : " + url + " ==> 【end】");
    }
}
