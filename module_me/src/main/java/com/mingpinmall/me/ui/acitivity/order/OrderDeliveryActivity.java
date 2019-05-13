package com.mingpinmall.me.ui.acitivity.order;

import android.os.Bundle;
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
import com.mingpinmall.me.ui.constants.Constants;

import java.util.Collections;
import java.util.List;

/**
 * 功能描述：物流信息
 * *@author 小斌
 * @date 2019/4/27
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
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> initData());
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.ORDER_DELIVER_LIST, Object.class).observeForever(result -> {
            if (result instanceof OrderDeliverListBean) {
                OrderDeliverListBean data = (OrderDeliverListBean) result;
                binding.refreshLayout.finishRefresh();
                binding.setData(data);
                List<String> dataList = data.getDeliver_info();
                Collections.reverse(dataList);
                listAdapter.setNewData(dataList);
            } else {
                ToastUtils.showShort(result.toString());
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
