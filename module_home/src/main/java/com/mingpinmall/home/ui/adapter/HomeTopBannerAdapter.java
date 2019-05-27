package com.mingpinmall.home.ui.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.goldze.common.dmvvm.adapter.BaseBannerAdapter;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.home.R;
import com.mingpinmall.home.databinding.ViewHomeItemTopbannerBinding;

/**
 * 功能描述：首页顶部轮播图适配器
 * *@author 小斌
 *
 * @date 2019/5/5
 **/
public class HomeTopBannerAdapter extends BaseBannerAdapter<String, ViewHomeItemTopbannerBinding> {

    float maxDbi = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.view_home_item_topbanner;
    }

    @Override
    protected void convert(ViewHomeItemTopbannerBinding binding, String url, int position) {
        Glide.with(binding.getRoot().getContext())
                .load(url)
                .apply(new RequestOptions()
                        .fitCenter()
                        .placeholder(R.drawable.ic_loading_image)
                        .error(new ColorDrawable(Color.WHITE)))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        binding.iv0.setImageDrawable(resource);
                        int imageWidth = resource.getIntrinsicWidth();
                        int imageHeight = resource.getIntrinsicHeight();
                        float dbis = (float) imageWidth / (float) imageHeight;
                        if (dbis >= maxDbi) {
                            if (maxDbi < 0) {
                                maxDbi = dbis;
                                LiveBus.getDefault().postEvent("banner_w_h", dbis);
                            }
                            return;
                        } else {
                            maxDbi = dbis;
                            LiveBus.getDefault().postEvent("banner_w_h", dbis);
                        }
                    }
                });
    }
}
