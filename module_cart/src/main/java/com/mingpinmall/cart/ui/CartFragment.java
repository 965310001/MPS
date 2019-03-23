package com.mingpinmall.cart.ui;


import android.os.Bundle;

import com.mingpinmall.cart.R;

import me.goldze.common.base.mvvm.base.BaseFragment;

/**
 * 购物车
 */
public class CartFragment extends BaseFragment {

    public CartFragment() {
    }

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_cart;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {
        showSuccess();
    }

}
