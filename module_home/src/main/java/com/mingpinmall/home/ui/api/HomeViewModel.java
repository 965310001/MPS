package com.mingpinmall.home.ui.api;

import android.app.Application;
import android.support.annotation.NonNull;

import com.goldze.common.dmvvm.base.mvvm.AbsViewModel;

/**
 * 功能描述：
 * @author 小斌
 * @date 2019/4/3
 **/
public class HomeViewModel extends AbsViewModel<HomeRepository> {

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    /*获取店铺列表*/
    public void getStoreStreet(String keyword, String area_info, String sc_id, int curPage) {
        mRepository.getStoreStreet(keyword, area_info, sc_id, curPage);
    }

    /*获取店铺分类*/
    public void getStoreClass() {
        mRepository.getStoreClass();
    }

    /*获取首页数据*/
    public void getHomeDataList() {
        mRepository.getHomeDataList();
    }

    /*获取专题页面*/
    public void getSpecialList(String specialId) {
        mRepository.getSpecialList(specialId);
    }
}
