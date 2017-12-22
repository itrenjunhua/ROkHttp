package com.android.okhttptest.application;

/**
 * ======================================================================
 * 作者：Renj
 * <p/>
 * 创建时间：2017-03-21   1:23
 * <p/>
 * 描述：
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public class Constant {
    // 本地基本路径
    private static final String BASE_LOCALTION_URL = "http://192.168.20.185:8080/MyOkHttpServer/";
    // 文件上传
    public final static String FILE_UP = BASE_LOCALTION_URL + "upload";
    // 文件下载
    public final static String FILE_DOWN = BASE_LOCALTION_URL + "upload/downtest.jpg";
    // 提交JSON数据路径
    public final static String JSON_URL = BASE_LOCALTION_URL + "getjson";


    // 获取天气信息不带参数路径
    public final static String BASE_URL = "http://weather.51wnl.com/weatherinfo/GetMoreWeather";
    // 获取天气信息带参数路径
    public final static String ALL_URL = BASE_URL + "?cityCode=101040100&weatherType=1";
}
