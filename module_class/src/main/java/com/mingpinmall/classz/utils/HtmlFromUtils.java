package com.mingpinmall.classz.utils;

import android.graphics.drawable.Drawable;

import com.socks.library.KLog;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * TextView HTML的工具类
 */
public class HtmlFromUtils {

    /**
     * 网络请求获取图片
     */
    public static Drawable getImageFromNetwork(String imageUrl) {
//        URL myFileUrl = null;
        Drawable drawable = null;
        try {
            URL myFileUrl = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            drawable = Drawable.createFromStream(is, null);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            KLog.i(e.toString());
            return null;
        }
        return drawable;
    }

}
