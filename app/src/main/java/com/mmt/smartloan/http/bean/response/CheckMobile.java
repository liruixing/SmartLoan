package com.mmt.smartloan.http.bean.response;

import com.lrx.module_base.base.BaseData;

/**
 * create by Dennis
 * on 2022/4/17
 * description：
 **/
public class CheckMobile extends BaseData {
    private String mobile;
    private boolean existed;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isExisted() {
        return existed;
    }

    public void setExisted(boolean existed) {
        this.existed = existed;
    }
}
