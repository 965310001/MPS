package com.mingpinmall.cart.ui.api;


import android.app.Application;
import android.support.annotation.NonNull;

import com.goldze.common.dmvvm.base.mvvm.AbsViewModel;

/**
 * 功能描述：
 * *@author 小斌
 * @date 2019/4/24
 **/
public class CartViewModel extends AbsViewModel<CartRepository> {

    public CartViewModel(@NonNull Application application) {
        super(application);
    }

    /*领取代金券*/
    public void getVoucherFreeex(String tid, Object eventKey) {
        mRepository.getVoucherFreeex(tid, eventKey);
    }

    /*店铺代金券*/
    public void getVoucherTplList(String storeId, Object eventKey) {
        mRepository.getVoucherTplList(storeId, "free", eventKey);
    }

    /*全部店铺代金券*/
    public void getAllVoucherList(int pageIndex, Object eventKey) {
        mRepository.getAllVoucherList(pageIndex, eventKey);
    }

    /*购物车列表*/
    public void getCartList(String event_key) {
        mRepository.getCartList(event_key);
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
