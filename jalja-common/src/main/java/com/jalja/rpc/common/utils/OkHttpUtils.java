package com.jalja.rpc.common.utils;

import com.jalja.rpc.common.rpc.RemoteInvocation;
import com.jalja.rpc.common.seria.IJaljaSerializable;
import com.jalja.rpc.common.seria.JDKSerializable;
import com.jalja.rpc.common.seria.SerializableSPI;
import okhttp3.*;

/**
 * @author XL
 * @title: OkHttpUtils
 * @projectName jalja-rpc
 * @date 2020/6/2417:26
 * @description: TODO
 */
public class OkHttpUtils {
    private static OkHttpClient client = new OkHttpClient();
    public static Object post(String url, RemoteInvocation invocation, IJaljaSerializable serializable) {
        try {
            RequestBody body = RequestBody.create(SerializableSPI.getIJaljaSerializable(JDKSerializable.class).serialize(invocation));
            Request request = new Request.Builder()
                    .url(url)
                    .post(body) //post请求
                    .build();
            final Call call = client.newCall(request);
            Response response = call.execute();
            if (response.body() == null) {
                return null;
            }
            return serializable.deserialize(response.body().bytes(),Object.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("http 接口调用失败", e);
        }
    }
}
