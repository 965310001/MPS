package com.goldze.common.dmvvm.http.interceptor;

import com.socks.library.KLog;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author GuoFeng
 * @date : 2019/2/19 11:03
 * @description: 制定的参数添加
 */
public class ParameteInterceptor implements Interceptor {

    /*1为Android，2为IOS，3小程序*/
    String key = "source_type";
    int value = 1;

    public ParameteInterceptor() {
    }

    public ParameteInterceptor(String key, int value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        HttpUrl.Builder builder = oldRequest.url().newBuilder();
        builder.addQueryParameter(key, String.valueOf(value));
        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(builder.build())
                .build();
        KLog.i("------------ParameteInterceptor-----------------");
        return chain.proceed(newRequest);
    }
}
