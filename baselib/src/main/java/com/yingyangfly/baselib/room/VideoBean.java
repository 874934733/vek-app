package com.yingyangfly.baselib.room;

import android.text.TextUtils;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * @author 王鹏鹏
 */
@Entity(tableName = "Video")
public class VideoBean implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String date;//时间戳

    private String url;//提取到的链接

    private String shereUrl;//提取前分享链接

    private String name;//作品名称



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        if (TextUtils.isEmpty(date)) {
            return "";
        }
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShereUrl() {
        if (TextUtils.isEmpty(shereUrl)) {
            return "";
        }
        return shereUrl;
    }

    public void setShereUrl(String shereUrl) {
        this.shereUrl = shereUrl;
    }

    public String getName() {
        if (TextUtils.isEmpty(name)) {
            return "";
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
