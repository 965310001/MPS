package com.goldze.common.dmvvm.base.mvvm.base;


import android.os.Bundle;

import com.flyco.tablayout.SlidingTabLayout;
import com.goldze.common.dmvvm.R;
import com.goldze.common.dmvvm.adapter.ViewPagerAdapter;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.base.mvvm.AbsViewModel;
import com.goldze.common.dmvvm.databinding.FragmentViewpagerBinding;
import com.goldze.common.dmvvm.widget.NestedViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author GuoFeng
 * @date :2019/1/17 17:15
 * @description:
 */
public abstract class BaseViewPagerFragment<T extends AbsViewModel> extends AbsLifecycleFragment<FragmentViewpagerBinding, T> {

    protected SlidingTabLayout mTabLayout;

    protected NestedViewPager mViewPager;

//    protected RelativeLayout mTitleBar;
//    protected TextView mTitle;

    protected ViewPagerAdapter adapter;

    protected List<BaseFragment> mFragments;

    protected List<String> mTitles;

    protected String[] mArrTitles;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_viewpager;
    }

    @Override
    public int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showLoadingState();
        mTabLayout = getViewById(R.id.tab_layout);
        mViewPager = getViewById(R.id.view_pager);
//        mTitleBar = getViewById(R.id.rl_title_bar);
//        mTitle = getViewById(R.id.tv_title);
        mTitles = new ArrayList<>();
        mFragments = new ArrayList<>();
    }

    /**
     * init adapter
     */
    protected void setAdapter() {
        mTitles.addAll(Arrays.asList(createPageTitle()));
        adapter = new ViewPagerAdapter(getChildFragmentManager(), createFragments(), mTitles);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(mTitles.size());
        mTabLayout.setViewPager(mViewPager, mTitles.toArray(createPageTitle()));
    }

    /**
     * create ViewPager title
     *
     * @return String[]
     */
    protected abstract String[] createPageTitle();

    /**
     * create Fragment
     *
     * @return List<BaseFragment>
     */
    protected abstract List<BaseFragment> createFragments();


//    /**
//     * set title
//     *
//     * @param titleName
//     */
//    protected void setTitle(String titleName) {
//        mTitleBar.setVisibility(View.VISIBLE);
//        mTitle.setText(titleName);
//    }
}