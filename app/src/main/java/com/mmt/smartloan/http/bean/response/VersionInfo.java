package com.mmt.smartloan.http.bean.response;

import com.lrx.module_base.base.BaseData;

/**
 * create by Dennis
 * on 2022/4/17
 * description：
 **/
public class VersionInfo extends BaseData {
    private String link;
    private boolean forcedUpdate;
    private String versionCode;
    private String versionName;
    private int h5VersionCode;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isForcedUpdate() {
        return forcedUpdate;
    }

    public void setForcedUpdate(boolean forcedUpdate) {
        this.forcedUpdate = forcedUpdate;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getH5VersionCode() {
        return h5VersionCode;
    }

    public void setH5VersionCode(int h5VersionCode) {
        this.h5VersionCode = h5VersionCode;
    }
}
