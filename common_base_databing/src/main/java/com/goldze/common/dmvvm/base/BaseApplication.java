package com.goldze.common.dmvvm.base;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bugtags.library.Bugtags;
import com.bugtags.library.BugtagsOptions;
import com.bumptech.glide.Glide;
import com.goldze.common.dmvvm.BuildConfig;
import com.goldze.common.dmvvm.base.mvvm.stateview.ErrorState;
import com.goldze.common.dmvvm.base.mvvm.stateview.LoadingState;
import com.goldze.common.dmvvm.manage.AppManager;
import com.goldze.common.dmvvm.utils.Utils;
import com.socks.library.KLog;
import com.squareup.leakcanary.LeakCanary;
import com.tqzhang.stateview.core.LoadState;



/**
 * @author GuoFeng
 * @date :2019/1/15 10:42
 * @description:
 */
public abstract class BaseApplication extends Application implements Runnable {

    @Override
    public void onCreate() {
        super.onCreate();

        setApplication(this, this);

        init();
    }

    /**
     * @param application
     * @param runnable    用于初始化第三方库，防止耗时初始化操作
     */
    public synchronized void setApplication(@NonNull Application application, @NonNull Runnable runnable) {
        Utils.init(application);
        setApplication(application);
        runnable.run();
    }

    /**
     * 当主工程没有继承BaseApplication时，可以使用setApplication方法初始化BaseApplication
     *
     * @param application
     */
    public synchronized void setApplication(@NonNull Application application) {
        application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                AppManager.getInstance().addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                AppManager.getInstance().removeActivity(activity);
            }
        });
    }

    protected void init() {
        //ARouter
        initARouter();

        //KLog
        initKLog();

        //CrashManage
        if (!BuildConfig.DEBUG) {
          /*  CrashHandlerManage.getInstance()
                    .init(getApplicationContext());*/
        }

        //注册 LoadState
        initLoadState();

        //Bugtags
        initBugtags();

        /*LeakCanary 内存泄漏检测*/
        initLeakCanary();

    }

    private void initLeakCanary() {
        if (!LeakCanary.isInAnalyzerProcess(this)) LeakCanary.install(this);
    }

    private void initBugtags() {
        BugtagsOptions options = new BugtagsOptions.Builder().
                trackingLocation(true).//是否获取位置
                trackingCrashLog(true).//是否收集crash
                trackingConsoleLog(true).//是否收集console log
                trackingUserSteps(true).//是否收集用户操作步骤
                enableCapturePlus(true).
                build();
        Bugtags.addUserStep("custom step");
        Bugtags.start(BuildConfig.BUGTAGS_APPKEY, this, Bugtags.BTGInvocationEventNone, options);
    }

    private void initLoadState() {
        new LoadState.Builder()
                .register(new ErrorState())
                .register(new LoadingState())
                .setDefaultCallback(LoadingState.class)
                .build();
    }

    private void initKLog() {
        KLog.init(BuildConfig.DEBUG, "TAG11");
    }

    private void initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();  // 打印日志
            ARouter.openDebug(); // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(Utils.getApplication());// 尽可能早，推荐在Application中初始化
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex分包方法 必须最先初始化
        MultiDex.install(this);
    }

    /**
     * 程序终止的时候执行
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        //退出应用
        AppManager.getInstance().AppExit();
    }

    /*当应用所有UI隐藏时应该释放UI上所有占用的资源*/
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN == level) {
            Glide.get(this).clearMemory();
            Glide.get(this).onTrimMemory(level);
        }
    }
}