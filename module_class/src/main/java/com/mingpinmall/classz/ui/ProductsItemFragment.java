package com.mingpinmall.classz.ui;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.goldze.common.dmvvm.base.mvvm.base.BaseListFragment;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.constants.Constants;
import com.mingpinmall.classz.ui.vm.ClassifyViewModel;
import com.mingpinmall.classz.ui.vm.bean.GoodsListInfo;
import com.socks.library.KLog;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.listener.OnItemClickListener;

public class ProductsItemFragment extends BaseListFragment<ClassifyViewModel> implements OnItemClickListener {

    private final static String ID = "id";
    private final static String TYPEID = "typeId";
    private String typeId;
    private String keyword = "";

    public ProductsItemFragment() {
    }

    public static ProductsItemFragment newInstance(String id, String typeId) {
        ProductsItemFragment fragment = new ProductsItemFragment();
        Bundle args = new Bundle();
        args.putString(ID, id);
        args.putString(TYPEID, typeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected boolean isItemDecoration() {
        return false;
    }

    //    @Override
//    protected BaseItemAdapter createAdapter() {
//        return new BaseItemAdapter();
//    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);

    }

    //    @Override
//    protected void register(BaseItemAdapter adapter) {
//        adapter.register(GoodsInfo.class,
//                new DataBindViewHolderManager(R.layout.item_products, BR.data));
//    }

    /*获取更多数据*/
    @Override
    protected void getRemoteData() {
        super.getRemoteData();

        mViewModel.getShappingList(getArguments().getString(ID), String.valueOf(page), keyword, typeId);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        if (getArguments() != null) {
            typeId = getArguments().getString(TYPEID);
        }
        registerObserver(Constants.PRODUCTS_EVENT_KEY, typeId, GoodsListInfo.class)
                .observe(this, new Observer<GoodsListInfo>() {
                    @Override
                    public void onChanged(@Nullable GoodsListInfo response) {
                        KLog.i("111" + typeId);
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
        return Constants.PRODUCTS_EVENT_KEY_LIST_STATE;
    }

    @Override
    protected String getStateEventTag() {
        return typeId;
    }

    @Override
    public void onItemClick(View view, int postion, Object o) {

    }
}
