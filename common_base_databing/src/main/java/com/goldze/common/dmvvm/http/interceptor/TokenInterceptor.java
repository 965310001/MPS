package com.goldze.common.dmvvm.http.interceptor;

import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * @author GuoFeng
 * @date : 2019/2/22 17:08
 * @description: Token失效的跳转
 */
public class TokenInterceptor implements Interceptor {

    private JSONObject jsonObject;
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        /*******************/
        Request request = chain.request();
        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }
        String bodyString = buffer.clone().readString(charset);

        /*************** 查看url *************/
//        if (request.method().equals("POST")) {
//            KLog.i(request.body().toString());
//            RequestBody requestBody = request.body();
//            MediaType rContentType = null;
//            if (requestBody != null) {
//                rContentType = request.body().contentType();
//            }
//            String rSubtype = null;
//            if (rContentType != null) {
//                rSubtype = rContentType.subtype();
//            }
//        }
        /****************************/


        KLog.i("\n" + request.url() + "\n" + bodyString);
        try {
            jsonObject = new JSONObject(bodyString);
            if (jsonObject.has("login") &&
                    jsonObject.get("error").toString().contains("登录")) {
                response.body().close();
                failed();
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            KLog.i(e.toString());
        }
        return response;
        /*******************/


//        Request request = chain.request();
//        Response response = chain.proceed(request);
//        /*KLog.i(response.body().string());*/
//        try {
//            jsonObject = new JSONObject(response.body().string());
//            if (jsonObject.has("error_desc") &&
//                    jsonObject.get("error_desc").toString().contains("Token 无效")) {
//                failed();
//                return null;
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//            KLog.i(e.toString());
//        }
//        response.body().close();
//        KLog.i("TOKEN");
//        Request newRequest = request.newBuilder()
//                .method(request.method(), request.body())
//                .url(request.url())
//                .build();
//        return chain.proceed(newRequest);
    }

    private void failed() {
        KLog.i("请登录");
        ActivityToActivity.toActivity(ARouterConfig.LOGINACTIVITY);
    }
}
