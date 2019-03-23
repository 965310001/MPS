package com.mingpinmall.classz;

import android.os.Bundle;

import me.goldze.common.base.mvvm.base.test.BaseActivity;

public class ClasszActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_classz;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("分类");
    }

    @Override
    protected boolean isBack() {
        return false;
    }
}