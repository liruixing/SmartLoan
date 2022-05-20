package com.mmt.smartloan.http.bean;

/**
 * create by Dennis
 * on 2022/5/20
 * description：
 **/
public class SException extends Exception{
    private int code = 0;

    public SException(int code, String message) {
        super(message);
        this.code = code;
    }

    public SException(String message) {
        super(message);
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
