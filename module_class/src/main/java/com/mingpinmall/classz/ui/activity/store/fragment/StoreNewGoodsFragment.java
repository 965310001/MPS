package com.mingpinmall.classz.ui.activity.store.fragment;

import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
import com.mingpinmall.classz.ui.vm.bean.GoodsListInfo;
import com.mingpinmall.classz.ui.vm.bean.ScreenInfo;
import com.mingpinmall.classz.ui.vm.bean.TypeInfo;
import com.mingpinmall.classz.widget.CustomPopWindow;
import com.mingpinmall.classz.widget.FilterTab;
import com.mingpinmall.classz.widget.ScreeningPopWindow;
import com.socks.library.KLog;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.adapter.ItemData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.operators.observable.ObservableGroupBy;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.subjects.Subject;

/**
 * 商品上新
 */
public class StoreNewGoodsFragment extends BaseListFragment<ClassifyViewModel> {

    String id;

    public static StoreNewGoodsFragment newInstance() {
        StoreNewGoodsFragment fragment = new StoreNewGoodsFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    /*获取更多数据*/
    @Override
    protected void getRemoteData() {
        super.getRemoteData();

        id = ((StoreActivity) activity).getStoreId();

        mViewModel.getStoreNewGoods(id, 1,
                Constants.STORE_GOODS_RANK_KEY[3]);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();

        registerObserver(Constants.STORE_GOODS_RANK_KEY[3], GoodsListInfo.class)
                .observe(this, new Observer<GoodsListInfo>() {
                    @Override
                    public void onChanged(@Nullable GoodsListInfo response) {
                        KLog.i("TAG123", "新品" + response.getDatas().getGoods_list().size());

                        ItemData itemData = new ItemData();
                        String s = "";

                        for (GoodsInfo goodsInfo : response.getDatas().getGoods_list()) {
                            if (!s.equals(goodsInfo.getGoods_addtime_text())) {
                                s = goodsInfo.getGoods_addtime_text();
                                itemData.add(s);
                            }
                            itemData.add(goodsInfo);
                        }
                        KLog.i(itemData);
                        setData(itemData);
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
