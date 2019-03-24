package com.goldze.common.dmvvm.manage;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import java.util.Stack;

/**
 * @author GuoFeng
 * @date :2019/1/15 9:56
 * @description: activity堆栈式管理
 */
public final class AppManager {

    static Stack<Activity> activityStack;
    static Stack<Fragment> fragmentStack;
    static AppManager sInstance;

    private AppManager() {
    }

    public static AppManager getInstance() {
        if (sInstance == null) {
            synchronized (AppManager.class) {
                if (sInstance == null) {
                    sInstance = new AppManager();
                }
            }
        }
        return sInstance;
    }

    public static Stack<Activity> getActivityStack() {
        return activityStack;
    }

    public static Stack<Fragment> getFragmentStack() {
        return fragmentStack;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(@NonNull Activity activity) {
        if (null == activityStack) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity(@NonNull Activity activity) {
        if (null != activity) {
            activityStack.remove(activity);
        }
    }

    /**
     * 是否有activity
     */
    public boolean isActivity() {
//        return activityStack != null ? !activityStack.isEmpty() : false;

        return activityStack != null && !activityStack.isEmpty();
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        return activityStack.lastElement();
    }


    /**
     * 结束指定的Activity
     */
    public void finishActivity(@NonNull Activity activity) {
        if (null != activity && !activity.isFinishing()) {
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(@NonNull Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (Activity activity : activityStack) {
            finishActivity(activity);
        }
        activityStack.clear();
    }


    /**
     * 获取指定的Activity
     */
    public Activity getActivity(@NonNull Class<?> cls) {
        if (activityStack != null)
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        return null;
    }

    /**
     * 添加Fragment到堆栈
     */
    public void addFragment(@NonNull Fragment fragment) {
        if (null == fragmentStack) {
            fragmentStack = new Stack<>();
        }
        fragmentStack.add(fragment);
    }

    /**
     * 移除指定的Fragment
     */
    public void removeFragment(@NonNull Fragment fragment) {
        if (null != fragment) {
            fragmentStack.remove(fragment);
        }
    }

    /**
     * 是否有Fragment
     */
    public boolean isFragment() {
//        return fragmentStack != null ? !fragmentStack.isEmpty() : false;
        return fragmentStack != null && !fragmentStack.isEmpty();
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Fragment currentFragment() {
        return fragmentStack != null ? fragmentStack.lastElement() : null;
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
            android.os.Process.killProcess(android.os.Process.myPid());
            /*杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid());
            调用 System.exit(n) 实际上等效于调用：
            Runtime.getRuntime().exit(n)
            finish() 是Activity的类方法，仅仅针对Activity，当调用finish() 时，只是将活动推向后台，并没有立即释放内存，活动的资源并没有被清理；
            当调用System.exit(0) 时，退出当前Activity并释放资源（内存），但是该方法不可以结束整个App如有多个Activty或者有其他组件service等不会结束。
            其实android的机制决定了用户无法完全退出应用，当你的application最长时间没有被用过的时候，android自身会决定将application关闭了。
            System.exit(0);*/
        } catch (Exception e) {
            activityStack.clear();
            e.printStackTrace();
        }
    }


}
