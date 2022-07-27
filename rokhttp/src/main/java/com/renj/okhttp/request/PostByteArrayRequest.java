package com.renj.okhttp.request;

import android.support.annotation.NonNull;

import com.renj.okhttp.ROkHttpRequest;
import com.renj.okhttp.ROkHttpResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-03-15    9:55
 * <p/>
 * 描述：创建一个ROkHttp的提交文byte数组请求<p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public class PostByteArrayRequest extends ROkHttpRequest<PostByteArrayRequest> {
    private Map<String, String> mParams;
    private List<byte[]> bytesList;

    public PostByteArrayRequest() {
        super();
    }

    public PostByteArrayRequest(OkHttpClient okHttpClient) {
        super(okHttpClient);
    }

    /**
     * 添加一个键值对参数
     *
     * @param key   键名
     * @param value 值
     * @return 本身对象，方便链式调用
     */
    public PostByteArrayRequest param(@NonNull String key, @NonNull String value) {
        return addParam(key, value);
    }

    /**
     * 新增键值对参数
     *
     * @param key   键名
     * @param value 值
     * @return 本身对象，方便链式调用
     */
    private PostByteArrayRequest addParam(@NonNull String key, @NonNull String value) {
        if (this.mParams == null) this.mParams = new HashMap<>();
        mParams.put(key, value);
        return this;
    }

    /**
     * 增加一个参数集合
     *
     * @param params 参数集合
     * @return 本身对象，方便链式调用
     */
    public PostByteArrayRequest params(Map<String, String> params) {
        if (params != null && !params.isEmpty()) {
            if (this.mParams == null) this.mParams = new LinkedHashMap<>();
            this.mParams.putAll(params);
        }
        return this;
    }

    /**
     * 添加需要上传的byte[]数据
     *
     * @param bytes 需要上传的byte[]数据
     * @return 本身对象，方便链式调用
     */
    public PostByteArrayRequest bytes(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) return this;

        if (bytesList == null)
            bytesList = new ArrayList<>();

        this.bytesList.add(bytes);
        return this;
    }

    /**
     * 添加需要上传的byte[]列表
     *
     * @param bytes 需要上传的byte[]列表
     * @return 本身对象，方便链式调用
     */
    public PostByteArrayRequest bytes(List<byte[]> bytes) {
        if (bytes != null && bytes.size() > 0) {
            for (byte[] aByte : bytes) {
                bytes(aByte);
            }
        }
        return this;
    }

    /**
     * 将参数(键值对参数、文件参数)加入到请求中
     *
     * @param builder          Request.Builder对象
     * @param mROkHttpResponse ROkHttpResponseHandler抽象类的实现类对象
     * @param <E>              泛型
     */
    @Override
    protected <E> void putParams(Request.Builder builder, final ROkHttpResponse<E> mROkHttpResponse) {
        // 只单传一个byte数组时不分块
        if ((mParams == null || mParams.size() <= 0)
                && (this.bytesList != null && this.bytesList.size() == 1)) {
            builder.post(RequestBody.create(this.bytesList.get(0), MediaType.parse("application/octet-stream")));
            return;
        }

        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        appendParams(multipartBodyBuilder); // 增加普通参数

        // 增加byte数组参数
        if (this.bytesList != null && this.bytesList.size() > 0) {
            for (byte[] bytes : this.bytesList) {
                multipartBodyBuilder.addPart(RequestBody.create(bytes, MediaType.parse("application/octet-stream")));
            }
        }
        builder.post(multipartBodyBuilder.build());
    }

    /**
     * 增加普通参数
     */
    private void appendParams(MultipartBody.Builder multipartBodyBuilder) {
        if (this.mParams == null || this.mParams.isEmpty()) return;
        for (String name : mParams.keySet()) {
            multipartBodyBuilder.addFormDataPart(name, mParams.get(name));
        }
    }
}
