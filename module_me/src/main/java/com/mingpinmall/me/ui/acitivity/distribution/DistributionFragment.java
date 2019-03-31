package com.mingpinmall.me.ui.acitivity.distribution;

import android.os.Bundle;

import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.BaseRecyclerviewBinding;

/**
 * 功能描述：我的推广 - X级下线
 * 创建人：小斌
 * 创建时间: 2019/3/29
 **/
public class DistributionFragment extends BaseFragment<BaseRecyclerviewBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.base_recyclerview;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {
        binding.tvLabel.setText("我的推广 - X级下线");
    }
}
