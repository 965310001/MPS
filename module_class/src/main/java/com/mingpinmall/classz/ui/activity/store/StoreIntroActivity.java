package com.mingpinmall.classz.ui.activity.store;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.utils.log.QLog;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.ResultBean;
import com.mingpinmall.classz.databinding.ActivityStoreIntroBinding;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.StoreInfo;


/**
 * 店铺介绍
 */
@Route(path = ARouterConfig.classify.STOREINTROACTIVITY)
public class StoreIntroActivity extends AbsLifecycleActivity<ActivityStoreIntroBinding, ClassifyViewModel> {

    @Autowired
    String storeId;

    private StoreInfo.StoreInfoBean storeInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_store_intro;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);
        setTitlePadding(baseBinding.rlTitleContent);

        setTitle("店铺介绍");
    }

    @Override
    protected void initData() {
        super.initData();
        showLoading();
        mViewModel.getStoreIntro(storeId, Constants.STOREINTRO[0]);
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.STOREINTRO[1];
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.STOREINTRO[0], BaseResponse.class)
                .observeForever(response -> {
                    showSuccess();
                    if (response.isSuccess()) {
                        BaseResponse<StoreInfo> data = response;
                        try {
                            storeInfo = data.getData().getStore_info();
                            binding.setData(storeInfo);
                        } catch (Exception e) {
                            QLog.i(e.toString());
                        }
                    } else {
                        ToastUtils.showLong(response.getMessage());
                    }
                });

        /*收藏*/
        registerObserver("Constants.STORE_FAVORITES", ResultBean.class)
                .observeForever(response -> {
                    if (response.isSuccess()) {
                        int store_collect = storeInfo.getStore_collect();
                        storeInfo.setStore_collect(storeInfo.isIs_favorate() ? store_collect - 1 : store_collect + 1);
                        storeInfo.setIs_favorate(!storeInfo.isIs_favorate());
                    } else {
                        ToastUtils.showLong(response.getError());
                    }
                });
    }

    public void favorites(View view) {
        /*QLog.i("店铺收藏");*/
        if (!SharePreferenceUtil.isLogin()) {
            ActivityToActivity.toActivity(ARouterConfig.LOGINACTIVITY);
        } else {
            mViewModel.getStoreFavorites(storeId, storeInfo.isIs_favorate(),
                    "Constants.STORE_FAVORITES");
        }
    }
}