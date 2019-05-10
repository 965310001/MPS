package com.mingpinmall.me.ui.acitivity.distribution.reducecash;

import android.os.Bundle;

import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentReducecashAction1Binding;
import com.mingpinmall.me.ui.api.MeViewModel;

/**
 * 功能描述：提现申请
 * *@author 小斌
 * @date 2019/4/29
 **/
public class ActionOneFragment extends AbsLifecycleFragment<FragmentReducecashAction1Binding, MeViewModel> {

    public static ActionOneFragment getInstance() {
        return new ActionOneFragment();
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
        return "ActionOneFragment";
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_reducecash_action1;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }
}
