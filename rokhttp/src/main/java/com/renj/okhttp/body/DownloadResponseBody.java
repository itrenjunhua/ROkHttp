package com.renj.okhttp.body;


import com.renj.okhttp.request.DownloadFileRequest;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-03-17   1:18
 * <p>
 * 描述：自定义可更新进度的下载文件ResponseBody，继承至OkHttp的ResponseBody<br/>
 * 对ResponseBody进行一次包装
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class DownloadResponseBody extends ResponseBody {
    /**
     * 实际待包装的ResponseBody
     */
    private final ResponseBody mResponseBody;
    /**
     * 包装完成的BufferedSource
     */
    private BufferedSource mBufferedSource;
    /***
     * 进度回调接口
     */
    private final DownloadFileRequest.DownloadProgressListener mDownloadProgressListener;

    public DownloadResponseBody(ResponseBody responseBody,DownloadFileRequest.DownloadProgressListener downloadProgressListener) {
        this.mResponseBody = responseBody;
        this.mDownloadProgressListener = downloadProgressListener;
    }

    /**
     * 重写调用实际的响应体的contentType
     *
     * @return MediaType
     */
    @Override
    public MediaType contentType() {
        return this.mResponseBody.contentType();
    }

    /**
     * 重写调用实际的响应体的contentLength
     *
     * @return contentLength
     */
    @Override
    public long contentLength() {
        return this.mResponseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        // 包装
        if (mBufferedSource == null)
            mBufferedSource = Okio.buffer(customSource(mResponseBody.source()));
        return mBufferedSource;
    }

    private Source customSource(BufferedSource source) {
        return new ForwardingSource(source) {
            // 当前读取字节数
            long bytesRead = 0L;
            // 总字节长度，避免多次调用contentLength()方法
            long contentLength = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long byteRead = super.read(sink, byteCount);
                // 如果contentLength()不知道长度，会返回-1
                if (contentLength == 0) contentLength = contentLength();
                // 增加当前读取的字节数，如果读取完成了byteRead会返回-1
                if (byteRead != -1)
                    bytesRead += byteRead;
                // 回调
                mDownloadProgressListener.onProgress(bytesRead, contentLength, byteRead == -1);
                return byteRead;
            }
        };
    }
}
