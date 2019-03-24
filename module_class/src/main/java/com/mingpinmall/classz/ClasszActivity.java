package com.mingpinmall.classz;

import android.os.Bundle;

import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.mingpinmall.classz.ui.ClassifyFragment;


public class ClasszActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_classz;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("分类");
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, ClassifyFragment.newInstance()).commit();
    }

    @Override
    protected boolean isBack() {
        return false;
    }
}