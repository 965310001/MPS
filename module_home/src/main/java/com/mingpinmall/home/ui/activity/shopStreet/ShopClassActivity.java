package com.mingpinmall.home.ui.activity.shopStreet;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/15
 **/
@Route(path = ARouterConfig.home.SHOPCLASSACTIVITY)
public class ShopClassActivity extends AbsLifecycleActivity {
    @Override
    protected Object getStateEventKey() {
        return "SHOP_CLASS_ACTIVITY";
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
