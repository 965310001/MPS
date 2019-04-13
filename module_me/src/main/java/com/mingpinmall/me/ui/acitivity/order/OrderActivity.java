package com.mingpinmall.me.ui.acitivity.order;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityOrderBinding;
import com.mingpinmall.me.ui.adapter.BasePagerAdapter;
import com.xuexiang.xui.widget.tabbar.TabControlView;

/**
 * 功能描述：我的订单
 * 创建人：小斌
 * 创建时间: 2019/3/26
 **/
@Route(path = ARouterConfig.ORDERACTIVITY)
public class OrderActivity extends BaseActivity<ActivityOrderBinding> {

    @Autowired
    int pageIndex = 0;

    private BasePagerAdapter pagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    protected boolean isTabsBar() {
        return true;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);

        pagerAdapter = new BasePagerAdapter(getSupportFragmentManager(), this);
        pagerAdapter.addFragment(PhysicalOrderFragment.newInstance(pageIndex), "");
        pagerAdapter.addFragment(VirtualOrderFragment.newInstance(), "");

        binding.viewPager.setAdapter(pagerAdapter);
        binding.ivSearch.setOnClickListener(this);

        try {
            tabControlView.setItems(new String[]{getString(R.string.tabs_text_physical), getString(R.string.tabs_text_virtual)}, new String[]{"1", "2"});
        } catch (Exception e) {
            e.printStackTrace();
        }
        tabControlView.setOnTabSelectionChangedListener(new TabControlView.OnTabSelectionChangedListener() {
            @Override
            public void newSelection(String title, String value) {
                binding.viewPager.setCurrentItem(value.equals("1") ? 0 : 1, false);
            }
        });
    }

    @Override
    public void onViewClicked(int viewId) {
        if (viewId == R.id.iv_search) {
            switch (binding.viewPager.getCurrentItem()) {
                case 0:
                    ((PhysicalOrderFragment) pagerAdapter.getItem(binding.viewPager.getCurrentItem())).refreshCurrentPage();
                    break;
                case 1:
                    ((VirtualOrderFragment) pagerAdapter.getItem(binding.viewPager.getCurrentItem())).refreshCurrentPage();
                    break;
            }
        }
    }

    public String getOrderKey() {
        return binding.edSearch.getText().toString() == null ? "" : binding.edSearch.getText().toString();
    }
}
