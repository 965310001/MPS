package com.goldze.common.dmvvm.http.interceptor;

import android.text.TextUtils;

import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.socks.library.KLog;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author GuoFeng
 * @date :2019/1/15 21:07
 * @description: 基础拦截器
 */
public class BaseInterceptor implements Interceptor {
    private Map<String, String> headers;

    public BaseInterceptor(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request
                .newBuilder();
        if (headers != null && headers.size() > 0) {
            Set<String> keys = headers.keySet();
            for (String headerKey : keys) {
                KLog.i("-----------------------------------" + headers.get(headerKey));
                builder.addHeader(headerKey, headers.get(headerKey)).build();
            }
        }
        String token = SharePreferenceUtil.getKeyValue("TOKEN");
        if (!TextUtils.isEmpty(token)) {
            /*KLog.i("TAG", token);*/
            builder.addHeader("X-ECAPI-Authorization", token);
        }
        KLog.i("-------------------------BaseInterceptor----------------------------");
        //请求信息
        return chain.proceed(builder.build());
//        return chain.proceed(request);
    }
}