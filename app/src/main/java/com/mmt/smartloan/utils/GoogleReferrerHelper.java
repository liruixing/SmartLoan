package com.mmt.smartloan.utils;

import android.Manifest;
import android.accounts.Account;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.RemoteException;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.lrx.module_base.utils.SPUtils;
import com.mmt.smartloan.MyApplication;
import com.mmt.smartloan.config.AccountInfo;
import com.mmt.smartloan.http.APIManager;
import com.mmt.smartloan.http.bean.request.AddActiveRequest;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * create by Dennis
 * on 2022/4/17
 * description：
 **/
public class GoogleReferrerHelper {
    private static GoogleReferrerHelper instance = null;

    public static GoogleReferrerHelper getIns() {
        if (instance == null) {
            instance = new GoogleReferrerHelper();
        }
        return instance;
    }

    private static final String TAG = "--- ReferrerHelper";
    private InstallReferrerClient mReferrerClient;

    public void start(Context context) {
        Log.d(TAG, "start");
        if (mReferrerClient != null) {
            end();
        }
        mReferrerClient = InstallReferrerClient.newBuilder(context).build();
        mReferrerClient.startConnection(new InstallReferrerStateListener() {
            @Override
            public void onInstallReferrerSetupFinished(int responseCode) {
                Log.d(TAG, String.format("onInstallReferrerSetupFinished, responseCode: %d", responseCode));
                switch (responseCode) {
                    case InstallReferrerClient.InstallReferrerResponse.OK:
                        // Connection established.
                        getArgs();
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                        // API not available on the current Play Store app.
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                        // Connection couldn't be established.
                        break;
                }
            }

            @Override
            public void onInstallReferrerServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                Log.d(TAG, "onInstallReferrerServiceDisconnected");
            }
        });
    }

    public void getArgs() {
        try {
            ReferrerDetails response = mReferrerClient.getInstallReferrer();
            String referrerUrl = response.getInstallReferrer();
            long referrerClickTime = response.getReferrerClickTimestampSeconds();
            long appInstallTime = response.getInstallBeginTimestampSeconds();
            boolean instantExperienceLaunched = response.getGooglePlayInstantParam();

            Map<String, Object> args = new HashMap<>();
            args.put("referrerUrl", referrerUrl);
            args.put("referrerClickTime", referrerClickTime);
            args.put("installTime", appInstallTime);
            args.put("instantExperienceLaunched", instantExperienceLaunched);
            Log.d(TAG, String.format("--- args: %s", new JSONObject(args).toString()));

            AccountInfo.INSTANCE.setInstallReferce(referrerUrl);
            AccountInfo.INSTANCE.setReferrerClickTime(referrerClickTime * 1000);
            AccountInfo.INSTANCE.setInstallStartTime(appInstallTime * 1000);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传参数
     */
    public void addActive() {
        boolean isFirst = (boolean) SPUtils.get(MyApplication.Companion.getAppContext(), AccountInfo.IS_FIRST_KEY, true);
        if(!isFirst)return;
        AddActiveRequest request = new AddActiveRequest();

        if(AccountInfo.INSTANCE.getReferrerClickTime() <1){
            request.setInstallReferceClickTime("");
        }else{
            request.setInstallReferceClickTime(new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(
                    new Date(AccountInfo.INSTANCE.getReferrerClickTime())));
        }


        if(AccountInfo.INSTANCE.getInstallStartTime() <1){
            request.setInstallStartTime("");
        }else {
            request.setInstallStartTime(
                    new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(
                            new Date(AccountInfo.INSTANCE.getInstallStartTime())));
        }

        if (ActivityCompat.checkSelfPermission(MyApplication.Companion.getAppContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            request.setSerial("");
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                request.setSerial(Build.getSerial() + "");
            }
        }
        request.setReleaseDate(Build.TIME);
        request.setDeviceName(Build.DEVICE);
        request.setPhoneBrand(Build.BRAND);
        request.setRooted(ConfigUtil.isRoot());
        request.setSysVersion(Build.VERSION.RELEASE);
        request.setLanguage(ConfigUtil.getLanguageCode(MyApplication.Companion.getAppContext()));
        request.setLocaleDisplayLanguage(ConfigUtil.getDisplayLanguage(MyApplication.Companion.getAppContext()));

        request.setLocaleIso3Country(Locale.getDefault().getDisplayCountry());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            request.setTimeZone(ConfigUtil.getCurrentTimeZone());
            request.setTimeZoneId(ConfigUtil.getCurrentTimeZoneId());
        }
        request.setApiLevel(Build.VERSION.SDK_INT);
        request.setNetworkOperatorName(ConfigUtil.getOperators(MyApplication.Companion.getAppContext()));

        APIManager.getInstance().addActive(request).subscribe(
                b->{
                    if(b){
                        SPUtils.put(MyApplication.Companion.getAppContext(),AccountInfo.IS_FIRST_KEY,false);
                    }
                },
                e->{},
                ()->{}
        );

    }
    public void end() {
        if (mReferrerClient != null) {
            mReferrerClient.endConnection();
            mReferrerClient = null;
        }
    }
}