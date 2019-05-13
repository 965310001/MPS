package com.mingpinmall.cart.ui.api;

import com.goldze.common.dmvvm.base.bean.BaseNothingBean;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.base.BaseRepository;
import com.goldze.common.dmvvm.http.RetrofitClient;
import com.goldze.common.dmvvm.http.rx.RxSchedulers;
import com.goldze.common.dmvvm.http.rx.RxSubscriber;
import com.mingpinmall.cart.ui.bean.CartQuantityState;
import com.mingpinmall.cart.ui.bean.ShopCartBean;
import com.mingpinmall.cart.ui.constants.Constants;

/**
 * 功能描述：
 * *@author 小斌
 * @date 2019/4/24
 **/
public class CartRepository extends BaseRepository {
    private CartApiService apiService = RetrofitClient.getInstance().create(CartApiService.class);

    /*购物车列表*/
    protected void getCartList(final String event_key) {
        addDisposable(apiService.getCartList(getUserKey())
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponse<ShopCartBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<ShopCartBean> result) {
                        if (result.isSuccess()) {
                            sendData(event_key + Constants.SHOP_CART_LIST, result.getData());
                        } else {
                            sendData(event_key + Constants.SHOP_CART_LIST, result.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        sendData(event_key + Constants.SHOP_CART_LIST, msg == null ? "获取失败" : msg);
                    }
                })
        );
    }

    /*购物车商品数量更改*/
    protected void editCartQuantity(int position, String cartId, int quantity) {
        final CartQuantityState data = new CartQuantityState();
        data.setPosition(position);
        data.setQuantity(quantity);
        addDisposable(apiService.editCartQuantity(getUserKey(), cartId, quantity)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {
                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            data.setMsg("成功");
                            data.setSuccess(true);
                            sendData(Constants.CART_QUANTITY, data);
                        } else {
                            data.setMsg(result.getMessage());
                            data.setSuccess(false);
                            sendData(Constants.CART_QUANTITY, data);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        data.setMsg(msg == null ? "获取失败" : msg);
                        data.setSuccess(false);
                        sendData(Constants.CART_QUANTITY, data);
                    }
                })
        );
    }

    /*购物车商品移除*/
    protected void deleteGoods(int position, String cartId) {
        final CartQuantityState data = new CartQuantityState();
        data.setPosition(position);
        addDisposable(apiService.deleteGoods(getUserKey(), cartId)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseNothingBean>() {
                    @Override
                    public void onSuccess(BaseNothingBean result) {
                        if (result.isSuccess()) {
                            data.setMsg("成功");
                            data.setSuccess(true);
                            sendData(Constants.CART_DELETE, data);
                        } else {
                            data.setMsg(result.getMessage());
                            data.setSuccess(false);
                            sendData(Constants.CART_DELETE, data);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        data.setMsg(msg == null ? "获取失败" : msg);
                        data.setSuccess(false);
                        sendData(Constants.CART_DELETE, data);
                    }
                })
        );
    }

}
