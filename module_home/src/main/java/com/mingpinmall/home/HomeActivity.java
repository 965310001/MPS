package com.mingpinmall.home;

import android.os.Bundle;

import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.mingpinmall.home.databinding.ActivityHomeBinding;
import com.mingpinmall.home.ui.HomeFragment;


public class HomeActivity extends BaseActivity<ActivityHomeBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("首页");
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, HomeFragment.newInstance()).commit();
    }

    @Override
    protected boolean isBack() {
        return false;
    }
}
