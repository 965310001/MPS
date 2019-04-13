package com.mingpinmall.me.ui.acitivity.property.cardSurplusFragment;

import android.os.Bundle;

import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;

/**
 * 功能描述：充值卡余额
 * 创建人：小斌
 * 创建时间: 2019/4/13
 **/
public class CardSurplusLogFragment extends AbsLifecycleFragment {

    @Override
    public void initView(Bundle state) {
        super.initView(state);

    }

    @Override
    protected void dataObserver() {

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected Object getStateEventKey() {
        return "CardSurplusLogFragment";
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected int getContentResId() {
        return 0;
    }
}
