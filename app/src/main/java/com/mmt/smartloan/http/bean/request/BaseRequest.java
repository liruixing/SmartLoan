package com.mmt.smartloan.http.bean.request;

import com.appsflyer.AppsFlyerLib;
import com.lrx.module_base.base.BaseData;
import com.mmt.smartloan.MyApplication;
import com.mmt.smartloan.config.AccountInfo;
import com.mmt.smartloan.utils.ConfigUtil;

/**
 * Created by WangKang.
 * Date: 2020/5/6 9:02
 * describle:
 */
public class BaseRequest extends BaseData {
    private int versionCode = ConfigUtil.getVersionCode(MyApplication.Companion.getAppContext());
    private String androidId = ConfigUtil.getAndroidID(MyApplication.Companion.getAppContext());
    private int adrVersion = ConfigUtil.getAdrVersion(MyApplication.Companion.getAppContext());
    private String appVersion = ConfigUtil.getAppVersion(MyApplication.Companion.getAppContext());
    private String channelId = "SmartLoan";
    private String imei  = ConfigUtil.getIMEI(MyApplication.Companion.getAppContext());
    private String installReferce = AccountInfo.INSTANCE.getInstallReferce();
    private String packageName = "com.mmt.smartloan";
    private String afid = AppsFlyerLib.getInstance().getAppsFlyerUID(MyApplication.Companion.getAppContext());


    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public int getAdrVersion() {
        return adrVersion;
    }

    public void setAdrVersion(int adrVersion) {
        this.adrVersion = adrVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getInstallReferce() {
        return installReferce;
    }

    public void setInstallReferce(String installReferce) {
        this.installReferce = installReferce;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAfid() {
        return afid;
    }

    public void setAfid(String afid) {
        this.afid = afid;
    }
}
