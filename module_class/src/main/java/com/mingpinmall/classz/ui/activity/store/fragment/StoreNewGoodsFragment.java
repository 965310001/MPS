package com.mingpinmall.classz.ui.activity.store.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.goldze.common.dmvvm.base.mvvm.base.BaseListFragment;
import com.goldze.common.dmvvm.base.mvvm.stateview.EmptyState;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.ui.activity.store.StoreActivity;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
import com.mingpinmall.classz.ui.vm.bean.GoodsListInfo;

import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.adapter.ItemData;

/**
 * 商品上新
 */
public class StoreNewGoodsFragment extends BaseListFragment<ClassifyViewModel> {

    public static StoreNewGoodsFragment newInstance() {
        return new StoreNewGoodsFragment();
    }

    /*获取更多数据*/
    @Override
    protected void getRemoteData() {
        super.getRemoteData();

        mViewModel.getStoreNewGoods(((StoreActivity) activity).getStoreId(), 1,
                Constants.STORE_GOODS_RANK_KEY[3]);
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        if (isTAg) {
            showError(EmptyState.class, "1");
        }
    }

    boolean isTAg;

    @Override
    protected void dataObserver() {
        super.dataObserver();

        registerObserver(Constants.STORE_GOODS_RANK_KEY[3], GoodsListInfo.class)
                .observe(this, response -> {
                    ItemData itemData = new ItemData();
                    String s = "";
                    for (GoodsInfo goodsInfo : response.getDatas().getGoods_list()) {
                        if (!s.equals(goodsInfo.getGoods_addtime_text())) {
                            s = goodsInfo.getGoods_addtime_text();
                            itemData.add(s);
                        }
                        itemData.add(goodsInfo);
                    }
                    if (itemData.size() > 0) {
                        setData(itemData);
                    } else {
                        isTAg = true;
                        showError(EmptyState.class, "1");
                    }
                });
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                if (adapter.getItems().get(i) instanceof GoodsInfo) {
                    return 1;
                }
                return 2;
            }
        });
        return gridLayoutManager;
    }

    @Override
    protected boolean isItemDecoration() {
        return false;
    }

    @Override
    protected DelegateAdapter createAdapter() {
        return AdapterPool.newInstance()
                .getStoreNewGoodsAdapter(getActivity())
                .build();
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.STORE_GOODS_RANK_KEY[1];
    }
}
