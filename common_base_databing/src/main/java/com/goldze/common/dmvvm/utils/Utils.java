package com.goldze.common.dmvvm.utils;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author GuoFeng
 * @date :2019/1/15 11:48
 * @description: 常用工具类(Application)
 */
public class Utils {

    private static Application sApplication;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param application 上下文
     */
    public static void init(@NonNull final Application application) {
        Utils.sApplication = application;
    }

    /**
     * 获取Application
     *
     * @return Application
     */
    public static Application getApplication() {
        if (sApplication != null) {
            return sApplication;
        }
        throw new NullPointerException("should be initialized in application");
    }

    public static Context getContext() {
        if (sApplication != null) {
            return sApplication.getApplicationContext();
        }
        throw new NullPointerException("should be initialized in application");
    }
}
