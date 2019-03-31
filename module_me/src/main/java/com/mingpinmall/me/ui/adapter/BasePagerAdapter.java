package com.mingpinmall.me.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：ViewPager适配器，适用于少量page
 * 创建人：小斌
 * 创建时间: 2019/3/26
 **/
public class BasePagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();
    private Context context;

    public BasePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    public void addFragment(Fragment fragment, String title) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }

    public void addFragment(Fragment fragment, @StringRes int title) {
        mFragments.add(fragment);
        mFragmentTitles.add(context.getResources().getString(title));
    }

    public void addFragments(Fragment... fragments) {
        for (Fragment fragment :
                fragments) {
            mFragments.add(fragment);
        }
    }

    public void addTitles(String... titles) {
        for (String title :
                titles) {
            mFragmentTitles.add(title);
        }
    }

    public void addTitles(@StringRes int... titleIds) {
        for (int title :
                titleIds) {
            mFragmentTitles.add(context.getResources().getString(title));
        }
    }

    public int getFragmentsSize() {
        return mFragments.size();
    }


    public void delFragment(int index) {
        mFragments.remove(index);
        mFragmentTitles.remove(index);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }

    public void clearAllFragment() {
        mFragments.removeAll(mFragments);
        mFragmentTitles.removeAll(mFragmentTitles);
    }

}
