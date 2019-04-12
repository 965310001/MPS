package com.mingpinmall.me.ui.acitivity.order;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityOrderBinding;
import com.mingpinmall.me.ui.acitivity.order.PhysicalObject.AllPhysicalFragment;
import com.mingpinmall.me.ui.acitivity.order.PhysicalObject.ScorePhysicalFragment;
import com.mingpinmall.me.ui.acitivity.order.PhysicalObject.WaitHarvest2PhysicalFragment;
import com.mingpinmall.me.ui.acitivity.order.PhysicalObject.WaitHarvestPhysicalFragment;
import com.mingpinmall.me.ui.acitivity.order.PhysicalObject.WaitPayPhysicalFragment;
import com.mingpinmall.me.ui.acitivity.order.VirtualObject.AllVirtualFragment;
import com.mingpinmall.me.ui.acitivity.order.VirtualObject.WaitPayVirtualFragment;
import com.mingpinmall.me.ui.acitivity.order.VirtualObject.WaitUseVirtualFragment;
import com.mingpinmall.me.ui.adapter.BasePagerStateAdapter;
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

    private BasePagerStateAdapter pagerAdapter;

    //实物订单
    private AllPhysicalFragment physicalFragment;
    private ScorePhysicalFragment scorePhysicalFragment;
    private WaitHarvestPhysicalFragment harvestPhysicalFragment;
    private WaitHarvest2PhysicalFragment harvest2PhysicalFragment;
    private WaitPayPhysicalFragment payPhysicalFragment;

    //虚拟订单
    private AllVirtualFragment virtualFragment;
    private WaitPayVirtualFragment payVirtualFragment;
    private WaitUseVirtualFragment useVirtualFragment;

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

        pagerAdapter = new BasePagerStateAdapter(getSupportFragmentManager(), this);
        binding.viewPager.setAdapter(pagerAdapter);
        binding.viewPager.setNoScroll(false);
        binding.tabs.setupWithViewPager(binding.viewPager);

        try {
            tabControlView.setItems(new String[]{getString(R.string.tabs_text_physical), getString(R.string.tabs_text_virtual)}, new String[]{"1", "2"});
        } catch (Exception e) {
            e.printStackTrace();
        }
        initFragments();
        setFunctionPage("1");
        binding.viewPager.setCurrentItem(pageIndex);
        tabControlView.setOnTabSelectionChangedListener(new TabControlView.OnTabSelectionChangedListener() {
            @Override
            public void newSelection(String title, String value) {
                setFunctionPage(value);
            }
        });
    }

    private void initFragments() {
        physicalFragment = new AllPhysicalFragment();
        scorePhysicalFragment = new ScorePhysicalFragment();
        harvestPhysicalFragment = new WaitHarvestPhysicalFragment();
        harvest2PhysicalFragment = new WaitHarvest2PhysicalFragment();
        payPhysicalFragment = new WaitPayPhysicalFragment();

        virtualFragment = new AllVirtualFragment();
        payVirtualFragment = new WaitPayVirtualFragment();
        useVirtualFragment = new WaitUseVirtualFragment();
    }

    /**
     * 获得对应的Fragment
     *
     * @param index
     */
    private void setFunctionPage(String index) {
        Log.i("设置", "setFunctionPage: 111110");
        pagerAdapter.clearAllFragment();
        switch (index) {
            case "1":
                /** 实物订单 **/
                pagerAdapter.addFragments(
                        physicalFragment,
                        payPhysicalFragment,
                        harvestPhysicalFragment,
                        harvest2PhysicalFragment,
                        scorePhysicalFragment
                );
                pagerAdapter.addTitles(
                        getString(R.string.tabs_text_all),
                        getString(R.string.tabs_text_pay),
                        getString(R.string.tabs_text_haverst),
                        getString(R.string.tabs_text_haverst2),
                        getString(R.string.tabs_text_uncomments)
                );
                break;
            case "2":
                /** 虚拟订单 **/
                pagerAdapter.addFragments(
                        virtualFragment,
                        payVirtualFragment,
                        useVirtualFragment
                );
                pagerAdapter.addTitles(
                        getString(R.string.tabs_text_all),
                        getString(R.string.tabs_text_pay),
                        getString(R.string.tabs_text_unuse)
                );
                break;
        }
        pagerAdapter.notifyDataSetChanged();
        binding.viewPager.setOffscreenPageLimit(index.equals("1") ? 5 : 3);
        binding.viewPager.setCurrentItem(0, false);
    }

    public String getOrderKey() {

        return binding.edSearch.getText().toString();
    }
}
