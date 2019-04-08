package com.mingpinmall.me.ui.acitivity.collection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityFootprintBinding;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * 功能描述：我的足迹
 * 创建人：小斌
 * 创建时间: 2019/3/27
 **/
@Route(path = ARouterConfig.FOOTPRINTACTIVITY)
public class FootprintActivity extends BaseActivity<ActivityFootprintBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_footprint;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle(getString(R.string.title_footprintActivity));
        tvRight.setText(getString(R.string.text_clearn));
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setOnClickListener(this);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }

    @Override
    public void onViewClicked(int viewId) {
        if (viewId == R.id.tv_right) {
            //清空记录
        }
    }
}
