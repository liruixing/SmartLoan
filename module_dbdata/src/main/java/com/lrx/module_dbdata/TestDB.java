package com.lrx.module_dbdata;

import org.litepal.crud.LitePalSupport;

/**
 * create by Dennis
 * on 2020-01-06
 * description：id字段的值始终为当前记录的行号（下标从1开始），即使我们在实体类中定义了int或者long类型的id字段，
 *   在添加数据时人为的设置id的值为100等其他值，查询数据库发现该id字段的值设置是无效的，她始终等于该条记录所在的行id，即第几条记录
 **/
public class TestDB extends LitePalSupport {
    private String mCreateTime;
    private String mTitle;
    private String mContent;
    private int mNumber;

    public String getCreateTime() {
        return mCreateTime;
    }

    public void setCreateTime(String createTime) {
        mCreateTime = createTime;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public int getNumber() {
        return mNumber;
    }

    public void setNumber(int number) {
        mNumber = number;
    }
}
