package com.renj.okhttp.request;

import android.support.annotation.NonNull;

import com.renj.okhttp.RLog;
import com.renj.okhttp.ROkHttpRequest;
import com.renj.okhttp.ROkHttpResponse;
import com.renj.okhttp.body.UploadRequestBody;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
 * 描述：创建一个ROkHttp的提交文件请求<p/>
 * <b>文件上传参数说明</b><br/>
 * <b><i>Content-Disposition: form-data; name="参数";filename="文件名"</i></b>
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public class UploadFileRequest extends ROkHttpRequest<UploadFileRequest> {
    private Map<String, String> mParams;
    private List<Map<String, File>> mFiles;
    private List<TempFile> mTempFiles;

    public UploadFileRequest() {
        super();
    }

    public UploadFileRequest(OkHttpClient okHttpClient) {
        super(okHttpClient);
    }

    /**
     * 添加一个键值对参数
     *
     * @param key   键名
     * @param value 值
     * @return 本身对象，方便链式调用
     */
    public UploadFileRequest param(@NonNull String key, @NonNull String value) {
        return addParam(key, value);
    }

    /**
     * 新增键值对参数
     *
     * @param key   键名
     * @param value 值
     * @return 本身对象，方便链式调用
     */
    private UploadFileRequest addParam(@NonNull String key, @NonNull String value) {
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
    public UploadFileRequest params(Map<String, String> params) {
        if (params != null && !params.isEmpty()) {
            if (this.mParams == null) this.mParams = new LinkedHashMap<>();
            this.mParams.putAll(params);
        }
        return this;
    }

    /**
     * 添加一个上传文件，这里的filename使用file.getName()获取到的文件名
     *
     * @param name 上传文件的参数名
     * @param file 上传文件对象
     * @return 本身对象，方便链式调用
     */
    public UploadFileRequest file(@NonNull String name, @NonNull File file) {
        return addFile(name, file);
    }

    /**
     * 新增上传文件，这里的filename使用file.getName()获取到的文件名
     *
     * @param name 上传文件的参数名
     * @param file 上传文件对象
     * @return 本身对象，方便链式调用
     */
    public UploadFileRequest addFile(@NonNull String name, @NonNull File file) {
        if (this.mFiles == null) this.mFiles = new LinkedList<>();
        Map<String, File> map = new HashMap<>();
        map.put(name, file);
        this.mFiles.add(map);
        return this;
    }

    /**
     * 新增上传文件集合，Map的键名为name的值，filename使用file.getName()获取到的文件名
     *
     * @param files 上传文件集合
     * @return 本身对象，方便链式调用
     */
    public UploadFileRequest files(List<Map<String, File>> files) {
        if (files != null && !files.isEmpty()) {
            if (this.mFiles == null) this.mFiles = new LinkedList<>();
            this.mFiles.addAll(files);
        }
        return this;
    }

    /**
     * 添加一个上传文件
     *
     * @param name         上传文件的参数名
     * @param fileName     上传文件到到服务器的名称
     * @param contentBytes 上传文件的字节数组
     * @return 本身对象，方便链式调用
     */
    public UploadFileRequest file(@NonNull String name, @NonNull String fileName, @NonNull byte[] contentBytes) {
        return addFile(name, fileName, contentBytes);
    }

    /**
     * 新增上传文件
     *
     * @param name         上传文件的参数名
     * @param fileName     上传文件到到服务器的名称
     * @param contentBytes 上传文件的字节数组
     * @return 本身对象，方便链式调用
     */
    public UploadFileRequest addFile(@NonNull String name, @NonNull String fileName, @NonNull byte[] contentBytes) {
        if (this.mTempFiles == null) this.mTempFiles = new LinkedList<>();
        TempFile tempFile = new TempFile();
        tempFile.name = name;
        tempFile.fileName = fileName;
        tempFile.mediaType = MediaType.parse(getFileMime(fileName));
        tempFile.contentBytes = contentBytes;
        this.mTempFiles.add(tempFile);
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
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        appendParams(multipartBodyBuilder); // 增加普通参数
        appendFiles(multipartBodyBuilder);// 增加文件参数
        appendTempFiles(multipartBodyBuilder); // 增加文件参数

        if (isEmptyMap(mParams) && isEmptyList(mFiles) && isEmptyList(mTempFiles)) {
            // builder.get(); // 默认 GET
            RLog.w("没有上传任何文件和参数，将 POST 请求方式变为 GET 请求方式");
        } else {
            // 将参数加入到请求中
            builder.post(new UploadRequestBody(multipartBodyBuilder.build(),
                            (completeLength, totalLength, isFinish) ->
                                    mHandler.post(() -> mROkHttpResponse.onProgress(completeLength, totalLength, isFinish))
                    )
            );
        }
    }

    /**
     * 增加文件参数
     */
    private void appendTempFiles(MultipartBody.Builder multipartBodyBuilder) {
        if (this.mTempFiles == null || this.mTempFiles.isEmpty()) return;
        for (TempFile tempFile : mTempFiles) {
            multipartBodyBuilder.addFormDataPart(tempFile.name, tempFile.fileName,
                    RequestBody.create(tempFile.contentBytes, tempFile.mediaType));
        }
    }

    /**
     * 增加文件参数
     */
    private void appendFiles(MultipartBody.Builder multipartBodyBuilder) {
        if (this.mFiles == null || this.mFiles.isEmpty()) return;
        for (Map<String, File> fileMap : mFiles) {
            for (String name : fileMap.keySet()) {
                File file = fileMap.get(name);
                String fileName = getFileName(file);
                multipartBodyBuilder.addFormDataPart(name, fileName,
                        RequestBody.create(file, MediaType.parse(getFileMime(fileName))));
            }
        }
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

    /**
     * 获取文件名
     *
     * @param file 问价对象
     */
    private String getFileName(@NonNull File file) {
        return file.getName();
    }

    /**
     * 根据文件名获取文件的 MIME 类型
     */
    private String getFileMime(String fileName) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(fileName);
        if (null == contentTypeFor) {
            return "application/octet-stream";
        }
        return contentTypeFor;
    }

    /**
     * 保存临时文件数据
     */
    private static class TempFile {
        String name;
        String fileName;
        MediaType mediaType;
        byte[] contentBytes;
    }

    /**
     * 判断Map是否为 null 或者 元素为空
     */
    private boolean isEmptyMap(Map<?, ?> map) {
        return null == map || map.isEmpty();
    }

    /**
     * 判断List是否为 null 或者 元素为空
     */
    private boolean isEmptyList(List<?> list) {
        return null == list || list.isEmpty();
    }

    /**
     * 更新进度接口
     */
    public interface UploadProgressListener {
        void onProgress(long completeLength, long totalLength, boolean isFinish);
    }
}
