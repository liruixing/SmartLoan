package com.mmt.smartloan.http.bean;

import com.lrx.module_base.base.BaseData;

/**
 * create by Dennis
 * on 2022/4/12
 * description：
 **/
public class HeaderBean extends BaseData {
    private String token;
    private String contentType = "application/json; charset=utf-8";
    private String packageName ="com.mmt.smartloan";
    private String appName="SmartLoan";
    private String afid;//AppsFlyerLib.getInstance().getAppsFlyerUID(instance);
    private String lang="es";

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
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

    public String getAfid() {
        return afid;
    }

    public void setAfid(String afid) {
        this.afid = afid;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
