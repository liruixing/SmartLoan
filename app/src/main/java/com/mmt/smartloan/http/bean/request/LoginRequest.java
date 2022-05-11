package com.mmt.smartloan.http.bean.request;

import android.os.Build;

import com.mmt.smartloan.BuildConfig;
import com.mmt.smartloan.MyApplication;
import com.mmt.smartloan.config.AccountInfo;
import com.mmt.smartloan.utils.ConfigUtil;

import java.util.TimeZone;

/**
 * create by Dennis
 * on 2022/4/12
 * description：
 **/
public class LoginRequest extends BaseRequest{
    private String mobile;
    private String verifyCode;
    private String mac = ConfigUtil.getNewMac();
    private String releaseDate = Build.TIME+"";
    private boolean isRooted = ConfigUtil.isRoot();
    private String timeZone = TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT);
    private String timeZoneId = TimeZone.getDefault().getID();
    private String appName = "smartloan";
    private boolean verified;
    private int adrVersion = ConfigUtil.getAdrVersion(MyApplication.Companion.getAppContext());
    private String androidId = ConfigUtil.getAndroidID(MyApplication.Companion.getAppContext());
    private String appVersion = ConfigUtil.getAppVersion(MyApplication.Companion.getAppContext());
    private String channelId = "SmartLoan";
    private String imei  = ConfigUtil.getIMEI(MyApplication.Companion.getAppContext());
    private String installReferce = AccountInfo.INSTANCE.getInstallReferce();
    private String packageName = BuildConfig.APPLICATION_ID;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isRooted() {
        return isRooted;
    }

    public void setRooted(boolean rooted) {
        isRooted = rooted;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public boolean getVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isVerified() {
        return verified;
    }

    public int getAdrVersion() {
        return adrVersion;
    }

    public void setAdrVersion(int adrVersion) {
        this.adrVersion = adrVersion;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
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
}
