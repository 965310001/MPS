package com.mingpinmall.me.ui.acitivity.property;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.mvvm.base.BaseTabsActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.acitivity.property.couponFragment.CouponGetFragment;
import com.mingpinmall.me.ui.acitivity.property.couponFragment.CouponListFragment;

/**
 * 功能描述：店铺代金券
 * 创建人：小斌
 * 创建时间: 2019/3/28
 **/
@Route(path = ARouterConfig.Me.COUPONACTIVITY)
public class CouponActivity extends BaseTabsActivity {

    @Override
    protected String[] initTabs() {
        return new String[]{getString(R.string.tabs_text_coupon1), getString(R.string.tabs_text_coupon2)};
    }

    @Override
    protected Class[] initFragments() {
        return new Class[]{CouponListFragment.class, CouponGetFragment.class};
    }

}
