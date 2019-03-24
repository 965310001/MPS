package com.mingpinmall.cart;

import android.os.Bundle;

import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.mingpinmall.cart.ui.CartFragment;


public class CartActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cart;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("购物车");
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, CartFragment.newInstance()).commit();
    }

    @Override
    protected boolean isBack() {
        return false;
    }
}
