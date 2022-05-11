package com.mmt.smartloan.http.bean;

/**
 * create by Dennis
 * on 2022/4/21
 * description：
 **/
public class Event {
    private String event;
    private String phone;
    private String packageId;
    private boolean isSelectContact;
    private int selectContactIndex;
    private String time;
    private String eventType;
    private String pageName;
    private String eventOption;
    private boolean isUpload;
    private String orderNo;
    private String token;
    private String phoneNumber;
    private String userId;
    private boolean isSubmit;
    private boolean appList;
    private boolean sms;
    private boolean exif;
    private boolean device;
    private boolean contact;
    private boolean location;


    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public boolean isSelectContact() {
        return isSelectContact;
    }

    public void setSelectContact(boolean selectContact) {
        isSelectContact = selectContact;
    }

    public int getSelectContactIndex() {
        return selectContactIndex;
    }

    public void setSelectContactIndex(int selectContactIndex) {
        this.selectContactIndex = selectContactIndex;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getEventOption() {
        return eventOption;
    }

    public void setEventOption(String eventOption) {
        this.eventOption = eventOption;
    }

    public boolean isUpload() {
        return isUpload;
    }

    public void setUpload(boolean upload) {
        isUpload = upload;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isSubmit() {
        return isSubmit;
    }

    public void setSubmit(boolean submit) {
        isSubmit = submit;
    }

    public boolean isAppList() {
        return appList;
    }

    public void setAppList(boolean appList) {
        this.appList = appList;
    }

    public boolean isSms() {
        return sms;
    }

    public void setSms(boolean sms) {
        this.sms = sms;
    }

    public boolean isExif() {
        return exif;
    }

    public void setExif(boolean exif) {
        this.exif = exif;
    }

    public boolean isDevice() {
        return device;
    }

    public void setDevice(boolean device) {
        this.device = device;
    }

    public boolean isContact() {
        return contact;
    }

    public void setContact(boolean contact) {
        this.contact = contact;
    }

    public boolean isLocation() {
        return location;
    }

    public void setLocation(boolean location) {
        this.location = location;
    }
}
