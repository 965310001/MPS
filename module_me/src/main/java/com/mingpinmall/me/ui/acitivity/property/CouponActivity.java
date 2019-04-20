package com.mingpinmall.me.ui.acitivity.property;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityBaseTabsViewpagerBinding;
import com.mingpinmall.me.ui.acitivity.property.couponFragment.CouponGetFragment;
import com.mingpinmall.me.ui.acitivity.property.couponFragment.CouponListFragment;
import com.mingpinmall.me.ui.adapter.BasePagerAdapter;
import com.xuexiang.xui.widget.tabbar.TabControlView;

/**
 * 功能描述：店铺代金券
 * 创建人：小斌
 * 创建时间: 2019/3/28
 **/
@Route(path = ARouterConfig.Me.COUPONACTIVITY)
public class CouponActivity extends BaseActivity<ActivityBaseTabsViewpagerBinding> {

    private BasePagerAdapter pagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_tabs_viewpager;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        pagerAdapter = new BasePagerAdapter(getSupportFragmentManager(), activity);
        pagerAdapter.addFragment(CouponListFragment.newFragment(), getString(R.string.tabs_text_coupon1));
        pagerAdapter.addFragment(CouponGetFragment.newFragment(), getString(R.string.tabs_text_coupon2));

        binding.viewPager.setNoScroll(true);
        binding.viewPager.setAdapter(pagerAdapter);

        try {
            tabControlView.setItems(new String[]{getString(R.string.tabs_text_coupon1), getString(R.string.tabs_text_coupon2)}, new String[]{"1", "2"});
        } catch (Exception e) {
            e.printStackTrace();
        }
        tabControlView.setOnTabSelectionChangedListener(new TabControlView.OnTabSelectionChangedListener() {
            @Override
            public void newSelection(String title, String value) {
                switch (value) {
                    case "1":
                        binding.viewPager.setCurrentItem(0, false);
                        break;
                    case "2":
                        binding.viewPager.setCurrentItem(1, false);
                        break;
                }
            }
        });
    }

    @Override
    protected boolean isTabsBar() {
        return true;
    }
}
