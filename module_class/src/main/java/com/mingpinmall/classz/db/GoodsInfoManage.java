package com.mingpinmall.classz.db;

import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;

import org.greenrobot.greendao.AbstractDao;

/**
 * @author GuoFeng
 * @date : 2019/1/24 20:38
 * @description: 购物车商品DB操作类
 */
public class GoodsInfoManage extends BaseDBManager<GoodsInfo, String> {

    public GoodsInfoManage(AbstractDao dao) {
        super(dao);
    }
}
