package com.yingyangfly.baselib.room;

import android.text.TextUtils;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * @author 王鹏鹏
 */
@Entity(tableName = "Video")
public class VideoBean implements Serializable {

    @NotNull
    @PrimaryKey
    private String shereUrl;//提取前分享链接

    private String date;//时间戳

    private String url;//提取到的链接

    private String name;//作品名称

    private String type;//1音频 2视频

    //todo 以下三个字段属于暂时无用占位字段，预防后期新增字段数据库升级使用
    private String placeHolderFieldOne;//暂时无用占位字段1


    private String placeHolderFieldTwo;//暂时无用占位字段2

    private String placeHolderFieldThree;//暂时无用占位字段3


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

    public String getType() {
        if (TextUtils.isEmpty(type)) {
            return "";
        }
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlaceHolderFieldOne() {
        if (TextUtils.isEmpty(placeHolderFieldOne)) {
            return "";
        }
        return placeHolderFieldOne;
    }

    public void setPlaceHolderFieldOne(String placeHolderFieldOne) {
        this.placeHolderFieldOne = placeHolderFieldOne;
    }

    public String getPlaceHolderFieldTwo() {
        if (TextUtils.isEmpty(placeHolderFieldTwo)) {
            return "";
        }
        return placeHolderFieldTwo;
    }

    public void setPlaceHolderFieldTwo(String placeHolderFieldTwo) {
        this.placeHolderFieldTwo = placeHolderFieldTwo;
    }

    public String getPlaceHolderFieldThree() {
        if (TextUtils.isEmpty(placeHolderFieldThree)) {
            return "";
        }
        return placeHolderFieldThree;
    }

    public void setPlaceHolderFieldThree(String placeHolderFieldThree) {
        this.placeHolderFieldThree = placeHolderFieldThree;
    }
}
