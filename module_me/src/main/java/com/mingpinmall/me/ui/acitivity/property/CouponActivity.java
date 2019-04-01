package com.mingpinmall.me.ui.acitivity.property;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityBaseTabsViewpagerBinding;
import com.xuexiang.xui.widget.tabbar.TabControlView;

/**
 * 功能描述：店铺代金券
 * 创建人：小斌
 * 创建时间: 2019/3/28
 **/
@Route(path = ARouterConfig.COUPONACTIVITY)
public class CouponActivity extends BaseActivity<ActivityBaseTabsViewpagerBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_tabs_viewpager;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        findViewById(R.id.rl_title_bar).setVisibility(View.GONE);
        binding.ivBackbtn.setOnClickListener(this);
        try {
            binding.tabs.setItems(new String[]{getString(R.string.tabs_text_coupon1), getString(R.string.tabs_text_coupon2)}, new String[]{"1", "2"});
        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.tabs.setOnTabSelectionChangedListener(new TabControlView.OnTabSelectionChangedListener() {
            @Override
            public void newSelection(String title, String value) {
            }
        });
    }

    @Override
    protected boolean isBack() {
        return false;
    }

    @Override
    public void onViewClicked(int viewId) {
        if (viewId == R.id.iv_backbtn) {
            onBackPressed();
        }
    }
}