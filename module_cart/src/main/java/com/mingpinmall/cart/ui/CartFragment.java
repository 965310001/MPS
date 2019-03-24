package com.mingpinmall.cart.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mingpinmall.cart.R;
import com.mingpinmall.cart.R2;

import butterknife.BindView;
import butterknife.OnClick;
import me.goldze.common.base.mvvm.base.BaseFragment;
import me.goldze.common.utils.ToastUtils;

/**
 * 购物车
 */
public class CartFragment extends BaseFragment {

    @BindView(R2.id.tv_cart)
    TextView tvCart;

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
        tvCart.setText("cart");
    }


    @OnClick(R2.id.tv_cart)
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id==R.id.tv_cart) {
            ToastUtils.showLong("tv_cart");
        }
    }
}
