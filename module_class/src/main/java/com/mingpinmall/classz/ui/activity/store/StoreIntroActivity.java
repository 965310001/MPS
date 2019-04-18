package com.mingpinmall.classz.ui.activity.store;

import android.arch.lifecycle.Observer;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ResourcesUtils;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.heima.tabview.library.TabViewChild;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.ResultBean;
import com.mingpinmall.classz.databinding.ActivityStoreBinding;
import com.mingpinmall.classz.databinding.ActivityStoreIntroBinding;
import com.mingpinmall.classz.ui.activity.classiflist.ProductsFragment;
import com.mingpinmall.classz.ui.activity.store.fragment.StoreNewGoodsFragment;
import com.mingpinmall.classz.ui.activity.store.fragment.StorePromotionFragment;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.GoodsListInfo;
import com.mingpinmall.classz.ui.vm.bean.StoreInfo;
import com.mingpinmall.classz.ui.vm.bean.TypeInfo;
import com.socks.library.KLog;
import com.trecyclerview.adapter.ItemData;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺介绍
 */
@Route(path = ARouterConfig.classify.STOREINTROACTIVITY)
public class StoreIntroActivity extends AbsLifecycleActivity<ActivityStoreIntroBinding, ClassifyViewModel> {

    @Autowired
    String storeId;

    StoreInfo.StoreInfoBean storeInfo;

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
                .observeForever(new Observer<BaseResponse>() {
                    @Override
                    public void onChanged(@io.reactivex.annotations.Nullable BaseResponse response) {
                        if (response.isSuccess()) {
                            BaseResponse<StoreInfo> data = response;
                            try {
                                storeInfo = data.getData().getStore_info();
                                binding.setData(storeInfo);
                            } catch (Exception e) {
                                KLog.i(e.toString());
                            }
                        } else {
                            ToastUtils.showLong(response.getMessage());
                        }
                    }
                });

        /*收藏*/
        registerObserver("Constants.STORE_FAVORITES", ResultBean.class)
                .observeForever(new Observer<ResultBean>() {
                    @Override
                    public void onChanged(@android.support.annotation.Nullable ResultBean response) {
                        KLog.i(response.isSuccess() + " " + response.getError());
                        if (response.isSuccess()) {
                            int store_collect = storeInfo.getStore_collect();
                            storeInfo.setStore_collect(storeInfo.isIs_favorate() ? store_collect - 1 : store_collect + 1);
                            storeInfo.setIs_favorate(!storeInfo.isIs_favorate());

                            // TODO: 2019/4/18 这里还需要修改
                        } else {
                            ToastUtils.showLong(response.getError());
                        }
                    }
                });
    }

    // TODO: 2019/4/16 店铺收藏代码添加
    public void favorites(View view) {
        KLog.i("店铺收藏");
        if (!SharePreferenceUtil.isLogin()) {
            ActivityToActivity.toActivity(ARouterConfig.LOGINACTIVITY);
        } else {
            mViewModel.getStoreFavorites(storeId, storeInfo.isIs_favorate(),
                    "Constants.STORE_FAVORITES");
        }
    }
}