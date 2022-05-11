package com.mmt.smartloan.http.bean.request;

import com.lrx.module_base.base.BaseData;

/**
 * create by Dennis
 * on 2022/4/26
 * description：
 **/
public class EventLogItem extends BaseData {
    private String eventOption;
    private String eventType;
    private String orderNo;
    private String pageName;
    private String time = System.currentTimeMillis()+"";
    private String longitude = "NaN";
    private String latitude = "NaN";

    public String getEventOption() {
        return eventOption;
    }

    public void setEventOption(String eventOption) {
        this.eventOption = eventOption;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
