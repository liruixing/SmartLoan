package com.mmt.smartloan.http.bean.request;

import com.appsflyer.AppsFlyerLib;
import com.mmt.smartloan.BuildConfig;
import com.mmt.smartloan.MyApplication;
import com.mmt.smartloan.config.AccountInfo;
import com.mmt.smartloan.utils.ConfigUtil;

import java.util.Date;
import java.util.List;

/**
 * create by Dennis
 * on 2022/4/12
 * description：
 **/
public class EventLogRequest extends BaseRequest{

    private String packageName = BuildConfig.APPLICATION_ID;
    private String afId = AppsFlyerLib.getInstance().getAppsFlyerUID(MyApplication.Companion.getAppContext());
    private String androidId = ConfigUtil.getAndroidID(MyApplication.Companion.getAppContext());
    private String imei  = ConfigUtil.getIMEI(MyApplication.Companion.getAppContext());
    private String phoneNumber="";
    private String userId="";
    private List<EventLogItem> eventList;
    private String merchantID = "000";
    private String country = ConfigUtil.getCountryCode(MyApplication.Companion.getAppContext());
    private String utm_source = AccountInfo.INSTANCE.getInstallReferce();
    private String channelId = "SmartLoan";

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<EventLogItem> getEventList() {
        return eventList;
    }

    public void setEventList(List<EventLogItem> eventList) {
        this.eventList = eventList;
    }

    public String getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUtm_source() {
        return utm_source;
    }

    public void setUtm_source(String utm_source) {
        this.utm_source = utm_source;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAfId() {
        return afId;
    }

    public void setAfId(String afId) {
        this.afId = afId;
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
}
