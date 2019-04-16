package com.mingpinmall.classz.ui.activity.store;

import android.arch.lifecycle.Observer;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.base.BaseListFragment;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
import com.mingpinmall.classz.ui.vm.bean.GoodsListInfo;
import com.mingpinmall.classz.ui.vm.bean.StoreInfo;
import com.mingpinmall.classz.ui.vm.bean.TypeInfo;
import com.socks.library.KLog;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.adapter.ItemData;
import com.trecyclerview.listener.OnItemClickListener;

import java.util.List;

import io.reactivex.annotations.Nullable;

/**
 * 店铺首页
 */
public class StoreHomeFragment extends BaseListFragment<ClassifyViewModel> implements OnItemClickListener {

    public StoreHomeFragment() {
    }

    public static StoreHomeFragment newInstance() {
        StoreHomeFragment fragment = new StoreHomeFragment();
        return fragment;
    }

    @Override
    protected void getRemoteData() {
        super.getRemoteData();

        String storeId = ((StoreActivity) activity).getStoreId();
        mViewModel.getStoreInfo("7", "",
                Constants.STORE_GOODS_RANK_KEY[2]);

//        mViewModel.getStoreGoodsRank("7",
//                "collectdesc", "3",
//                Constants.STORE_GOODS_RANK_KEY[0]);
//
//        mViewModel.getStoreGoodsRank(storeId, "salenumdesc",
//                "3", "");
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
                                ItemData itemData = new ItemData();
                                itemData.add(new TypeInfo("店铺排行榜"));
                                GoodsListInfo goodsListInfo = new GoodsListInfo();
                                goodsListInfo.setCollectdesc_goods_list(data.getData().getCollectdesc_goods_list());
                                goodsListInfo.setSalenumdesc_goods_list(data.getData().getSalenumdesc_goods_list());
                                itemData.add(goodsListInfo);
                                itemData.add(new TypeInfo("店主推荐"));
                                itemData.addAll(data.getData().getRec_goods_list());
                                setData(itemData);
                            } catch (Exception e) {
                                KLog.i(e.toString());
                            }
                        } else {
                            ToastUtils.showLong(response.getMessage());
                        }
                    }
                });

        /*收藏排行*/
//        registerObserver(Constants.STORE_GOODS_RANK_KEY[0], GoodsListInfo.class)
//                .observeForever(new Observer<GoodsListInfo>() {
//                    @Override
//                    public void onChanged(@Nullable GoodsListInfo response) {
//                        KLog.i("TAGS", response.toString());
//                        KLog.i("====");
//
////                        addItems(response.getDatas());
//                    }
//                });
    }


    @Override
    protected Object getStateEventKey() {
        return Constants.STORE_GOODS_RANK_KEY[1];
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                int span = 1;
//                if (adapter.getItems().get(i) instanceof GoodsInfo) {
//                    span = 1;
//                }
                if (adapter.getItems().get(i) instanceof TypeInfo) {
                    span = 2;
                }
                return span;
            }
        });
        return gridLayoutManager;
    }

    @Override
    protected DelegateAdapter createAdapter() {
        return AdapterPool.newInstance().getStoreHomeAdapter(getActivity())
                .setOnItemClickListener(this)
                .build();
    }

    @Override
    public void onItemClick(View view, int i, Object o) {

    }
}