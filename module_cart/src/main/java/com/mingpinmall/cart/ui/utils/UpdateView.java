package com.mingpinmall.cart.ui.utils;

/**
 * Created by Administrator on 2016/10/28 0028.
 */

public interface UpdateView {

    void checkChange(boolean isCheck, int position);
    void update(int position, boolean isAdd, double price);
}
