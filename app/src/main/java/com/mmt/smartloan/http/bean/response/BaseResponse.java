package com.mmt.smartloan.http.bean.response;

/**
 * Created by WangKang.
 * Date: 2020/4/30 16:16
 * describle:
 */
public class BaseResponse<T> {

    //错误编码
    //0 成功
    // other 失败
    private int code;
    //编码描述
    private String msg;

    private boolean existed = false;

    private T data;

    private String key;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isExisted() {
        return existed;
    }

    public void setExisted(boolean existed) {
        this.existed = existed;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
