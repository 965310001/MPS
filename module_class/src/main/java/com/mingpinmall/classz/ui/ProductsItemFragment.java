package com.mingpinmall.classz.ui;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.base.BaseListFragment;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.ui.vm.ClassifyViewModel;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
import com.mingpinmall.classz.ui.vm.bean.GoodsListInfo;
import com.socks.library.KLog;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.listener.OnItemClickListener;

import java.util.ArrayList;

public class ProductsItemFragment extends BaseListFragment<ClassifyViewModel> implements OnItemClickListener {

    private final static String ID = "id";

    public ProductsItemFragment() {
    }

    public static ProductsItemFragment newInstance(String id) {
        ProductsItemFragment fragment = new ProductsItemFragment();
        Bundle args = new Bundle();
        args.putString(ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected boolean isItemDecoration() {
        return false;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
//        showLoading();
        mViewModel.getShappingList(getArguments().getString(ID), String.valueOf(page), "");
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();

        LiveBus.getDefault()
                .subscribe(Constants.PRODUCTS_EVENT_KEY, GoodsListInfo.class)
                .observeForever(new Observer<GoodsListInfo>() {
                    @Override
                    public void onChanged(@Nullable GoodsListInfo response) {
                        KLog.i("abs");
                        setData(response.getDatas().getGoods_list());
                    }
                });
    }

    @Override
    protected DelegateAdapter createAdapter() {
        return AdapterPool.newInstance()
                .getProductsAdapter(getActivity())
                .setOnItemClickListener(this)
                .build();
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.PRODUCTS_EVENT_KEY;
    }

    @Override
    public void onItemClick(View view, int postion, Object o) {

    }
}
