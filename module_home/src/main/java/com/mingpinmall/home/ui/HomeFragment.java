package com.mingpinmall.home.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mingpinmall.home.R;
import com.mingpinmall.home.R2;

import butterknife.BindView;
import butterknife.OnClick;
import me.goldze.common.base.mvvm.base.BaseFragment;
import me.goldze.common.utils.ToastUtils;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment {

    @BindView(R2.id.tv_home)
    TextView tvHome;

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
        tvHome.setText("这是首页");
    }


    @OnClick(R2.id.tv_home)
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id==R.id.tv_home) {
            ToastUtils.showLong("home");
        }
    }
}
