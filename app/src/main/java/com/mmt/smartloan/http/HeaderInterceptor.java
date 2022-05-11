package com.mmt.smartloan.http;

import android.text.TextUtils;

import com.mmt.smartloan.http.bean.HeaderBean;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * create by Dennis
 * on 2020-05-06
 * description：
 **/
public class HeaderInterceptor implements Interceptor {

    private HeaderBean mHeader = new HeaderBean();

    public HeaderInterceptor(HeaderBean token) {
        this.mHeader = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        if(!TextUtils.isEmpty(mHeader.getToken())){
            builder.addHeader("Authorization","Bearer"+mHeader.getToken());
        }
        builder.addHeader("Content-Type",mHeader.getContentType());
        builder.addHeader("packageName",mHeader.getPackageName());
        builder.addHeader("appName",mHeader.getAppName());
        builder.addHeader("afid",mHeader.getAfid());
        builder.addHeader("lang",mHeader.getLang());

        final Request request = builder.build();
        return chain.proceed(request);
    }
}
