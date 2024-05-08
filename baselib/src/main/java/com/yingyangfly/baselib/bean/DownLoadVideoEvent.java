package com.yingyangfly.baselib.bean;

import android.text.TextUtils;

public class DownLoadVideoEvent {
    private String url;

    private String type;

    private String name;

    public DownLoadVideoEvent(String url, String type, String name) {
        this.url = url;
        this.type = type;
        this.name = name;
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

    public String getType() {
        if (TextUtils.isEmpty(type)) {
            return "";
        }
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
