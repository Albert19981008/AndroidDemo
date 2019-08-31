package com.example.readhub.data;

import android.support.annotation.NonNull;


/**
 * 数据新闻类
 */
public class News {

    private String mHeadline;   //文章标题

    private String mUrl;        //文章链接地址

    private String mSiteSource; //文章来源网站

    private String mType;       //新闻的种类

    private long mTimeStamp;    //该文章发布的时间戳

    private int mId;   //其在网站上的id

    public News(@NonNull String headline, @NonNull String siteSource, @NonNull String url, @NonNull String type, long timeStamp, int id) {
        mHeadline = headline;
        mSiteSource = siteSource;
        mUrl = url;
        mType = type;
        mId = id;
        mTimeStamp = timeStamp;
    }

    public String getHeadline() {
        return mHeadline;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getSiteSource() {
        return mSiteSource;
    }

    public String getType() {
        return mType;
    }

    public long getTimeStamp() {
        return mTimeStamp;
    }

    public int getID() {
        return mId;
    }
}
