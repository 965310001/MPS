package com.mingpinmall.cart.ui.api;


import android.app.Application;
import android.support.annotation.NonNull;

import com.goldze.common.dmvvm.base.mvvm.AbsViewModel;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/24
 **/
public class CartViewModel extends AbsViewModel<CartRepository> {

    public CartViewModel(@NonNull Application application) {
        super(application);
    }

    /*购物车列表*/
    public void getCartList() {
        mRepository.getCartList();
    }

    /*购物车商品数量更改*/
    public void editCartQuantity(int position, String cartId, int quantity) {
        mRepository.editCartQuantity(position, cartId, quantity);
    }

    /*购物车商品移除*/
    public void deleteGoods(int position, String cartId) {
        mRepository.deleteGoods(position, cartId);
    }

}
