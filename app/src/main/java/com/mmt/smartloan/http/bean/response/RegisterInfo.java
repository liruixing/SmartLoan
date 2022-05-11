package com.mmt.smartloan.http.bean.response;

import com.lrx.module_base.base.BaseData;

/**
 * create by Dennis
 * on 2022/4/17
 * description：
 **/
public class RegisterInfo extends BaseData {
    private String token;
    private String userId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
