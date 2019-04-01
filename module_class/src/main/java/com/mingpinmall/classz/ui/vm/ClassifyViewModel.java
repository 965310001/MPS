package com.mingpinmall.classz.ui.vm;

import android.app.Application;
import android.support.annotation.NonNull;

import com.goldze.common.dmvvm.base.mvvm.AbsViewModel;

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


}
