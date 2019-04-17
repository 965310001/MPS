package com.mingpinmall.classz.ui.activity.store;

import android.arch.lifecycle.Observer;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
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
import com.mingpinmall.classz.ui.activity.classiflist.ProductsFragment;
import com.mingpinmall.classz.ui.activity.store.fragment.StoreNewGoodsFragment;
import com.mingpinmall.classz.ui.activity.store.fragment.StorePromotionFragment;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.StoreInfo;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 商家店铺
 */
@Route(path = ARouterConfig.classify.STOREACTIVITY)
public class StoreActivity extends AbsLifecycleActivity<ActivityStoreBinding, ClassifyViewModel> {

    @Autowired
    String storeId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_store;
    }

    @Override
    protected boolean isActionBar() {
        return false;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.initViews(savedInstanceState);
        setTitlePadding(binding.clTitleBar);

//        getSupportFragmentManager()
//                .beginTransaction().add(R.id.rl_content,
//                StoreHomeFragment.newInstance()).commit();

        List<TabViewChild> tabViewChildList = new ArrayList<>();
        BaseFragment[] fragmentList = {StoreHomeFragment.newInstance(),
                ProductsFragment.newInstance(storeId),
                StoreNewGoodsFragment.newInstance(),
                StorePromotionFragment.newInstance()
        };

        TypedArray tabIcon = ResourcesUtils.getInstance().obtainTypedArray(R.array.tab_icon);
        TypedArray tabIconDef = ResourcesUtils.getInstance().obtainTypedArray(R.array.tab_icon_def);
        String[] tabName = ResourcesUtils.getInstance().getStringArray(R.array.tab_name);

        TabViewChild tabViewChild;
        for (int i = 0; i < tabIcon.length(); i++) {
            tabViewChild = new TabViewChild(tabIcon.getResourceId(i, 0),
                    tabIconDef.getResourceId(i, 0),
                    tabName[i],
                    fragmentList[i]);
            tabViewChildList.add(tabViewChild);
        }

        binding.tabView.setTabViewChild(tabViewChildList, getSupportFragmentManager());
    }


    @Override
    protected Object getStateEventKey() {
        return Constants.STORE_GOODS_RANK_KEY[1];
    }

    @Override
    protected void initData() {
        super.initData();
        /*店铺信息*/
//        mViewModel.getStoreInfo("7", "",
//                Constants.STORE_GOODS_RANK_KEY[2]);
//
//        /*全部商品*/
//        mViewModel.getStoreGoods("7", 1,
//                Constants.STORE_GOODS_RANK_KEY[5]);
//
//        /*商品上新*/
//        mViewModel.getStoreNewGoods("7", 1,
//                Constants.STORE_GOODS_RANK_KEY[3]);
//
////        /*收藏排行*/
////        mViewModel.getStoreGoodsRank("7",
////                "collectdesc", "3",
////                Constants.STORE_GOODS_RANK_KEY[0]);
//
//        /*活动店铺*/
//        mViewModel.getStorePromotion("7", 1,
//                Constants.STORE_GOODS_RANK_KEY[4]);
    }

    StoreInfo.StoreInfoBean storeInfo;

    public void setStoreInfo(StoreInfo.StoreInfoBean storeInfo) {
        this.storeInfo = storeInfo;
        binding.setData(storeInfo);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();

//        registerObserver(Constants.STORE_GOODS_RANK_KEY[2], BaseResponse.class)
//                .observeForever(new Observer<BaseResponse>() {
//                    @Override
//                    public void onChanged(@Nullable BaseResponse response) {
//                        if (response.isSuccess()) {
//                            BaseResponse<StoreInfo> data = response;
//                            try {
//                                storeInfo = data.getData().getStore_info();
//                                binding.setData(storeInfo);
//                            } catch (Exception e) {
//                                KLog.i(e.toString());
//                            }
//                        } else {
//                            ToastUtils.showLong(response.getMessage());
//                        }
//                    }
//                });

        /*收藏*/
        registerObserver(Constants.FAVORITES, ResultBean.class)
                .observeForever(new Observer<ResultBean>() {
                    @Override
                    public void onChanged(@android.support.annotation.Nullable ResultBean response) {
                        KLog.i(response.isSuccess() + " " + response.getError());
                        if (response.isSuccess()) {
                            ToastUtils.showLong(storeInfo.isIs_favorate() ? "取消收藏成功" : "添加收藏成功");
                            storeInfo.setIs_favorate(!storeInfo.isIs_favorate());
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
//            mViewModel.favorites(id, is_favorate, Constants.FAVORITES);
        }
    }

    public void finish(View view) {
        finish();
    }

    public String getStoreId() {
        storeId = "10";
        return storeId;
    }
}