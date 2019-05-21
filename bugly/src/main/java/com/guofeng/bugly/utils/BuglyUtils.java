package com.guofeng.bugly.utils;

import android.content.Context;

import com.guofeng.bugly.BuildConfig;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Bugly的添加
 */
public class BuglyUtils {

    public static void init(Context context) {
        CrashReport.initCrashReport(context, BuildConfig.BUGLY_APP_ID, BuildConfig.DEBUG);
    }
}
