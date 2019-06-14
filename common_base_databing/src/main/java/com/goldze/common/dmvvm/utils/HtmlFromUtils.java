package com.goldze.common.dmvvm.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.utils.log.QLog;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TextView HTML的工具类
 */
public class HtmlFromUtils {

    /**
     * TextView 图文处理
     *
     * @param context  上下文
     * @param textView TextView
     * @param text     [http://hao.qudao.com/upload/article/20160120/82935299371453253610.jpg]或[图片id]
     * @param isAppend true:append  false:TextView.setText
     */
    public static void setImageFromNetWork(Context context, TextView textView, String text, boolean isAppend) {
        SpannableString spannableString = new SpannableString(text);
        /*QLog.i(text);*/
        /*匹配图片*/
        Matcher matcher = Pattern.compile("\\[[^\\]]+\\]").matcher(text);
        ImageSpan imageSpan;
        String group, url;
        int length;
        Drawable drawable = null;
        while (matcher.find()) {
            //匹配的内容，例如[http://hao.qudao.com/upload/article/20160120/82935299371453253610.jpg]或[哈哈]
            group = matcher.group();
            length = group.length();
            url = group.substring(1, length - 1);
            try {
                if (group.contains("http")) {
                    drawable = new URLImageParser(textView, context, 0).getDrawable(url); /*网络图片*/
                } else {
                    drawable = Utils.getApplication().getResources().getDrawable(Integer.parseInt(url));/*本地图片*/
                }
            } catch (Exception e) {
                Log.i("TAG", e.toString());
            }
            if (null != drawable) {
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
                spannableString.setSpan(imageSpan, matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        /*保存颜色*/
        List<String> colorList = new ArrayList<>();
        matcher = Pattern.compile("\\<color=[^\\=](.*?)'>(.*?)\\</color>").matcher(text);
        while (matcher.find()) {
            colorList.add(matcher.group(1));
            text.replace(matcher.group(), matcher.group(2));
        }
        /*字体的颜色*/
        matcher = Pattern.compile("\\<color=[^\\=](.*?)'>(.*?)\\</color>").matcher(text);
        while (matcher.find()) {
            QLog.i(matcher.group(0) + "||" + matcher.group(1) + "||" + matcher.group(2));
            spannableString.toString().replace(matcher.group(), matcher.group(2));
            spannableString.setSpan(new ForegroundColorSpan(Color.RED),
                    matcher.start(), matcher.end(),
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        /*字体大小*/

        if (isAppend) {
            textView.append(spannableString);
        } else {
            textView.setText(spannableString);
        }
    }

}
