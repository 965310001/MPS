package com.mingpinmall.classz.ui.activity.store;

import android.arch.lifecycle.Observer;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
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
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.databinding.ActivityStoreBinding;
import com.mingpinmall.classz.ui.activity.store.fragment.ProductsFragment;
import com.mingpinmall.classz.ui.activity.store.fragment.StoreNewGoodsFragment;
import com.mingpinmall.classz.ui.activity.store.fragment.StorePromotionFragment;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.StoreInfo;
import com.mingpinmall.classz.ui.vm.bean.VoucherInfo;
import com.mingpinmall.classz.widget.XBottomSheet;
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

    XBottomSheet xBottomSheet;

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

        List<TabViewChild> tabViewChildList = new ArrayList<>();
        BaseFragment[] fragmentList = {StoreHomeFragment.newInstance(),
                ProductsFragment.newInstance(storeId),
                StoreNewGoodsFragment.newInstance(),
                StorePromotionFragment.newInstance()
        };

        TypedArray tabIcon = ResourcesUtils.getInstance().obtainTypedArray(R.array.store_tab_icon);
        TypedArray tabIconDef = ResourcesUtils.getInstance().obtainTypedArray(R.array.store_tab_icon_def);
        String[] tabName = ResourcesUtils.getInstance().getStringArray(R.array.store_tab_name);

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

    StoreInfo.StoreInfoBean storeInfo;

    public void setStoreInfo(StoreInfo.StoreInfoBean storeInfo) {
        this.storeInfo = storeInfo;
        binding.setData(storeInfo);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        /*收藏*/
        registerObserver(Constants.STORE_FAVORITES, ResultBean.class)
                .observeForever(new Observer<ResultBean>() {
                    @Override
                    public void onChanged(@android.support.annotation.Nullable ResultBean response) {
                        KLog.i(response.isSuccess() + " " + response.getError());
                        if (response.isSuccess()) {
                            int store_collect = storeInfo.getStore_collect();
                            storeInfo.setStore_collect(storeInfo.isIs_favorate() ? store_collect - 1 : store_collect + 1);
                            storeInfo.setIs_favorate(!storeInfo.isIs_favorate());
                        } else {
                            ToastUtils.showLong(response.getError());
                        }
                    }
                });
        /*代金券*/
        registerObserver(Constants.VOUCHER[0], BaseResponse.class)
                .observeForever(new Observer<BaseResponse>() {
                    @Override
                    public void onChanged(@Nullable BaseResponse response) {
                        KLog.i("======接收数据");
                        BaseResponse<VoucherInfo> data = response;
                        if (data.isSuccess()) {
                            List<VoucherInfo.VoucherListBean> voucher_list = data.getData().getVoucher_list();
//                            KLog.i(voucher_list);
                            if (null == xBottomSheet) {
                                xBottomSheet = new XBottomSheet.BottomListSheetBuilder(activity)
                                        .setItemData(voucher_list)
//                                        .setItemData(voucher_list)
//                                        .setItemData(voucher_list)
//                                        .addItem("1")
                                        .setAdapter(AdapterPool.newInstance()
                                                .getVoucherInfoAdapter(activity)
                                                .build())
                                        .setLayoutManager(new LinearLayoutManager(activity))
                                        .setOnSheetItemClickListener(new XBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                                            @Override
                                            public void onClick(XBottomSheet dialog, View itemView, int position, String tag) {
                                                dialog.dismiss();
                                                ToastUtils.showLong("Item " + (position + 1));
                                            }
                                        })
                                        .build();
                            }
                            xBottomSheet.show();
                        } else {
                            ToastUtils.showLong(data.getMessage());
                        }
                    }
                });

        /*领取代金券*/
        registerObserver(Constants.VOUCHER[2], ResultBean.class)
                .observe(this, new Observer<ResultBean>() {
                    @Override
                    public void onChanged(@Nullable ResultBean response) {
                        xBottomSheet.dismiss();
                        if (response.isSuccess()) {
                            ToastUtils.showLong("领取成功");
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
            mViewModel.getStoreFavorites(getStoreId(), storeInfo.isIs_favorate(),
                    Constants.STORE_FAVORITES);
        }
    }

    /*店铺介绍*/
    public void getStoreIntro(View view) {
        ActivityToActivity.toActivity(ARouterConfig.classify.STOREINTROACTIVITY, "storeId", getStoreId());
    }

    /*代金券*/
    public void getVoucherTplList(View view) {
        mViewModel.getVoucherTplList(getStoreId(), Constants.VOUCHER[0]);
    }

    /*领取代金券*/
    public void getReceive(View view) {
        String tId = (String) view.getTag();
        mViewModel.getVoucherFreeex(tId, Constants.VOUCHER[2]);
    }

    /*联系客服*/
    public void getContactService(View view) {
    }

    /*商品分类*/
    public void getGoodsClassif(View view) {
    }

    public void finish(View view) {
        finish();
    }

    public String getStoreId() {
//        storeId = "10";
        return storeId;
    }
}