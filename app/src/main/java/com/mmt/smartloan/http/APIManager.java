package com.mmt.smartloan.http;

import android.text.TextUtils;

import com.appsflyer.AppsFlyerLib;
import com.lrx.module_base.utils.SPUtils;
import com.mmt.smartloan.MyApplication;
import com.mmt.smartloan.config.AccountInfo;
import com.mmt.smartloan.http.bean.HeaderBean;
import com.mmt.smartloan.http.bean.request.AddActiveRequest;
import com.mmt.smartloan.http.bean.request.EventLogRequest;
import com.mmt.smartloan.http.bean.request.LoginRequest;
import com.mmt.smartloan.http.bean.request.RegisterRequest;
import com.mmt.smartloan.http.bean.request.VerCodeRequest;
import com.mmt.smartloan.http.bean.response.BaseResponse;
import com.mmt.smartloan.http.bean.response.CheckMobile;
import com.mmt.smartloan.http.bean.response.RegisterInfo;
import com.mmt.smartloan.http.bean.response.VerCode;
import com.mmt.smartloan.http.bean.response.VersionInfo;
import com.st.network.http.config.RxNetwork;
import com.st.network.http.config.RxNetworkConfig;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * create by Dennis
 * on 2020-04-22
 * description：网络请求管理类
 **/
public class APIManager {

    private static APIManager instance;

    private APIStore mAPIStore;
    private HeaderBean headerBean;

    private APIManager() {
        initNetwork("");
    }

    public static APIManager getInstance() {
        if (instance == null) {
            instance = new APIManager();
        }
        return instance;
    }

    private void initNetwork(String token) {
        headerBean = new HeaderBean();
        if(!TextUtils.isEmpty(token)){
            headerBean.setToken(token);
        }else{
            headerBean.setToken(null);
        }
        headerBean.setAfid(AppsFlyerLib.getInstance().getAppsFlyerUID(MyApplication.Companion.getAppContext()));

        Interceptor headerInterceptor = new HeaderInterceptor(headerBean);
        RxNetworkConfig rxNetworkConfig = new RxNetworkConfig.Builder()
                .setReadTimeOut(20)
                .setWriteTimeOut(30)
                .setConnectTimeOut(10)
                .setCache(false)
                .setEnableSSLSocket(false)
                .setInterceptors(new ArrayList<>(Collections.singletonList(headerInterceptor)))
                .build();
        mAPIStore = RxNetwork.getInstance().createSingleApi(
                APIStore.BASE_URL,
                APIStore.class,
                rxNetworkConfig
        );
    }

    /**
     * 登录成功后 后续请求需要添加一个header
     *
     * @param token
     */
    public void updateToken(String token) {
        initNetwork(token);
    }


    /**
     * 短信验证码
     */
//    public Observable<String> SendCode(){
////        return mAPIStore.SendCode(request)
////                .compose(RxHelper.handleResult());
//    }

    /**
     * 上传文件
     *
     * @param type
     * @return
     */
    public Observable<Boolean> uploadZip6in1(File file,String type,
                                                        String md5,String orderno) {
        List<MultipartBody.Part> parts = new ArrayList<>();
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),
                file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData(type,
                file.getName(), requestFile);
        parts.add(filePart);

        return uploadPromisePart(parts,md5,orderno );
    }

    public Observable<Boolean> uploadPromisePart(List<MultipartBody.Part> parts,
                                        String md5,String orderno) {
        return mAPIStore.zip6in1(parts, md5,orderno).compose(RxHelper.handleResult());
    }
    //检查电话号码
    public Observable<CheckMobile> existsByMobile(String phone){
        return mAPIStore.existsByMobile(phone)
                .compose(RxHelper.handleResult());
    }
    //获取验证码
    public Observable<VerCode> getCode(@NotNull VerCodeRequest request) {
        return mAPIStore.getVerifyCode(request)
                .compose(RxHelper.handleResult());
    }

    //注册
    public Observable<RegisterInfo> register(@NotNull RegisterRequest request) {
        return mAPIStore.register(request)
                .compose(RxHelper.handleResult());
    }
    //登录
    public Observable<RegisterInfo> login(Map<String,Object> request) {
        return mAPIStore.login(request)
                .compose(RxHelper.handleResult());
    }

    //上传日志
    public Observable<BaseResponse>  uploadLog(EventLogRequest request) {
        return mAPIStore.eventLog(request);
    }

    //上传日志
    public Observable<Boolean>  addActive(AddActiveRequest request) {
        return mAPIStore.addActive(request)
                .compose(RxHelper.handleResult());
    }

    //检测更新
    public Observable<VersionInfo>  getNewVersion(String packageName) {
        return mAPIStore.getNewVersion(packageName)
                .compose(RxHelper.handleResult());
    }
}
