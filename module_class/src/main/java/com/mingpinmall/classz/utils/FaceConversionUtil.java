package com.mingpinmall.classz.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;

import com.goldze.common.dmvvm.utils.ResourcesUtils;
import com.mingpinmall.classz.R;
import com.socks.library.KLog;
import com.xuexiang.xui.utils.ResUtils;

/**
 * 表情工具类
 */
public class FaceConversionUtil {

    /**
     * 添加表情
     *
     * @param context
     * @param imgId           图片id
     * @param spannableString
     * @return
     */
    public static SpannableString addFace(Context context, int imgId,
                                          String spannableString) {
        if (TextUtils.isEmpty(spannableString)) {
            return null;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                imgId);
        bitmap = Bitmap.createScaledBitmap(bitmap, 35, 35, true);
        ImageSpan imageSpan = new ImageSpan(context, bitmap);
        SpannableString spannable = new SpannableString(spannableString);
        spannable.setSpan(imageSpan, 0, spannableString.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    /**
     * 解析
     **/
    public static String dealExpression(String msg) {
        String[] stringArray = ResUtils.getStringArray(R.array.semoj);
        int length = stringArray.length;
        TypedArray intArray = ResourcesUtils.getInstance().obtainTypedArray(R.array.iemoj);
        msg = msg.trim();
        for (int i = 0; i < length; i++) {
            if (msg.contains(stringArray[i])) {
                msg = msg.replaceAll(stringArray[i], String.format("<img src='%d'/>", intArray.getResourceId(i, 0)));
            }
        }
        KLog.i(msg);
        return msg;
    }

}
