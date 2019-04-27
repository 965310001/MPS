package com.mingpinmall.me.ui.acitivity.order;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityOrderDeliveryBinding;
import com.mingpinmall.me.ui.adapter.OrderDeliverysAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.OrderDeliverListBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 功能描述：物流信息
 * 创建人：小斌
 * 创建时间: 2019/4/27
 **/
@Route(path = ARouterConfig.Me.ORDERDELIVERYACTIVITY)
public class OrderDeliveryActivity extends AbsLifecycleActivity<ActivityOrderDeliveryBinding, MeViewModel> {

    private OrderDeliverysAdapter listAdapter;

    @Autowired
    String order_id;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        ARouter.getInstance().inject(this);
        setTitle(R.string.title_OrderDeliveryActivity);

        listAdapter = new OrderDeliverysAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerView.setAdapter(listAdapter);

        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
            }
        });
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver("ORDER_DELIVER_LIST", "success", OrderDeliverListBean.class).observeForever(new Observer<OrderDeliverListBean>() {
            @Override
            public void onChanged(@Nullable OrderDeliverListBean result) {
                binding.refreshLayout.finishRefresh();
                binding.setData(result);
                List<String> dataList = result.getDeliver_info();
                Collections.reverse(dataList);
                listAdapter.setNewData(dataList);
            }
        });
        registerObserver("ORDER_DELIVER_LIST", "err", String.class).observeForever(new Observer<String>() {
            @Override
            public void onChanged(@Nullable String msg) {
                ToastUtils.showShort(msg);
                binding.refreshLayout.finishRefresh(false);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mViewModel.getOrderDeliverList(order_id);
    }

    @Override
    protected Object getStateEventKey() {
        return "OrderDeliveryActivity";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_delivery;
    }
}
