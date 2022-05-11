package com.mmt.smartloan.http.bean.request;

/**
 * create by Dennis
 * on 2022/4/12
 * description：
 **/
public class RegisterRequest extends BaseRequest{
    private String mobile;
    private String verifyCode;
    private boolean verified;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
