package com.mingpinmall.classz.ui.activity.details;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.view.View;

import com.goldze.common.dmvvm.base.mvvm.base.BaseListFragment;
import com.mingpinmall.classz.adapter.AdapterPool;
import com.mingpinmall.classz.ui.constants.Constants;
import com.mingpinmall.classz.ui.api.ClassifyViewModel;
import com.mingpinmall.classz.ui.vm.bean.GoodsCommentListBean;
import com.socks.library.KLog;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.listener.OnItemClickListener;

import android.support.annotation.Nullable;

/**
 * 商品详情 -商品评价
 */
public class GoodsCommentFragment extends BaseListFragment<ClassifyViewModel> implements OnItemClickListener {

    String type = "";

    public GoodsCommentFragment() {
    }

//    public static GoodsCommentFragment newInstance(String id) {
//        GoodsCommentFragment fragment = new GoodsCommentFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("id", id);
//        fragment.setArguments(bundle);
//        return fragment;
//    }

    public static GoodsCommentFragment newInstance() {
        GoodsCommentFragment fragment = new GoodsCommentFragment();
        return fragment;
    }

    @Override
    protected void getRemoteData() {
        super.getRemoteData();
        KLog.i("EVALUATE_EVENT_KEY");
        mViewModel.getEvaluate(((ShoppingDetailsActivity) activity).getId(), type, String.valueOf(page));
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        /*评价列表*/
        registerObserver(Constants.EVALUATE_EVENT_KEY[0], GoodsCommentListBean.class)
                .observeForever(new Observer<GoodsCommentListBean>() {
                    @Override
                    public void onChanged(@Nullable GoodsCommentListBean response) {
                        KLog.i("EVALUATE_EVENT_KEY");
                        setData(response.getDatas().getGoods_eval_list());
                    }
                });
    }

    @Override
    protected DelegateAdapter createAdapter() {
        return AdapterPool.newInstance()
                .getEvaluate(getActivity())
                .setOnItemClickListener(this)
                .build();
    }

    @Override
    protected Object getStateEventKey() {
        return Constants.EVALUATE_EVENT_KEY[1];
    }

    @Override
    public void onItemClick(View view, int i, Object o) {

    }
}

