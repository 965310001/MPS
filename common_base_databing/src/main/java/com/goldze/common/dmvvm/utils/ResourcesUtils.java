package com.goldze.common.dmvvm.utils;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AnimRes;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.xuexiang.xui.XUI;

/**
 * @author GuoFeng
 * @date : 2019/1/20 16:07
 * @description: 资源获取工具类
 */
public final class ResourcesUtils {

    private static ResourcesUtils INSTANCE;

    private ResourcesUtils() {
    }

    public static ResourcesUtils getInstance() {
        if (INSTANCE == null) {
            synchronized (ResourcesUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ResourcesUtils();
                }
            }
        }
        return INSTANCE;
    }

    public static Resources getResources() {
        return Utils.getApplication().getResources();
    }

    /**
     * 获取字符串的数组
     *
     * @param resId
     * @return
     */
    public String[] getStringArray(@ArrayRes int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获取数字的数组
     *
     * @param resId
     * @return
     */
    public int[] getIntArray(@ArrayRes int resId) {
        return getResources().getIntArray(resId);
    }

    /*获取图片数组*/
    public TypedArray obtainTypedArray(int resId) {
        return getResources().obtainTypedArray(resId);
    }

    /**
     * 获取Color值
     *
     * @param resId
     * @return
     */
    public int getColor(@ColorRes int resId) {
        return getResources().getColor(resId);
    }


    /**
     * 获取字符串
     *
     * @param resId
     * @return
     */
    public String getString(@StringRes int resId) {
        return getResources().getString(resId);
    }

    /**
     * 获取dimes值
     *
     * @param resId
     * @return
     */
    public float getDimens(@DimenRes int resId) {
        return getResources().getDimension(resId);
    }

    /**
     * 获取资源图片
     *
     * @param resId
     * @return
     */
    public Drawable getDrawable(@DrawableRes int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return Utils.getApplication().getDrawable(resId);
        }
        return getResources().getDrawable(resId);
    }

    /**
     * 获取dimes值【px不会乘以denstiy.】
     *
     * @param resId
     * @return
     */
    public static int getDimensionPixelOffset(@DimenRes int resId) {
        return getResources().getDimensionPixelOffset(resId);
    }

    /**
     * 获取dimes值【getDimensionPixelSize则不管写的是dp还是sp还是px,都会乘以denstiy.】
     *
     * @param resId
     * @return
     */
    public static int getDimensionPixelSize(@DimenRes int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取动画
     *
     * @param resId
     * @return
     */
    public static Animation getAnim(@AnimRes int resId) {
        return AnimationUtils.loadAnimation(Utils.getApplication(), resId);
    }
}
