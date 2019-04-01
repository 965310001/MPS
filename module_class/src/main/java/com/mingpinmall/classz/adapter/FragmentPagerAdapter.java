package com.mingpinmall.classz.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.goldze.common.dmvvm.base.bean.HorizontalTabTitle;
import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;

import java.util.List;

public class FragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<HorizontalTabTitle> titles;
    private List<BaseFragment> fragments;

    /**
     * 使用该构造方法 必须重写 getTabFragment 返回对应的Fragment
     */
    public FragmentPagerAdapter(FragmentManager fm, List<HorizontalTabTitle> titles) {
        this(fm, titles, null);
    }

    public FragmentPagerAdapter(FragmentManager fm, List<HorizontalTabTitle> titles, List<BaseFragment> fragments) {
        super(fm);
        this.titles = titles;
        this.fragments = fragments;
    }


    @Override
    public int getCount() {
        return titles != null ? titles.size() : 0;
    }


    @Override
    public Fragment getItem(int position) {
        BaseFragment fragment;
        if (fragments == null || fragments.isEmpty()) {
            fragment = getTabFragment();
        } else {
            fragment = fragments.get(position);
        }
        if (fragment == null) {
            throw new NullPointerException("Fragment is  null,please give me a fragment!");
        }
        // TODO: 2019/1/24 设置数据
//        fragment.setFragmentData(titles.get(position));
        return fragment;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public void startUpdate(ViewGroup container) {
        super.startUpdate(container);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position).getTitle();
    }

    /**
     * 使用两参构造方法 必须重写 该方法 返回对应的Fragment
     */
    public BaseFragment getTabFragment() {
        return null;
    }
}

