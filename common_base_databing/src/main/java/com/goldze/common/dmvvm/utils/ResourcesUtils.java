package com.goldze.common.dmvvm.utils;

import android.content.res.TypedArray;

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

    /*获取String数组*/
    public String[] getStringArray(int id) {
        return Utils.getApplication().getResources().getStringArray(id);
    }

    /*获取图片数组*/
    public TypedArray obtainTypedArray(int id) {
        return Utils.getApplication().getResources().obtainTypedArray(id);
    }

    /*获取图片数组*/
    public int getColor(int id) {
        return Utils.getApplication().getResources().getColor(id);
    }
}
