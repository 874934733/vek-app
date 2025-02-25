package com.yingyangfly.baselib.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class DownloadUtils {

    private static final String TAG = "DownloadUtils";
    public static final String UA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:91.0) Gecko/20100101 Firefox/91.0";

    public static LinkedList<String> linkedList = new LinkedList<>();

    public static void downloadDialog(String url, String shortUrl, SetOnDownLoadListener setOnDownLoadListener) {
        if (TextUtils.isEmpty(shortUrl) && !TextUtils.isEmpty(url)) {
            shortUrl = url.split("\\?")[0];
        }

        if (isExists(shortUrl)) {
            //下载过了
            ToastUtils.showLong("下载过了,文件在Download目录下!");
            return;
        }

        if (linkedList.contains(url)) {
            return;
        }
        linkedList.add(url);
        //处理下载事件
        download(url, shortUrl, setOnDownLoadListener);
    }

    public static void download(String url, String shortUrl, SetOnDownLoadListener setOnDownLoadListener) {
        if (TextUtils.isEmpty(shortUrl) && !TextUtils.isEmpty(url)) {
            shortUrl = url.split("\\?")[0];
        }

        if (isExists(shortUrl)) {
            //下载过了
            ToastUtils.showLong("下载过了,文件在Download目录下!");
            return;
        }

        Log.i(TAG, "download_videoUrl:" + url);

        String finalShortUrl = shortUrl;
        new Thread(() -> {
            Log.e("wpp", "------------------------>    "+PathUtils.getExternalAppCachePath());
            boolean re = DownloadUtils.downloadVideo(url, PathUtils.getExternalAppCachePath(), finalShortUrl);
            if (re) {
//                ToastUtils.cancel();
//                ToastUtils.showLong("下载成功,文件在Download目录下!");
                String fileName = PathUtils.getExternalAppCachePath() + "/" + EncryptUtils.encryptMD5ToString(finalShortUrl) + ".mp4";
                setOnDownLoadListener.success(fileName);
            }
        }).start();

    }

    /**
     * 下载抖音无水印视频到某个路径下.
     *
     * @param shareInfo    下载链接
     * @param saveToFolder 下载目录
     */
    public static boolean downloadVideo(String shareInfo, String saveToFolder, String shortUrl) {
        //创建目录
        FileUtils.createOrExistsDir(saveToFolder);
        if (isExists(shortUrl)) {
            //下载过了
            return true;
        }
        String fileName = saveToFolder + "/" + EncryptUtils.encryptMD5ToString(shortUrl) + ".mp4";
        Log.e("wpp", "fileName------------------------>    " + fileName);
        File file = new File(fileName);
        File fileTemp = new File(saveToFolder + "/" + EncryptUtils.encryptMD5ToString(shortUrl) + ".temp");
        Map<String, String> headers = new HashMap<>();
        try {
            URL url = new URL(shareInfo);
            //host需要随着变化不然会下载失败
            headers.put("Host", url.getHost());
        } catch (MalformedURLException ignored) {
        }
        headers.put("Connection", "keep-alive");
        headers.put("User-Agent", UA);
        InputStream in = get(shareInfo, headers);
        if (in == null) {
            return false;
        }
        //删除再创建，缓存文件
        FileUtils.delete(fileTemp);
        FileUtils.createOrExistsFile(fileTemp);
        Log.i(TAG, "file_path:" + fileTemp.getAbsolutePath());
        boolean writeRe = FileIOUtils.writeFileFromIS(fileTemp, in);
        if (writeRe) {
            boolean re = FileUtils.copy(fileTemp, file);
            if (re) {
                FileUtils.delete(fileTemp);
                return true;
            }
            FileUtils.delete(file);
        }
        return false;
    }

    public static InputStream get(String url, Map<String, String> headers) {
        try {
            URL serverUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(60000);
            conn.setDoInput(true);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
                Log.i(TAG, "header_key:" + entry.getKey() + ",value:" + entry.getValue());
            }
            int code = conn.getResponseCode();
            String type = conn.getContentType();
            Log.i(TAG, "url:" + url);
            Log.i(TAG, "code:" + code);
            Log.i(TAG, "conn.getContentType():" + conn.getContentType());

            if (code == 302) {
                //如果会重定向，保存302重定向地址，以及Cookies,然后重新发送请求(模拟请求)
                String locationUrl = conn.getHeaderField("Location");
                Log.i(TAG, "locationUrl:" + locationUrl);
                return get(locationUrl, new HashMap<>());
            }

            if (code == 200) {
                return conn.getInputStream();
            } else {
                ToastUtils.showLong("请稍后再试，错误码：" + code);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getContentType(String url) {
        Map<String, String> headers = new HashMap<>();
        try {
            URL mUrl = new URL(url);
            //host需要随着变化不然会下载失败
            headers.put("Host", mUrl.getHost());
        } catch (MalformedURLException ignored) {
        }
        headers.put("User-Agent", UA);
        try {
            URL serverUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoInput(true);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
            int code = conn.getResponseCode();
            String type = conn.getContentType();
            Log.i(TAG, "url:" + url);
            Log.i(TAG, "code:" + code);
            Log.i(TAG, "conn.getContentType():" + type);
            return type;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isExists(String shortUrl) {
        String saveToFolder = PathUtils.getExternalAppCachePath();
//        Log.i(TAG, "isExists:" + shortUrl);
        if (StringUtils.isEmpty(shortUrl)) {
            //下载链接为空
            return false;
        }

        File file = new File(saveToFolder + "/" + EncryptUtils.encryptMD5ToString(shortUrl) + ".mp4");
        if (file.exists()) {
            //下载过了
            return true;
        }
        return false;
    }

    /**
     * 从路径中提取itemId
     *
     * @param url
     * @return
     */
    public static String parseItemIdFromUrl(String url) {
        // https://www.iesdouyin.com/share/video/6519691519585160455/?region=CN&mid=6519692104368098051&u_code=36fi3lehcdfb&titleType=title
        String ans = "";
        String[] firstSplit = url.split("\\?");
        if (firstSplit.length > 0) {
            String[] strings = firstSplit[0].split("/");
            // after video.
            for (String string : strings) {
                if (!TextUtils.isEmpty(string)) {
                    return string;
                }
            }
        }
        return ans;
    }

    public interface SetOnDownLoadListener {
        void success(String path);
    }
}
