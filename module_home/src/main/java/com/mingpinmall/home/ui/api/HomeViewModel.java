package com.mingpinmall.home.ui.api;

import android.app.Application;
import android.support.annotation.NonNull;

import com.goldze.common.dmvvm.base.mvvm.AbsViewModel;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/3
 **/
public class HomeViewModel extends AbsViewModel<HomeRepository> {

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 获取店铺列表
     * @param curPage
     */
    public void getStoreStreet(String keyword, String area_info, String sc_id, int curPage) {
        mRepository.getStoreStreet(keyword, area_info, sc_id, curPage);
    }

    /**
     * @date 创建时间： 2019/4/3
     * @author 创建人：小斌
     * @Description 描述：获取店铺分类
     * @Version
     **/
    public void getStoreClass() {
        mRepository.getStoreClass();
    }

    /**
     * @date 创建时间： 2019/4/3
     * @author 创建人：小斌
     * @Description 描述：获取首页数据
     * @Version
     **/
    public void getHomeDataList() {
        mRepository.getHomeDataList();
    }
}
