package com.mingpinmall.me;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.goldze.common.dmvvm.utils.ImageUtils;


public class DatabingUtils {

    @BindingAdapter(value = {"image"}, requireAll = false)
    public static void imageLoader(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            if (url.endsWith(".gif")) {
                ImageUtils.loadImageAsGIF(imageView, url);
            } else {
                ImageUtils.loadImage(imageView, url);
            }
        }

    }

}
