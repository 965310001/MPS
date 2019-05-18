package com.mingpinmall.home.ui.adapter;

import com.goldze.common.dmvvm.adapter.BaseBannerAdapter;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.home.R;
import com.mingpinmall.home.databinding.ViewHomeItemTopbannerBinding;

/**
 * 功能描述：首页顶部轮播图适配器
 * *@author 小斌
 * @date 2019/5/5
 **/
public class HomeTopBannerAdapter extends BaseBannerAdapter<String, ViewHomeItemTopbannerBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.view_home_item_topbanner;
    }

    @Override
    protected void convert(ViewHomeItemTopbannerBinding binding, String item, int position) {
        ImageUtils.loadImage(binding.iv0, item, 640, 300);
    }
}
