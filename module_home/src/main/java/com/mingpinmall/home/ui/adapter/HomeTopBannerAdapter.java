package com.mingpinmall.home.ui.adapter;

import com.goldze.common.dmvvm.adapter.BaseBannerAdapter;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.home.R;
import com.mingpinmall.home.databinding.ViewHomeItem3Binding;

/**
 * 功能描述：首页顶部轮播图适配器
 * *@author 小斌
 * @date 2019/5/5
 **/
public class HomeTopBannerAdapter extends BaseBannerAdapter<String, ViewHomeItem3Binding> {

    @Override
    protected int getLayoutId() {
        return R.layout.view_home_item_3;
    }

    @Override
    protected void convert(ViewHomeItem3Binding binding, String item, int position) {
        ImageUtils.loadImage(binding.iv0, item);
    }
}
