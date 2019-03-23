package com.mingpinmall.home.ui;


import android.os.Bundle;

import com.mingpinmall.home.R;

import me.goldze.common.base.mvvm.base.BaseFragment;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment {

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {
        showSuccess();
    }

}
