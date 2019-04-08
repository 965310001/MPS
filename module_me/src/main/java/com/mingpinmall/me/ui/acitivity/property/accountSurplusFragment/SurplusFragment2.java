package com.mingpinmall.me.ui.acitivity.property.accountSurplusFragment;

import android.os.Bundle;

import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentDefaultRecyclerviewBinding;

/**
 * 功能描述：充值明细
 * 创建人：小斌
 * 创建时间: 2019/3/28
 **/
public class SurplusFragment2 extends BaseFragment<FragmentDefaultRecyclerviewBinding> {
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_default_recyclerview;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {
    }
}
