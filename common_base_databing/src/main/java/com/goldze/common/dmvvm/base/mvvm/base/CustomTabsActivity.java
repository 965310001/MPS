package com.goldze.common.dmvvm.base.mvvm.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 功能描述：标题栏上Tabs分页按钮，适用于不使用ViewPager切换Fragment的页面
 * *@author 小斌
 * @date 2019/4/29
 **/
public abstract class CustomTabsActivity<T extends ViewDataBinding> extends BaseActivity<T> {

    protected int index = 0;

    private Fragment currentFrament;
    private List<String> titles;
    private List<Class> classes;
    private List<Fragment> fragments;

    protected Fragment getFragments(int index) {
        if (index >= fragments.size()) {
            return null;
        }
        return fragments.get(index);
    }

    /**
     * @return Titles String & Fragment Class
     */
    protected abstract String[] initTabs();
    protected abstract Class[] initFragments();

    @Override
    protected boolean isTabsBar() {
        return true;
    }

    /**
     * @return Fragment into
     */
    protected abstract int getFrameId();

    protected void setIndex(int index) {
        this.index = index;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        titles = Arrays.asList(initTabs());
        classes = Arrays.asList(initFragments());
        fragments = new ArrayList<>();
        Fragment fragment;
        for (Class clx : classes) {
            fragment = getSupportFragmentManager().findFragmentByTag(clx.getSimpleName());
            if (fragment == null) {
                try {
                    fragment = (Fragment) clx.newInstance();
                } catch (IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                    return;
                }
            }
            fragments.add(fragment);
        }
        tabControlView.setOnTabSelectionChangedListener((title, value) -> switchFragment(titles.indexOf(title)));
        try {
            tabControlView.setItems(titles.toArray(new String[titles.size()]), null);
            tabControlView.setDefaultSelection(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void switchFragment(int index) {
        if (index > fragments.size() - 1) {
            return;
        }
        Fragment fragment = fragments.get(index);
        if (fragment != currentFrament) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (!fragment.isAdded()) {
                if (currentFrament == null) {
                    transaction.add(getFrameId(), fragment, fragment.getClass().getSimpleName()).commitNowAllowingStateLoss();
                } else {
                    transaction.hide(currentFrament).add(getFrameId(), fragment, fragment.getClass().getSimpleName()).commitNowAllowingStateLoss();
                }
            } else {
                transaction.hide(currentFrament).show(fragment).commitNowAllowingStateLoss();
            }
            currentFrament = fragment;
        }
    }

}
