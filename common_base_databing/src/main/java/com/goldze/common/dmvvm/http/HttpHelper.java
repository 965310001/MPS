package com.goldze.common.dmvvm.http;

import android.content.Context;

import com.goldze.common.dmvvm.http.cookie.CookieJarImpl;
import com.goldze.common.dmvvm.http.cookie.store.PersistentCookieStore;
import com.goldze.common.dmvvm.http.interceptor.BaseInterceptor;
import com.goldze.common.dmvvm.http.interceptor.CacheInterceptor;
import com.goldze.common.dmvvm.http.interceptor.TokenInterceptor;
import com.goldze.common.dmvvm.http.interceptor.logging.Level;
import com.goldze.common.dmvvm.http.interceptor.logging.LoggingInterceptor;


import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.goldze.common.dmvvm.utils.TUtil.checkNotNull;

/**
 * 对RetrofitClient 的重新封装
 * 必须在Application 中注册
 * new HttpHelper.Builder(this)
 * .initOkHttp()
 * .createRetrofit(BuildConfig.APP_URL)
 * .build();
 */
public class HttpHelper {

    private static volatile HttpHelper mHttpHelper = null;

    private static OkHttpClient mOkHttpClient;

    //超时时间
    private static final int DEFAULT_TIMEOUT = 20;
    //缓存时间
    private static final int CACHE_TIMEOUT = 10 * 1024 * 1024;
    //缓存路径
    private static final String CACHEPATH = "goldze_cache";

    private static Retrofit mRetrofit;

    private static OkHttpClient.Builder mBuilder;

    private static String BASE_URL;

    private HttpHelper() {
    }

    public static HttpHelper getInstance() {
        if (mHttpHelper == null) {
            synchronized (HttpHelper.class) {
                if (mHttpHelper == null) {
                    mHttpHelper = new HttpHelper();
                }
            }
        }
        return mHttpHelper;
    }

    public static void init(Context context, String baseUrl) {
        new HttpHelper.Builder(context)
                .initOkHttp()
                .createRetrofit(baseUrl)
                .build();
    }

    public static class Builder {
        private OkHttpClient mOkHttpClient;

        private OkHttpClient.Builder mBuilder;

        private Retrofit mRetrofit;

        private Context mContext;

        private File mHttpCacheDirectory;

        private Cache mCache;

        private Map<String, String> mHeaders;

        public Builder(Context context) {
            this.mContext = context;
        }

        /**
         * create OkHttp 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志
         *
         * @return Builder
         */
        public Builder initOkHttp() {
            if (mBuilder == null) {
                synchronized (HttpHelper.class) {
                    if (mBuilder == null) {
                        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
                        if (null == mHttpCacheDirectory) {
                            mHttpCacheDirectory = new File(mContext.getCacheDir(), CACHEPATH);
                        }
                        if (null == mCache) {
                            mCache = new Cache(mHttpCacheDirectory, CACHE_TIMEOUT);
                        }
                        mBuilder = new OkHttpClient.Builder()
                                .cache(mCache)
                                .cookieJar(new CookieJarImpl(new PersistentCookieStore(mContext)))
                                .addInterceptor(new BaseInterceptor(mHeaders))
                                .addInterceptor(new CacheInterceptor(mContext))
                                .addInterceptor(new TokenInterceptor())
                                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                                .addInterceptor(new LoggingInterceptor
                                        .Builder()//构建者模式
                                        .loggable(true) //是否开启日志打印 BuildConfig.DEBUG
                                        .setLevel(Level.BODY) //打印的等级
                                        .log(Platform.INFO) // 打印类型
                                        .request("Request") // request的Tag
                                        .response("Response")// Response的Tag
                                        .addHeader("log-header", "I am the log request header.") // 添加打印头, 注意 key 和 value 都不能是中文
                                        .build()
                                ).connectTimeout(30, TimeUnit.SECONDS)
                                .writeTimeout(30, TimeUnit.SECONDS)
                                .readTimeout(30, TimeUnit.SECONDS)
                        ;
                    }
                }
            }

            return this;
        }

        /**
         * 添加拦截器
         *
         * @param mInterceptor Interceptor
         * @return Builder
         */
        public Builder addInterceptor(Interceptor mInterceptor) {
            checkNotNull(mInterceptor);
            this.mBuilder.addNetworkInterceptor(mInterceptor);
            return this;
        }

        public Builder addHeaders(Map<String, String> headers) {
            checkNotNull(headers);
            this.mHeaders = headers;
            return this;
        }

        /**
         * create retrofit
         *
         * @param baseUrl baseUrl
         * @return Builder
         */
        public Builder createRetrofit(String baseUrl) {
            checkNotNull(baseUrl);
            Retrofit.Builder builder = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl);
            BASE_URL = baseUrl;
            this.mOkHttpClient = mBuilder.build();
            this.mRetrofit = builder.client(mOkHttpClient)
                    .build();
            return this;
        }

        public void build() {
            HttpHelper.getInstance().build(this);
        }
    }

    private void build(Builder builder) {
        checkNotNull(builder);
        checkNotNull(builder.mBuilder);
        checkNotNull(builder.mOkHttpClient);
        checkNotNull(builder.mRetrofit);
        mBuilder = builder.mBuilder;
        mOkHttpClient = builder.mOkHttpClient;
        mRetrofit = builder.mRetrofit;
    }

    public <T> T create(Class<T> clz) {
        checkNotNull(clz);
        checkNotNull(mRetrofit);
        return mRetrofit.create(clz);
    }

}