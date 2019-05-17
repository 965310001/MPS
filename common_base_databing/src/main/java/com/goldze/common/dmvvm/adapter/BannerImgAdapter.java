package com.goldze.common.dmvvm.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.goldze.common.dmvvm.R;
import com.goldze.common.dmvvm.utils.DisplayUtil;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.goldze.common.dmvvm.utils.Utils;
import com.socks.library.KLog;


/**
 * @author GuoFeng
 * @date :2019/1/16 18:20
 * @description: 轮播图默认适配器 适合只加载一张图的Banner
 */
public class BannerImgAdapter implements CBViewHolderCreator {

    Holder<String> holder;

    @Override
    public Holder createHolder(View itemView) {

        holder = new Holder<String>(itemView) {
            FrameLayout layout;
            ImageView imageView;

            @Override
            protected void initView(View itemView) {
                if (getImageView() != null) {
                    layout = itemView.findViewById(R.id.ll_img_parent);
                    layout.removeAllViews();
                    imageView = getImageView();
                    layout.addView(imageView);
                } else {
                    imageView = itemView.findViewById(R.id.image);
                }
            }

            @Override
            public void updateUI(String data) {
                /*KLog.i(data);*/
                ImageUtils.loadImage(Utils.getApplication(), data, new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        params.width = DisplayUtil.getScreenWidth(Utils.getApplication());
                        params.height = params.width * resource.getIntrinsicHeight() / resource.getIntrinsicWidth();
                        imageView.setImageDrawable(resource);
                        imageView.setLayoutParams(params);
                        imageView.invalidate();
                    }
                });
            }
        };
        return holder;
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_banner_img;
    }

    /**
     * 可以传入自定义的ImageView
     */
    public ImageView getImageView() {
        return null;
    }
}

