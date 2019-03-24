package com.goldze.common.dmvvm.adapter;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.goldze.common.dmvvm.R;
import com.goldze.common.dmvvm.utils.ImageUtils;


/**
 * @author GuoFeng
 * @date :2019/1/16 18:20
 * @description: 轮播图默认适配器 适合只加载一张图的Banner
 */
public class BannerImgAdapter implements CBViewHolderCreator {

    @Override
    public Holder createHolder(View itemView) {

        return new Holder<String>(itemView) {
            FrameLayout layout;
            ImageView imageView;

            @Override
            protected void initView(View itemView) {
                layout = itemView.findViewById(R.id.ll_img_parent);
                if (getImageView() != null) {
                    layout.removeAllViews();
                    imageView = getImageView();
                    layout.addView(imageView);
                } else {
                    imageView = itemView.findViewById(R.id.image);
                }
            }

            @Override
            public void updateUI(String data) {
                ImageUtils.loadImage(imageView, data);
            }
        };
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

