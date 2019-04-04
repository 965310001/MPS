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
     * @date 创建时间： 2019/4/3
     * @author 创建人：小斌
     * @Description 描述：获取首页数据
     * @Version
     **/
    public void getHomeDataList() {
        mRepository.getHomeDataList();
    }
}
