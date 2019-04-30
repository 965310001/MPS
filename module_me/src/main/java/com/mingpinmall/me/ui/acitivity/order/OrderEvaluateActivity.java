package com.mingpinmall.me.ui.acitivity.order;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityOrderevaluateBinding;
import com.mingpinmall.me.ui.adapter.OrderEvaluateAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.OrderEvaluateBean;

/**
 * 功能描述：订单评价
 * 创建人：小斌
 * 创建时间: 2019/4/30
 **/
@Route(path = ARouterConfig.Me.ORDEREVALUATEACTIVITY)
public class OrderEvaluateActivity extends AbsLifecycleActivity<ActivityOrderevaluateBinding, MeViewModel> {

    @Autowired
    String id;

    private OrderEvaluateAdapter evaluateAdapter;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        ARouter.getInstance().inject(this);
        setTitle(R.string.title_OrderEvaluateActivity);
        evaluateAdapter = new OrderEvaluateAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerView.setAdapter(evaluateAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mViewModel.getOrderEvaluate(id);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver("ORDER_EVALUATE_LIST", Object.class).observeForever(new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object result) {
                //可评价商品列表
                if (result instanceof OrderEvaluateBean) {
                    //获取成功
                    evaluateAdapter.setNewData(((OrderEvaluateBean) result).getOrder_goods());
                } else {
                    //获取失败
                    ToastUtils.showShort(result.toString());
                }
            }
        });
    }

    @Override
    protected Object getStateEventKey() {
        return "OrderEvaluateActivity";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_orderevaluate;
    }
}
