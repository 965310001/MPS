package com.mingpinmall.me.ui.acitivity.order.RefundOrder;

import android.os.Bundle;

import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentOrderBinding;

/**
 * 功能描述：退款单列表
 * 创建人：小斌
 * 创建时间: 2019/3/26
 **/
public class RefunMoneyFragment extends BaseFragment<FragmentOrderBinding> {
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
    }
}
