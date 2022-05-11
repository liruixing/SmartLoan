package com.mmt.smartloan.http.bean.response;

import com.lrx.module_base.base.BaseData;

/**
 * create by Dennis
 * on 2022/4/17
 * description：
 **/
public class VerCode extends BaseData {
    private String code;
    private boolean enableAutoLogin;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isEnableAutoLogin() {
        return enableAutoLogin;
    }

    public void setEnableAutoLogin(boolean enableAutoLogin) {
        this.enableAutoLogin = enableAutoLogin;
    }
}
