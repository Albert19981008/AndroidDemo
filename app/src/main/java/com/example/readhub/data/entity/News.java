package com.example.readhub.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


/**
 * 数据新闻类
 */
@Entity(indices = {@Index("id")})
public class News {

    private String headline;   //文章标题

    private String url;        //文章链接地址

    private String siteSource; //文章来源网站

    private String type;       //新闻的种类

    private long timeStamp;    //该文章发布的时间戳

    @PrimaryKey
    private int id;   //其在网站上的id

    public News(@NonNull String headline, @NonNull String siteSource, @NonNull String url, @NonNull String type, long timeStamp, int id) {
        this.headline = headline;
        this.siteSource = siteSource;
        this.url = url;
        this.type = type;
        this.id = id;
        this.timeStamp = timeStamp;
    }

    public String getHeadline() {
        return headline;
    }

    public String getUrl() {
        return url;
    }

    public String getSiteSource() {
        return siteSource;
    }

    public String getType() {
        return type;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public int getId() {
        return id;
    }
}
