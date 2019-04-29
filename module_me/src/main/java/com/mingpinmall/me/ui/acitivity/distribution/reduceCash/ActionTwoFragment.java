package com.mingpinmall.me.ui.acitivity.distribution.reduceCash;

import android.os.Bundle;

import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentReducecashAction2Binding;
import com.mingpinmall.me.ui.api.MeViewModel;

/**
 * 功能描述：提现申请
 * 创建人：小斌
 * 创建时间: 2019/4/29
 **/
public class ActionTwoFragment extends AbsLifecycleFragment<FragmentReducecashAction2Binding, MeViewModel> {

    public static ActionTwoFragment getInstance() {
        return new ActionTwoFragment();
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
    }

    @Override
    protected Object getStateEventKey() {
        return "ActionTwoFragment";
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_reducecash_action2;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }
}
