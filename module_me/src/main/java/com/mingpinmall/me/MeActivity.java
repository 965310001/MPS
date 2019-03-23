package com.mingpinmall.me;

import android.os.Bundle;

import me.goldze.common.base.mvvm.base.test.BaseActivity;

/**
 * 我的
 */
public class MeActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_me;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("我的");
    }

    @Override
    protected boolean isBack() {
        return false;
    }
}
