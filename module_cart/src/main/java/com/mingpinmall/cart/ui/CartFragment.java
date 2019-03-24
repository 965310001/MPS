package com.mingpinmall.cart.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.cart.R;
import com.mingpinmall.cart.databinding.FragmentCartBinding;


/**
 * 购物车
 */
public class CartFragment extends BaseFragment<FragmentCartBinding> {

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
        tvCart = binding.tvCart;
        tvCart.setText("cart");
    }


    //    @OnClick(R2.id.tv_cart)
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_cart) {
            ToastUtils.showLong("tv_cart");
        }
    }
}
