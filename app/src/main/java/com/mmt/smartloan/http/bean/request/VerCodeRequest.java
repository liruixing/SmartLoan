package com.mmt.smartloan.http.bean.request;

import com.mmt.smartloan.MyApplication;
import com.mmt.smartloan.utils.ConfigUtil;

/**
 * create by Dennis
 * on 2022/4/12
 * description：
 **/
public class VerCodeRequest extends BaseRequest{
    private String mobile;
    private int type;
    private String androidId = ConfigUtil.getAndroidID(MyApplication.Companion.getAppContext());
    private int versionCode = ConfigUtil.getVersionCode(MyApplication.Companion.getAppContext());

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }
}
