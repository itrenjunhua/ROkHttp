package com.renj.okhttp.body;


import com.renj.okhttp.request.UploadFileRequest;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-03-16   0:26
 * <p>
 * 描述：自定义可更新进度的上传文件RequestBody，继承至OkHttp的RequestBody<br/>
 * 对RequestBody进行一次包装
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class UploadRequestBody extends RequestBody {
    /**
     * 实际待包装的RequestBody
     */
    private final RequestBody mRequestBody;
    /**
     * 包装完成的BufferedSink
     */
    private BufferedSink mBuffer;
    /***
     * 进度回调接口
     */
    private final UploadFileRequest.UploadProgressListener mUploadProgressListener;

    public UploadRequestBody(RequestBody requestBody, UploadFileRequest.UploadProgressListener uploadProgressListener) {
        this.mRequestBody = requestBody;
        this.mUploadProgressListener = uploadProgressListener;
    }

    /**
     * 重写调用实际的响应体的contentType
     *
     * @return MediaType
     */
    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    /**
     * 重写调用实际的响应体的contentLength
     *
     * @return contentLength
     * @throws IOException 异常
     */
    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    /**
     * 重写进行写入
     *
     * @param sink BufferedSink
     * @throws IOException 异常
     */
    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        // 包装
        if (mBuffer == null)
            mBuffer = Okio.buffer(customSink(sink));
        // 写入
        mRequestBody.writeTo(mBuffer);
        // 必须调用flush，否则最后一部分数据可能不会被写入
        mBuffer.flush();
    }

    private Sink customSink(BufferedSink sink) {
        return new ForwardingSink(sink) {
            // 当前写入字节数
            long bytesWritten = 0L;
            // 总字节长度，避免多次调用contentLength()方法
            long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                // 获得contentLength的值，后续不再调用
                if (contentLength == 0)
                    contentLength = contentLength();
                // 增加当前写入的字节数
                bytesWritten += byteCount;
                // 回调
                mUploadProgressListener.onProgress(bytesWritten, contentLength, bytesWritten == contentLength);
            }
        };
    }
}
