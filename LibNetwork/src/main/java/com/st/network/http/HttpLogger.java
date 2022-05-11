package com.st.network.http;

import com.st.network.utils.JsonUtil;
import com.st.network.utils.LogUtil;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * create by Dennis
 * on 2019-12-24
 * description：
 **/
public class HttpLogger implements HttpLoggingInterceptor.Logger{

    private StringBuilder mMessage = new StringBuilder();
    @Override
    public void log(String message) {
        LogUtil.d(message);
    }
}
