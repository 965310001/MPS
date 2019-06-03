package com.goldze.common.dmvvm.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * @author GuoFeng
 * @date : 2019/1/22 19:41
 * @description: SharedPreferences工具类
 */
public class SharePreferenceUtil {

    private static final String KEY_SEARCH_LIST = "search_list";
    private static final String KEY_USER = "user";
    private static final String KEY_SETTING_LOGIN = "LOGIN";

    private static final String KEY_SETTING_NO_IMAGE = "no_image";
    private static final String KEY_SETTING_DARK_STYLE = "dark_style";

    /**
     * 获取实例
     *
     * @return
     */
    private static SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(Utils.getApplication());
    }

    /**
     * SP中写入Boolean
     *
     * @param key   键
     * @param value 值
     */
    public static void saveBooleanKeyValue(String key, Boolean value) {
        getPreferences().edit().putBoolean(key, value).apply();
    }

    /**
     * SP中读取Boolean
     *
     * @param key 键
     */
    public static boolean getBooleanKeyValue(String key) {
        return getPreferences().getBoolean(key, false);
    }

    /**
     * SP中写入String
     *
     * @param key   键
     * @param value 值
     */
    public static void saveKeyValue(String key, String value) {
        getPreferences().edit().putString(key, value).apply();
    }


    /**
     * SP中读取String
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code ""}
     */
    public static String getKeyValue(String key) {
        return getPreferences().getString(key, "");
    }

    /**
     * SP中写入Int
     *
     * @param key   键
     * @param value 值
     */
    public static void saveIntKeyValue(String key, int value) {
        getPreferences().edit().putInt(key, value).apply();
    }

    /**
     * SP中读取int
     *
     * @param key    键
     * @param defVal 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public static int getIntKeyValue(String key, int defVal) {
        return getPreferences().getInt(key, defVal);
    }

    public static void saveSearchList(List<String> searchList) {
        getPreferences().edit().putString(KEY_SEARCH_LIST, new Gson().toJson(searchList)).apply();
    }

    //todo 改成数据库存储
    public static List<String> getSearchList() {
        return new Gson().fromJson(getPreferences().getString(KEY_SEARCH_LIST, "[]"), new TypeToken<List<String>>() {
        }.getType());
    }

    /**
     * SP中写入用户资料
     *
     * @param object 对象
     */
    public static void saveUser(Object object) {
        if (object == null) {
            getPreferences().edit().putString(KEY_USER, "").apply();
            saveLogin(false);
            return;
        }
        saveLogin(true);
        getPreferences().edit().putString(KEY_USER, new Gson().toJson(object)).apply();
    }

    /**
     * SP中读取用户资料
     *
     * @param clazz class
     */
    public static Object getUser(Class clazz) {
        return new Gson().fromJson(getPreferences().getString(KEY_USER, null), clazz);
    }

    /**
     * SP 中写入登录状态
     *
     * @param isLogin 值
     */
    public static void saveLogin(boolean isLogin) {
        saveBooleanKeyValue(KEY_SETTING_LOGIN, isLogin);
    }

    /**
     * SP 中读取是否登录
     *
     * @return true：登录     false：未登录
     */
    public static boolean isLogin() {
        return getBooleanKeyValue(KEY_SETTING_LOGIN);
    }


    public static void saveNoImage(boolean isChecked) {
        saveBooleanKeyValue(KEY_SETTING_NO_IMAGE, isChecked);
    }

    public static boolean isNoImage() {
        return getBooleanKeyValue(KEY_SETTING_NO_IMAGE);
    }

    public static void changeDarkStyle(boolean isChecked) {
        saveBooleanKeyValue(KEY_SETTING_DARK_STYLE, isChecked);
    }

    public static boolean isDarkStyle() {
        return getBooleanKeyValue(KEY_SETTING_DARK_STYLE);
    }

    /**
     * 清除
     *
     * @return
     */
    public static boolean isClear() {
        return getPreferences().edit().clear().commit();
    }
}