package com.mingpinmall.me.ui.acitivity.order;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentDefaultTabviewpagerBinding;
import com.mingpinmall.me.ui.adapter.BasePagerAdapter;

/**
 * 功能描述：虚拟订单
 * 创建人：小斌
 * 创建时间: 2019/4/13
 **/
public class VirtualOrderFragment extends BaseFragment<FragmentDefaultTabviewpagerBinding> {

    private BasePagerAdapter pagerAdapter;

    public VirtualOrderFragment() {
    }

    public void refreshCurrentPage() {
        ((VirtualOrderListFragment) pagerAdapter.getItem(binding.viewPager.getCurrentItem())).lazyLoad();
    }

    public static VirtualOrderFragment newInstance() {
        VirtualOrderFragment fragment = new VirtualOrderFragment();
        return fragment;
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
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
        pagerAdapter = new BasePagerAdapter(getChildFragmentManager(), activity);
        binding.viewPager.setAdapter(pagerAdapter);
        binding.tabs.setupWithViewPager(binding.viewPager);

        pagerAdapter.addFragment(//全部虚拟订单
                VirtualOrderListFragment.newInstance("", "ALL_VIRTUAL"),
                R.string.tabs_text_all
        );
        pagerAdapter.addFragment(//待付款
                VirtualOrderListFragment.newInstance("state_new", "PAY_VIRTUAL"),
                R.string.tabs_text_pay)
        ;
        pagerAdapter.addFragment(//待使用
                VirtualOrderListFragment.newInstance("state_send", "USE_VIRTUAL"),
                R.string.tabs_text_unuse
        );
        binding.viewPager.setAdapter(pagerAdapter);
    }
}
