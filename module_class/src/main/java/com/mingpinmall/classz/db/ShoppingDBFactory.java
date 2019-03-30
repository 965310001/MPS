package com.mingpinmall.classz.db;

import com.goldze.common.dmvvm.utils.Utils;

/**
 * @author GuoFeng
 * @date : 2019/1/24 20:36
 * @description: 商城模块DB工厂  管理所有的DB操作类
 */
public class ShoppingDBFactory {

    private static ShoppingDBFactory mInstance = null;
    private GoodsInfoManage goodsInfoManage;

    public static ShoppingDBFactory getInstance() {
        if (mInstance == null) {
            synchronized (ShoppingDBFactory.class) {
                if (mInstance == null) {
                    mInstance = new ShoppingDBFactory();
                }
            }
        }
        return mInstance;
    }

    /**
     * 购物车商品Mange
     *
     * @return GoodsInfoManage
     */
    public GoodsInfoManage getGoodsInfoManage() {
        if (goodsInfoManage == null) {
            goodsInfoManage = new GoodsInfoManage(ShoppingDBManage.getInstance(Utils.getApplication()).getDaoSession()
                    .getGoodsInfoDao());
        }
        return goodsInfoManage;
    }
}
