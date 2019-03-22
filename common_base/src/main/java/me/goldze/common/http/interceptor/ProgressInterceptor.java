package me.goldze.common.http.interceptor;

import java.io.IOException;

import me.goldze.common.http.download.ProgressResponseBody;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author GuoFeng
 * @date :2019/1/16 10:06
 * @description:
 */
public class ProgressInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .body(new ProgressResponseBody(originalResponse.body()))
                .build();
    }
}

