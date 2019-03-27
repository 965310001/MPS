package com.mingpinmall.classz;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.goldze.common.dmvvm.utils.ImageUtils;

public class DatabingUtils {

    @BindingAdapter({"bind:image"})
    public static void imageLoader(ImageView imageView, String url) {
        ImageUtils.loadImage(imageView, url);
    }
}
