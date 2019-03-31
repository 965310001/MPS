package com.mingpinmall.me;

import android.os.Bundle;
import android.view.View;

import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.mingpinmall.me.databinding.ActivityMeBinding;
import com.mingpinmall.me.ui.MeFragment;

/**
 * 我的
 */
public class MeActivity extends BaseActivity<ActivityMeBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_me;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("我的");
        findViewById(R.id.rl_title_bar).setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, MeFragment.newInstance()).commit();
    }

    @Override
    protected boolean isBack() {
        return false;
    }
}
