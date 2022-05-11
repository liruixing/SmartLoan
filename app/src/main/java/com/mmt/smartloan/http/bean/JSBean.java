package com.mmt.smartloan.http.bean;

/**
 * create by Dennis
 * on 2022/4/18
 * description：
 **/
public class JSBean {
    private String action;
    private String id;
    private Event data;
    private String callback;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Event getData() {
        return data;
    }

    public void setData(Event data) {
        this.data = data;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }
}
