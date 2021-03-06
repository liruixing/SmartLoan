package com.mmt.smartloan.http.bean.request;

import com.appsflyer.AppsFlyerLib;
import com.mmt.smartloan.BuildConfig;
import com.mmt.smartloan.MyApplication;
import com.mmt.smartloan.config.AccountInfo;
import com.mmt.smartloan.utils.ConfigUtil;

/**
 * create by Dennis
 * on 2022/4/12
 * description：
 **/
public class AddActiveRequest extends BaseRequest{
    private String installReferce = AccountInfo.INSTANCE.getInstallReferce();
    private String installReferceClickTime="";
    private String installStartTime="";
    private int adrVersion = ConfigUtil.getAdrVersion(MyApplication.Companion.getAppContext());
    private String appVersion = ConfigUtil.getAppVersion(MyApplication.Companion.getAppContext());
    private String androidId = ConfigUtil.getAndroidID(MyApplication.Companion.getAppContext());
    private String imei  = ConfigUtil.getIMEI(MyApplication.Companion.getAppContext());
    private String packageName = BuildConfig.APPLICATION_ID;
    private String appName="SmartLoan";
    private String mac = ConfigUtil.getNewMac();
    private String serial="";
    private long releaseDate;
    private String deviceName="";
    private String phoneBrand="";
    private boolean isRooted;
    private String sysVersion="";

    private String language="";
    private String localeDisplayLanguage="";
    private String localeIso3Country="";
    private String localeIso3Language=ConfigUtil.getLocaleIso3Language(MyApplication.Companion.getAppContext(),false);
    private String timeZone="";
    private String timeZoneId="";
    private int apiLevel;
    private String networkOperatorName="";
    private boolean isUsingProxyPort;
    private String afId = AppsFlyerLib.getInstance().getAppsFlyerUID(MyApplication.Companion.getAppContext());
    private String channelId = "SmartLoan";

    public String getInstallReferce() {
        return installReferce;
    }

    public void setInstallReferce(String installReferce) {
        this.installReferce = installReferce;
    }

    public String getInstallReferceClickTime() {
        return installReferceClickTime;
    }

    public void setInstallReferceClickTime(String installReferceClickTime) {
        this.installReferceClickTime = installReferceClickTime;
    }

    public String getInstallStartTime() {
        return installStartTime;
    }

    public void setInstallStartTime(String installStartTime) {
        this.installStartTime = installStartTime;
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

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public long getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(long releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getPhoneBrand() {
        return phoneBrand;
    }

    public void setPhoneBrand(String phoneBrand) {
        this.phoneBrand = phoneBrand;
    }

    public boolean isRooted() {
        return isRooted;
    }

    public void setRooted(boolean rooted) {
        isRooted = rooted;
    }

    public String getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLocaleDisplayLanguage() {
        return localeDisplayLanguage;
    }

    public void setLocaleDisplayLanguage(String localeDisplayLanguage) {
        this.localeDisplayLanguage = localeDisplayLanguage;
    }

    public String getLocaleIso3Country() {
        return localeIso3Country;
    }

    public void setLocaleIso3Country(String localeIso3Country) {
        this.localeIso3Country = localeIso3Country;
    }

    public String getLocaleIso3Language() {
        return localeIso3Language;
    }

    public void setLocaleIso3Language(String localeIso3Language) {
        this.localeIso3Language = localeIso3Language;
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

    public int getApiLevel() {
        return apiLevel;
    }

    public void setApiLevel(int apiLevel) {
        this.apiLevel = apiLevel;
    }

    public String getNetworkOperatorName() {
        return networkOperatorName;
    }

    public void setNetworkOperatorName(String networkOperatorName) {
        this.networkOperatorName = networkOperatorName;
    }

    public boolean isUsingProxyPort() {
        return isUsingProxyPort;
    }

    public void setUsingProxyPort(boolean usingProxyPort) {
        isUsingProxyPort = usingProxyPort;
    }

    public String getAfId() {
        return afId;
    }

    public void setAfId(String afId) {
        this.afId = afId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
