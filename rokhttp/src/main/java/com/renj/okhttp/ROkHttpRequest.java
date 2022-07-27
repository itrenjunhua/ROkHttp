package com.renj.okhttp;

import android.os.Handler;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ======================================================================
 * 作者：Renj
 * <p/>
 * 创建时间：2017-03-12   1:55
 * <p/>
 * 描述：创建一个ROkHttp请求的超类
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public abstract class ROkHttpRequest<T extends ROkHttpRequest<?>> {
    protected String mUrl;
    protected Object mTag;
    protected CacheControl mCacheControl;
    protected Map<String, String> mHeaders;
    protected Handler mHandler;
    protected OkHttpClient mOkHttpClient;
    // 是否将请求加入到请求管理队列中，默认加入
    private boolean mAddRequestManger = true;

    /**
     * 构造方法，检查是否已经对ROkHttp进行了初始化
     */
    public ROkHttpRequest() {
        this.mHandler = ROkHttpManager.mHandler;
        this.mOkHttpClient = ROkHttpManager.mOkHttpClient;
        if (this.mOkHttpClient == null)
            throw new NullPointerException("ROkHttpManager 初始化异常：" + "没有在 Application 中调用 ROkHttpManager.initOkHttpUtil(Context context) 方法进行初始化");
    }

    /**
     * 构造方法，检查是否已经对ROkHttp进行了初始化
     */
    public ROkHttpRequest(OkHttpClient okHttpClient) {
        this.mHandler = ROkHttpManager.mHandler;
        if (okHttpClient != null)
            this.mOkHttpClient = okHttpClient;
        else
            this.mOkHttpClient = ROkHttpManager.mOkHttpClient;
        if (this.mOkHttpClient == null)
            throw new NullPointerException("ROkHttpManager 初始化异常：" + "没有在 Application 中调用 ROkHttpManager.initOkHttpUtil(Context context) 方法进行初始化");
    }

    /**
     * 设置访问地址
     *
     * @param url 访问地址
     * @return
     */
    public T url(@NonNull String url) {
        this.mUrl = url;
        return (T) this;
    }

    /**
     * 给当前的请求指定一个Tag
     *
     * @param tag 指定的Tag
     * @return
     */
    public T tag(@NonNull Object tag) {
        this.mTag = tag;
        return (T) this;
    }

    /**
     * 设置单个头信息
     *
     * @param name  键名
     * @param value 值
     * @return
     */
    public T header(@NonNull String name, @NonNull String value) {
        addHeader(name, value);
        return (T) this;
    }

    /**
     * 增加单个头信息
     *
     * @param name  键名
     * @param value 值
     * @return
     */
    private T addHeader(@NonNull String name, @NonNull String value) {
        if (this.mHeaders == null) this.mHeaders = new LinkedHashMap<String, String>();
        this.mHeaders.put(name, value);
        return (T) this;
    }

    /**
     * 添加一个头信息集合
     *
     * @param headers 头信息集合
     * @return
     */
    public T headers(Map<String, String> headers) {
        if (headers != null && !headers.isEmpty()) {
            if (this.mHeaders == null) this.mHeaders = new LinkedHashMap<String, String>();
            this.mHeaders.putAll(headers);
        }
        return (T) this;
    }

    /**
     * 根据键名移除单个头信息
     *
     * @param name 需要移除的头信息名称
     * @return
     */
    public T removeHeader(@NonNull String name) {
        if (this.mHeaders != null) this.mHeaders.remove(name);
        return (T) this;
    }

    /**
     * 清空头信息
     *
     * @return
     */
    public T clearHeaders() {
        if (this.mHeaders != null) this.mHeaders.clear();
        return (T) this;
    }

    /**
     * 针对当前请求的缓存控制
     *
     * @param cacheControl CacheControl对象
     * @return
     */
    public T cacheControl(@NonNull CacheControl cacheControl) {
        this.mCacheControl = cacheControl;
        return (T) this;
    }

    /**
     * 是否将请求加入到全局的请求队列中，加入后可以管理所有请求，默认加入
     *
     * @param addRequestManger true 加入，false 不加入
     * @return
     */
    public T addRequestManger(@NonNull boolean addRequestManger) {
        this.mAddRequestManger = addRequestManger;
        return (T) this;
    }

    /**
     * 开始执行网络访问
     *
     * @param <E>              泛型，期望的结果类型
     * @param mROkHttpResponse ROkHttpResponseHandler抽象类的实现类对象
     */
    public <E> void enqueue(@NonNull final ROkHttpResponse<E> mROkHttpResponse) {
        // 开始访问接口
        mROkHttpResponse.onStart(mUrl);

        // 对网络链接进行判断
        if (!isConnectedByState()) {
            mROkHttpResponse.onNetWork();
            mROkHttpResponse.onEnd(mUrl);
            return;
        }
        Call call = createCall(mROkHttpResponse);
        // 开始请求数据
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                mROkHttpResponse.onOkHttpError(call, new ROkHttpException(e));
                mROkHttpResponse.onEnd(mUrl);
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                // 将解析过程放在非UI线程
                mROkHttpResponse.parseResponse(call, response);
                mROkHttpResponse.onEnd(mUrl);
            }
        });
    }

    /**
     * 判断网络连接状态
     *
     * @return
     */
    protected boolean isConnectedByState() {
        return NetWorkUtils.isConnectedByState(ROkHttpManager.mContext);
    }

    /**
     * 创建一个Call对象，并添加到请求集合中
     *
     * @param mROkHttpResponse ROkHttpResponseHandler抽象类的实现类对象
     * @return
     */
    protected <E> Call createCall(ROkHttpResponse<E> mROkHttpResponse) {
        Request request = createRequest(mROkHttpResponse);
        Call call = getOkHttpClient(mROkHttpResponse).newCall(request);
        if (this.mAddRequestManger)
            ROkHttpRequestManager.putRequest(call, request, mTag);
        return call;
    }

    /**
     * 获取一个 OkHttpClient 对象，当需要定制OkHttpClient时可以重写该方法，然后克隆一个OkHttpClient
     *
     * @return OkHttpClient 对象
     */
    protected <E> OkHttpClient getOkHttpClient(ROkHttpResponse<E> mROkHttpResponse) {
        return mOkHttpClient;
    }

    /**
     * 创建一个OKHttp的Request对象
     *
     * @param mROkHttpResponse ROkHttpResponseHandler抽象类的实现类对象
     * @return
     */
    private <E> Request createRequest(ROkHttpResponse<E> mROkHttpResponse) {
        if (mUrl == null) throw new NullPointerException("Url为null异常：访问地址(URL)为 null");
        Request.Builder builder = new Request.Builder();
        putHeader(builder);
        putParams(builder, mROkHttpResponse);
        cacheManager(builder);
        builder.url(mUrl);

        if (mTag != null) builder.tag(mTag);
        return buildRequest(builder);
    }

    /**
     * 如果添加了CacheControl对象，将CacheControl对象添加到OkHttp的Request中
     *
     * @param builder
     */
    private void cacheManager(Request.Builder builder) {
        if (this.mCacheControl != null) builder.cacheControl(this.mCacheControl);
    }

    /**
     * 将头信息添加到OKHttp的Request中
     *
     * @param builder
     */
    private void putHeader(Request.Builder builder) {
        if (mHeaders == null || mHeaders.isEmpty()) return;
        Headers headers = Headers.of(mHeaders);
        builder.headers(headers);
    }

    /**
     * 通过Request.Builder对象创建一个OKHttp的Request对象
     *
     * @param builder Request.Builder对象
     * @return
     */
    protected Request buildRequest(Request.Builder builder) {
        return builder.build();
    }

    /**
     * 向OKHttp的Request对象中添加参数的方法<br/>
     * <b>不同的请求，添加的参数不同，所以该方法由超类定义，子类实现</b>
     *
     * @param builder          Request.Builder对象
     * @param mROkHttpResponse ROkHttpResponseHandler抽象类的实现类对象
     */
    protected abstract <E> void putParams(Request.Builder builder, ROkHttpResponse<E> mROkHttpResponse);
}
