package com.mingpinmall.cart.ui.bean;

/**
 * 功能描述：购物车商品数量更改
 * *@author 小斌
 * @date 2019/4/25
 **/
public class CartQuantityState {

    boolean isSuccess;
    String msg;
    int position;
    int quantity;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? "" : msg;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
