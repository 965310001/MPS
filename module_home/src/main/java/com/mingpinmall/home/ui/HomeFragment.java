package com.mingpinmall.home.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.home.R;
import com.mingpinmall.home.databinding.FragmentHomeBinding;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment<FragmentHomeBinding> {

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
        tvHome = binding.tvHome;
        tvHome.setText("这是首页");
    }


    //    @OnClick(R2.id.tv_home)
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_home) {
            ToastUtils.showLong("home");
        }
    }
}
