package com.mingpinmall.classz.ui.activity.store;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.databinding.ActivityStoreBinding;
import com.mingpinmall.classz.ui.activity.classify.ClassifyFragment;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.GoodsListInfo;
import com.mingpinmall.classz.ui.vm.bean.StoreInfo;
import com.mingpinmall.classz.ui.vm.bean.TypeInfo;
import com.socks.library.KLog;
import com.trecyclerview.adapter.ItemData;

import io.reactivex.annotations.Nullable;

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

        getSupportFragmentManager()
                .beginTransaction().add(R.id.rl_content,
                StoreHomeFragment.newInstance()).commit();
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

        /*全部商品*/
        mViewModel.getStoreGoods("7", 1,
                Constants.STORE_GOODS_RANK_KEY[5]);

        /*商品上新*/
        mViewModel.getStoreNewGoods("7", 1,
                Constants.STORE_GOODS_RANK_KEY[3]);

//        /*收藏排行*/
//        mViewModel.getStoreGoodsRank("7",
//                "collectdesc", "3",
//                Constants.STORE_GOODS_RANK_KEY[0]);

        /*活动店铺*/
        mViewModel.getStorePromotion("7", 1,
                Constants.STORE_GOODS_RANK_KEY[4]);

    }

    @Override
    protected void dataObserver() {
        super.dataObserver();

        registerObserver(Constants.STORE_GOODS_RANK_KEY[2], BaseResponse.class)
                .observeForever(new Observer<BaseResponse>() {
                    @Override
                    public void onChanged(@Nullable BaseResponse response) {
                        if (response.isSuccess()) {
                            BaseResponse<StoreInfo> data = response;
                            try {
                                binding.setData(data.getData().getStore_info());
                            } catch (Exception e) {
                                KLog.i(e.toString());
                            }
                        } else {
                            ToastUtils.showLong(response.getMessage());
                        }
                    }
                });


        /*店铺信息*/
//        registerObserver(Constants.INVOICECONTENT_KEY[2], BaseResponse.class)
//                .observeForever(new Observer<BaseResponse>() {
//                    @Override
//                    public void onChanged(@Nullable BaseResponse response) {
//                        if (response.isSuccess()) {
//                            BaseResponse<StoreInfo> data = response;
//                            KLog.i("TAGS", data.getData());
//                        } else {
//                            ToastUtils.showLong(response.getMessage());
//                        }
//                        KLog.i("====");
//                    }
//                });
        /*收藏排行*/
//        registerObserver(Constants.STORE_GOODS_RANK_KEY[0], GoodsListInfo.class)
//                .observeForever(new Observer<GoodsListInfo>() {
//                    @Override
//                    public void onChanged(@Nullable GoodsListInfo response) {
//                        KLog.i("TAGS", response.toString());
//                        KLog.i("====");
//                    }
//                });
    }

    public void finish(View view) {
        finish();
    }

    public String getStoreId() {
        return storeId;
    }
}