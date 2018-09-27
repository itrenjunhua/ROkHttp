package com.renj.myokhttp.response;

import com.renj.myokhttp.LogUtil;
import com.renj.myokhttp.MyOkHttpException;
import com.renj.myokhttp.MyOkHttpResponseHandler;

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
 * 描述：文件下载专用类，继承MyOkHttpResponseHandler<T>类，指定泛型为String<br/>
 * 回调方法onSucceed(Call call,String result)的 result 为文件保存路径
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class DownLoadResponseHandler extends MyOkHttpResponseHandler<String> {
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
        } catch (MyOkHttpException myOkHttpException) {
            myOkHttpException.printStackTrace();
            onOkHttpError(call, myOkHttpException);
        }
    }

    /**
     * 保存文件
     *
     * @param response
     * @return 是否保存成功
     */
    private boolean saveFile(Response response) throws MyOkHttpException {
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
            return true;
        } catch (IOException e) {
            throw new MyOkHttpException("将文件写入到 \"" + mSaveFilePath + " \"时出错！", e);
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
     *
     * @param mFilePath
     * @param mFileP
     * @param mFileDir
     * @param mFileD
     * @param mFileName
     */
    public void checkFilePath(String mFilePath, File mFileP, String mFileDir, File mFileD, String mFileName) throws MyOkHttpException {
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
            throw new MyOkHttpException("文件 \"" + mSaveFilePath + " \"已存在，覆盖原文件...");
        }
        if (mSaveFilePath.endsWith(File.separator)) {
            throw new MyOkHttpException("创建文件 \"" + mSaveFilePath + " \"失败，目标文件不能为目录！");
        }
        //判断目标文件所在的目录是否存在；不存在，就创建
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                throw new MyOkHttpException("创建目标文件 \"" + mSaveFilePath + " \"所在目录失败！");
            }
        }
    }

    /**
     * 当指定下载文件保存目录发生异常时回调，UI线程
     *
     * @param myOkHttpException
     */
    public void onFilePathException(MyOkHttpException myOkHttpException) {
        LogUtil.e(myOkHttpException + "");
    }
}
