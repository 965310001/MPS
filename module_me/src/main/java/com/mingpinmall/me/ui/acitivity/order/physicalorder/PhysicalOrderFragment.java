package com.mingpinmall.me.ui.acitivity.order.physicalorder;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentDefaultTabviewpagerBinding;
import com.mingpinmall.me.ui.adapter.BasePagerAdapter;

/**
 * 功能描述：实物订单
 * @author 小斌
 * @date 2019/4/13
 **/
public class PhysicalOrderFragment extends BaseFragment<FragmentDefaultTabviewpagerBinding> {

    int pageIndex = 0;
//    private BasePagerAdapter pagerAdapter;

    public PhysicalOrderFragment() {
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public static PhysicalOrderFragment newInstance(int pageIndex) {
        Bundle bundle = new Bundle();
        bundle.putInt("pageIndex", pageIndex);

        PhysicalOrderFragment fragment = new PhysicalOrderFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        pageIndex = args.getInt("pageIndex");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_default_tabviewpager;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {
        LiveBus.getDefault().subscribe("PAGER", "ORDER", Integer.class).observeForever(integer -> pageIndex = integer);

        BasePagerAdapter pagerAdapter = new BasePagerAdapter(getChildFragmentManager(), activity);
        pagerAdapter.addFragment(//实物订单
                PhysicalOrderListFragment.newInstance("", "ALL_PHYSICAL"),
                R.string.tabs_text_all
        );
        pagerAdapter.addFragment(//待付款
                PhysicalOrderListFragment.newInstance("state_new", "PAY_PHYSICAL"),
                R.string.tabs_text_pay)
        ;
        pagerAdapter.addFragment(//待收货
                PhysicalOrderListFragment.newInstance("state_send", "HARVEST_PHYSICAL"),
                R.string.tabs_text_haverst
        );
        pagerAdapter.addFragment(//待自提
                PhysicalOrderListFragment.newInstance("state_notakes", "HARVEST2_PHYSICAL"),
                R.string.tabs_text_haverst2
        );
        pagerAdapter.addFragment(//待评价
                PhysicalOrderListFragment.newInstance("state_noeval", "SCORE_PHYSICAL"),
                R.string.tabs_text_uncomments
        );
        binding.viewPager.setAdapter(pagerAdapter);
        binding.tabs.setViewPager(binding.viewPager);
        binding.viewPager.setCurrentItem(pageIndex, false);
    }
}
