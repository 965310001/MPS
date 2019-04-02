package com.mingpinmall.classz.ui.vm;

import android.app.Application;
import android.support.annotation.NonNull;

import com.goldze.common.dmvvm.base.mvvm.AbsViewModel;
import com.mingpinmall.classz.ui.vm.api.ClassifyService;

import java.util.HashMap;
import java.util.Map;

public class ClassifyViewModel extends AbsViewModel<ClassifyRepository> {

    public ClassifyViewModel(@NonNull Application application) {
        super(application);
    }

    /*商品分类*/
    public void getLeft() {
        mRepository.getLeft();
    }

    public void getRight(String gcId) {
        mRepository.getRight(gcId);
    }

    public void getRightByBrand() {
        mRepository.getRightByBrand();
    }

    /*商品列表*/
    public void getShappingList(String bId, String curpage, String keyword, String typeId) {
        mRepository.getShappingList(bId, curpage, keyword, typeId);
    }

    /*商品详情*/
    public void getGoodsDetail(String goodsId) {
        mRepository.getGoodsDetail(goodsId);
    }

    /**
     * 添加到购物车
     *
     * @param goodsId  商品ID
     * @param num      数量
     * @param eventKey 有数据的Key
     */
    public void addCart(String goodsId, long num, Object eventKey) {
        Map<String, Object> map = new HashMap<>();
        map.put("Goods_ID", goodsId);
        map.put("Quantity", num);
        mRepository.execute(ClassifyService.ADD_CART_APP, ClassifyService.ADD_CART_WWI, eventKey, map);
    }
}
