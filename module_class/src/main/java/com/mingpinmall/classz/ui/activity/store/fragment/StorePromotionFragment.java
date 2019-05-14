package com.mingpinmall.classz.ui.activity.store.fragment;

import android.view.View;

import com.goldze.common.dmvvm.base.mvvm.base.BaseListFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.ui.activity.store.StoreActivity;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.StorePromotionInfo;
import com.socks.library.KLog;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.adapter.ItemData;
import com.trecyclerview.listener.OnItemClickListener;

/**
 * 店铺活动
 */
public class StorePromotionFragment extends BaseListFragment<ClassifyViewModel> implements OnItemClickListener {

    public static StorePromotionFragment newInstance() {
        return new StorePromotionFragment();
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
                .observe(this, response -> {
                    try {
                        ItemData itemData = new ItemData();
                        itemData.add(response.getDatas().getPromotion().getMansong());
                        itemData.add(response.getDatas().getPromotion().getXianshi());
                        setData(itemData);
                    } catch (Exception e) {
                        KLog.i(e.toString());
                    }
                });
    }

    @Override
    protected boolean isItemDecoration() {
        return false;
    }

    @Override
    protected DelegateAdapter createAdapter() {
        return AdapterPool.newInstance()
                .getStorePromotionAdapter(getActivity())
                .setOnItemClickListener(this)
                .build();
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.STORE_GOODS_RANK_KEY[1];
    }

    @Override
    public void onItemClick(View view, int i, Object object) {
        // TODO: 2019/4/18 待修改
        ActivityToActivity.toActivity(ARouterConfig.classify.PRODUCTSACTIVITY2, "id", ((StoreActivity) activity).getStoreId());
    }
}
