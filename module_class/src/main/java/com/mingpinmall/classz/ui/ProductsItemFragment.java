package com.mingpinmall.classz.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.goldze.common.dmvvm.base.mvvm.base.BaseListFragment;
import com.mingpinmall.classz.ui.vm.ClassifyViewModel;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.listener.OnItemClickListener;

public class ProductsItemFragment extends BaseListFragment<ClassifyViewModel> implements OnItemClickListener {

    //    private final static String STATUS = "status";
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
    public void initView(Bundle state) {
        super.initView(state);
        showLoading();
    }

    @Override
    protected DelegateAdapter createAdapter() {
        return null;
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected Object getStateEventKey() {
        return "";
    }

    @Override
    public void onItemClick(View view, int postion, Object o) {

    }
}
