package com.mingpinmall.cart.ui.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;

/**
 * 提交订单
 * @author 小斌
 * @data 2019/5/20
 **/
@Route(path = ARouterConfig.cart.PAYSTEPONEACTIVITY)
public class PayStepOneActivity extends AbsLifecycleActivity {
    @Override
    protected Object getStateEventKey() {
        return "PayStepOneActivity";
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
