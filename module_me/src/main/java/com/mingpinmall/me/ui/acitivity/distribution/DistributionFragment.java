package com.mingpinmall.me.ui.acitivity.distribution;

import android.os.Bundle;

import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.BaseRecyclerviewBinding;
import com.mingpinmall.me.ui.api.MeViewModel;

/**
 * 功能描述：我的推广 - X级下线
 * 创建人：小斌
 * 创建时间: 2019/3/29
 **/
public class DistributionFragment extends AbsLifecycleFragment<BaseRecyclerviewBinding, MeViewModel> {

    private int pageIndex = 1;

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
        super.initView(state);
    }

    @Override
    protected void lazyLoad() {
        mViewModel.getInviteList("", 1);
    }

    @Override
    protected void dataObserver() {

    }

    @Override
    protected Object getStateEventKey() {
        return "DistributionFragment";
    }
}
