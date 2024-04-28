/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yingyangfly.baselib.net.convert;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.yingyangfly.baselib.net.BaseResp;
import com.yingyangfly.baselib.utils.GsonUtil;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author: gold
 * @time: 2021/12/16 下午1:34
 * @description:  自定义GsonResponseBodyConverter 处理响应码不为200时，data解析处理
 */
final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
  private final Gson gson;
  private final TypeAdapter<T> adapter;

  GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
    this.gson = gson;
    this.adapter = adapter;
  }

  @Override
  public T convert(ResponseBody value) throws IOException {
    String strJsong = value.string();
    try {
      BaseResp bean;
      bean = gson.fromJson(strJsong, BaseResp.class);
      if (bean.getCode() != 200) {
        strJsong = GsonUtil.GsonString(bean);
      }
      T result = adapter.fromJson(strJsong);
      return result;
    } finally {
      value.close();
    }
  }

}
