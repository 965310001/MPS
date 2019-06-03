package com.mingpinmall.me.ui.acitivity.order.virtualorder;

import android.os.Bundle;

import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentDefaultTabviewpagerBinding;
import com.mingpinmall.me.ui.adapter.BasePagerAdapter;

/**
 * 功能描述：虚拟订单
 *
 * @author 小斌
 * @date 2019/4/13
 **/
public class VirtualOrderFragment extends BaseFragment<FragmentDefaultTabviewpagerBinding> {

    public VirtualOrderFragment() {
    }

    public static VirtualOrderFragment newInstance() {
        return new VirtualOrderFragment();
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
        BasePagerAdapter pagerAdapter = new BasePagerAdapter(getChildFragmentManager(), activity);
        pagerAdapter.addFragment(//全部虚拟订单
                VirtualOrderListFragment.newInstance("", "ALL_VIRTUAL"),
                R.string.tabs_text_all
        );
        pagerAdapter.addFragment(//待付款
                VirtualOrderListFragment.newInstance("state_new", "PAY_VIRTUAL"),
                R.string.tabs_text_pay
        );
        pagerAdapter.addFragment(//待使用
                VirtualOrderListFragment.newInstance("state_pay", "USE_VIRTUAL"),
                R.string.tabs_text_unuse
        );
        binding.viewPager.setAdapter(pagerAdapter);
        binding.tabs.setViewPager(binding.viewPager);
    }
}
