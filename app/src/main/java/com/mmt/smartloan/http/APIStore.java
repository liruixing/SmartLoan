package com.mmt.smartloan.http;

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

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * create by Dennis
 * on 2020-04-22
 * description：在这里定义所有的请求
 **/
public interface APIStore {

    int SUCCESS_CODE = 0;

    String BASE_URL = "http://8.134.38.88:3003/api/";
    String H5_URL = "http://8.134.38.88:3003/";
    String HOME_URL = "http://8.134.38.88:3003/#/";
    String PROVICY_URL = H5_URL+"#/provicy";
    String CONDITION_URL = H5_URL+"#/termsCondition";



    @GET("security/existsByMobile")
    Observable<BaseResponse<CheckMobile>> existsByMobile(@Query("mobile") String mobile);

    @POST("security/getVerifyCode")
    Observable<BaseResponse<VerCode>> getVerifyCode(@Body VerCodeRequest request);

    @POST("security/register")
    Observable<BaseResponse<RegisterInfo>> register(@Body RegisterRequest request);

    @Headers({"Content-Type:application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("security/login")
    Observable<BaseResponse<RegisterInfo>> login(@FieldMap Map<String,Object> request);

    @POST("user/device/addActive")
    Observable<BaseResponse<Boolean>> addActive(@Body AddActiveRequest request);


    @GET("app/getNewVersion")
    Observable<BaseResponse<VersionInfo>> getNewVersion(@Query("packageName") String packageName);

    @POST("log/event-log")
    Observable<BaseResponse> eventLog(@Body EventLogRequest request);

    @Multipart
    @POST("time/upload/zip6in1")
    Observable<BaseResponse<Boolean>> zip6in1(@Part List<MultipartBody.Part> parts,
                                     @Query("md5") String md5,@Query("orderNo") String orderNo);
}
