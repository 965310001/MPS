package com.goldze.common.dmvvm.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.goldze.common.dmvvm.R;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.goldze.common.dmvvm.databinding.ActivityHorizontalTabBinding;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.tabbar.EasyIndicator;

import java.util.List;


public abstract class HorizontalTabActivity extends BaseActivity<ActivityHorizontalTabBinding> {

    EasyIndicator mEasyIndicator;
    ViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_horizontal_tab;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mEasyIndicator = binding.easyIndicator;
        mViewPager = binding.viewPager;

        mEasyIndicator.setTabTitles(getTabTitles());
        mEasyIndicator.setViewPager(mViewPager,
                new FragmentAdapter<>(getSupportFragmentManager(),
                        getTabFragments()));
        mViewPager.setOffscreenPageLimit(getTabTitles().length);
    }

    /**
     * 根据需求对象Fragment
     *
     * @param position
     */
    protected void onPageSelected(int position) {
        mViewPager.setCurrentItem(position);
        mEasyIndicator.onPageSelected(position);
    }

    protected abstract String[] getTabTitles();

    protected abstract List<BaseFragment> getTabFragments();
}
