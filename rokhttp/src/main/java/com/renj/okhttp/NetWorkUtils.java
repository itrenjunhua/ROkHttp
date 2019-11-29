package com.renj.okhttp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-02-03    10:16
 * <p/>
 * 描述：网络相关工具类
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
/*public*/ class NetWorkUtils {

    /**
     * 获取当前网络的状态
     *
     * @param context 上下文
     * @return 当前网络的状态。具体类型可参照NetworkInfo.State.CONNECTED、NetworkInfo.State.CONNECTED.DISCONNECTED等字段。当前没有网络连接时返回null
     */
    @Nullable
    public static NetworkInfo.State getCurrentNetworkState(Context context) {
        NetworkInfo networkInfo
                = ((ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return networkInfo != null ? networkInfo.getState() : null;
    }

    /**
     * 判断当前网络是否已经连接
     *
     * @param context 上下文
     * @return 当前网络是否已经连接。false：尚未连接
     */
    public static boolean isConnectedByState(Context context) {
        return getCurrentNetworkState(context) == NetworkInfo.State.CONNECTED;
    }
}
