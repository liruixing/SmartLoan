package com.mmt.smartloan.http.bean.request;

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

    private String phoneNumber;
    private String userId;
    private List<EventLogItem> eventList;
    private String merchantID = "000";
    private String country;
    private String utm_source = AccountInfo.INSTANCE.getInstallReferce();

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
}
