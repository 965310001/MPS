package com.mingpinmall.home;

import android.os.Bundle;

import me.goldze.common.base.mvvm.base.test.BaseActivity;

public class HomeActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("首页");
    }

    @Override
    protected boolean isBack() {
        return false;
    }
}
