package com.mingpinmall.me.ui.acitivity.collection;

import android.os.Bundle;

import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentOrderBinding;

/**
 * 功能描述：商品收藏页面
 * 创建人：小斌
 * 创建时间: 2019/3/26
 **/
public class ProductCollectionFragment extends BaseFragment<FragmentOrderBinding> {
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_order;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {
        binding.tvLabel.setText("这是商品收藏页面");
    }
}
