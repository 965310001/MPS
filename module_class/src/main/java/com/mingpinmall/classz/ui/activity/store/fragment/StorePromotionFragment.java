package com.mingpinmall.classz.ui.activity.store.fragment;

import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.goldze.common.dmvvm.base.mvvm.base.BaseListFragment;
import com.goldze.common.dmvvm.utils.DisplayUtil;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.databinding.ItemTabsegmentBinding;
import com.mingpinmall.classz.ui.activity.store.StoreActivity;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.GoodsListInfo;
import com.mingpinmall.classz.ui.vm.bean.ScreenInfo;
import com.mingpinmall.classz.ui.vm.bean.StorePromotionInfo;
import com.mingpinmall.classz.widget.CustomPopWindow;
import com.mingpinmall.classz.widget.FilterTab;
import com.mingpinmall.classz.widget.ScreeningPopWindow;
import com.socks.library.KLog;
import com.trecyclerview.adapter.DelegateAdapter;

import java.util.Arrays;

/**
 * 店铺活动
 */
public class StorePromotionFragment extends BaseListFragment<ClassifyViewModel> {

    public static StorePromotionFragment newInstance() {
        StorePromotionFragment fragment = new StorePromotionFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    /*获取更多数据*/
    @Override
    protected void getRemoteData() {
        super.getRemoteData();

        mViewModel.getStorePromotion(((StoreActivity) activity).getStoreId(), 1,
                Constants.STORE_GOODS_RANK_KEY[4]);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();

        registerObserver(Constants.STORE_GOODS_RANK_KEY[4], StorePromotionInfo.class)
                .observe(this, new Observer<StorePromotionInfo>() {
                    @Override
                    public void onChanged(@Nullable StorePromotionInfo response) {
//                        setData(response.getDatas().);
                        // TODO: 2019/4/17 设置数据 
                    }
                });
    }

    @Override
    protected DelegateAdapter createAdapter() {
        return AdapterPool.newInstance()
                .getStorePromotionAdapter(getActivity())
                .build();
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.STORE_GOODS_RANK_KEY[1];
    }
}
