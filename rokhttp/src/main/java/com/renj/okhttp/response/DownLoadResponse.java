package com.renj.okhttp.response;

import com.renj.okhttp.RLog;
import com.renj.okhttp.ROkHttpException;
import com.renj.okhttp.ROkHttpResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Call;
import okhttp3.Response;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-03-20   2:58
 * <p>
 * 描述：文件下载专用类，继承ROkHttpResponseHandler<T>类，指定泛型为String<br/>
 * 回调方法onSucceed(Call call,String result)的 result 为文件保存路径
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class DownLoadResponse extends ROkHttpResponse<String> {
    /**
     * 最终文件保存路径
     */
    private String mSaveFilePath = "";

    @Override
    protected void parseResponse(Call call, Response response) {
        // 保存文件
        try {
            saveFile(response);
            onParseSucceed(call, mSaveFilePath);
        } catch (ROkHttpException rOkHttpException) {
            rOkHttpException.printStackTrace();
            onOkHttpError(call, rOkHttpException);
        }
    }

    /**
     * 保存文件
     *
     * @param response 响应对象
     */
    private void saveFile(Response response) throws ROkHttpException {
        int len;
        byte[] bytes = new byte[1024 * 8]; // 每次读取8kb
        RandomAccessFile randomAccessFile = null;
        InputStream inputStream = null;

        try {
            inputStream = response.body().byteStream();
            randomAccessFile = new RandomAccessFile(mSaveFilePath, "rwd");
            while ((len = inputStream.read(bytes)) != -1) {
                randomAccessFile.write(bytes, 0, len);
            }
        } catch (IOException e) {
            throw new ROkHttpException("将文件写入到 \"" + mSaveFilePath + " \"时出错！", e);
        } finally {
            try {
                if (randomAccessFile != null) randomAccessFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 检查文件路径
     */
    public void checkFilePath(String mFilePath, File mFileP, String mFileDir, File mFileD, String mFileName) throws ROkHttpException {
        File file;
        if (null == mFileP) {
            if (mFilePath.length() == 0 || null == mFileD) {
                throw new IllegalArgumentException("文件路径不能为 null！");
            } else {
                if (null != mFileD) {
                    if (mFileName.length() == 0) {
                        throw new IllegalArgumentException("文件名不能为 null！");
                    } else {
                        mSaveFilePath = mFileD.getAbsolutePath() + mFileName;
                        file = new File(mSaveFilePath);
                    }
                } else {
                    if (mFileName.length() == 0) {
                        throw new IllegalArgumentException("文件名不能为 null！");
                    } else {
                        mSaveFilePath = mFileDir + mFileName;
                        file = new File(mSaveFilePath);
                    }
                }
            }
        } else {
            file = mFileP;
            mSaveFilePath = file.getAbsolutePath();
        }

        if (file.exists()) {
            throw new ROkHttpException("文件 \"" + mSaveFilePath + " \"已存在，覆盖原文件...");
        }
        if (mSaveFilePath.endsWith(File.separator)) {
            throw new ROkHttpException("创建文件 \"" + mSaveFilePath + " \"失败，目标文件不能为目录！");
        }
        //判断目标文件所在的目录是否存在；不存在，就创建
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                throw new ROkHttpException("创建目标文件 \"" + mSaveFilePath + " \"所在目录失败！");
            }
        }
    }

    /**
     * 当指定下载文件保存目录发生异常时回调，UI线程
     *
     * @param rOkHttpException ROkHttpException对象
     */
    public void onFilePathException(ROkHttpException rOkHttpException) {
        RLog.e(rOkHttpException + "");
    }
}
