package com.yingyangfly.baselib.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author: yaoyongchao
 * @date: 2016/9/8 16:08
 * @description:
 */
public class FileMultipartBuilder {
    public static MultipartBody filesToMultipartBody(List<File> files, int position) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            builder.addFormDataPart("file", "yingyangfly" + position + file.getName().substring(file.getName().indexOf(".")), requestBody);
        }
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }

    public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (File file : files) {
            // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("dirName", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }
}
