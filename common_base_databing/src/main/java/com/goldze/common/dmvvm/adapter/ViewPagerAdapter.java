package com.goldze.common.dmvvm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * @author GuoFeng
 * @date :2019/1/17 17:01
 * @description:
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> fragments;

    private List<String> mTitles;

    public ViewPagerAdapter(FragmentManager fm, List<BaseFragment> lists, List<String> titles) {
        super(fm);
        fragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        fragments.addAll(lists);
        mTitles.addAll(titles);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }
}
