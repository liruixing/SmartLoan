package com.mmt.smartloan.http.bean.request;

/**
 * create by Dennis
 * on 2022/4/12
 * description：
 **/
public class VerCodeRequest extends BaseRequest{
    private String mobile;
    private int type;

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

}
