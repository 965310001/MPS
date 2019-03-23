package com.mingpinmall.cart;

import android.os.Bundle;

import me.goldze.common.base.mvvm.base.test.BaseActivity;

public class CartActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cart;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("购物车");
    }

    @Override
    protected boolean isBack() {
        return false;
    }
}
