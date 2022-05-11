package com.mmt.smartloan.utils;

import android.os.Looper;

/**
 * Created by WangKang.
 * Date: 2020/4/30 16:44
 * describle:
 */
public class AppUtils {
    /**
     * 是否是主线程
     *
     * @return
     */
    public static boolean isMainThread() {
        if (Looper.getMainLooper() == Looper.myLooper())
            return true;
        return false;
    }
}
