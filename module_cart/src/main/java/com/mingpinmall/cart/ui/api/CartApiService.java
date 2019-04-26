package com.mingpinmall.cart.ui.api;

import com.goldze.common.dmvvm.base.bean.BaseNothingBean;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.mingpinmall.cart.ui.bean.ShopCartBean;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/24
 **/
public interface CartApiService {

    /**
     * 购物车
     * https://www.mingpinmall.cn/mo_bile/index.php?app=member_cart&wwi=cart_list
     * POST
     * 参数
     */

    String SHOP_CART_LIST = "/mo_bile/index.php?app=member_cart&wwi=cart_list";

    @FormUrlEncoded
    @POST(SHOP_CART_LIST)
    Flowable<BaseResponse<ShopCartBean>> getCartList(
            @Field("key") String key
    );

    /**
     * 购物车 增加或者减少商品
     * http://192.168.0.44/mo_bile/index.php?app=member_cart&wwi=cart_edit_quantity
     * POST
     * 参数
     * cart_id
     * quantity  1 减少  2 增加
     */

    String EDIT_CART_QUANTITY = "/mo_bile/index.php?app=member_cart&wwi=cart_edit_quantity";

    @FormUrlEncoded
    @POST(EDIT_CART_QUANTITY)
    Flowable<BaseNothingBean> editCartQuantity(
            @Field("key") String key,
            @Field("cart_id") String cart_id,
            @Field("quantity") int quantity
    );

    /**
     * 购物车 移除商品
     * http://192.168.0.44/mo_bile/index.php?app=member_cart&wwi=cart_del
     * POST
     * 参数
     * cart_id
     * quantity  1 减少  2 增加
     */

    String DELETE_GOODS = "/mo_bile/index.php?app=member_cart&wwi=cart_del";

    @FormUrlEncoded
    @POST(DELETE_GOODS)
    Flowable<BaseNothingBean> deleteGoods(
            @Field("key") String key,
            @Field("cart_id") String cart_id
    );
}
