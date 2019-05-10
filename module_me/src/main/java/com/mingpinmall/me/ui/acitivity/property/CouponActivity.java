package com.mingpinmall.me.ui.acitivity.property;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.mvvm.base.BaseTabsActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.acitivity.property.coupon.CouponGetFragment;
import com.mingpinmall.me.ui.acitivity.property.coupon.CouponListFragment;

/**
 * 功能描述：店铺代金券
 *
 * @author 小斌
 * @date 2019/3/28
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
