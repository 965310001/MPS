package com.mingpinmall.me.ui.acitivity.collection;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleActivity;
import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.goldze.common.dmvvm.widget.dialog.TextDialog;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.ActivityFootprintBinding;
import com.mingpinmall.me.ui.adapter.FootprintListAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.FootprintBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

/**
 * 功能描述：我的足迹
 * 创建人：小斌
 * 创建时间: 2019/3/27
 **/
@Route(path = ARouterConfig.Me.FOOTPRINTACTIVITY)
public class FootprintActivity extends AbsLifecycleActivity<ActivityFootprintBinding, MeViewModel> {

    private boolean isLoadmore;
    private int pageIndex = 1;
    private FootprintListAdapter footprintListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_footprint;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTitle(getString(R.string.title_footprintActivity));
        tvRight.setText(getString(R.string.text_clearn));
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setOnClickListener(this);

        footprintListAdapter = new FootprintListAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(footprintListAdapter);

        binding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isLoadmore = true;
                mViewModel.getMyFootprint(pageIndex + 1);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isLoadmore = false;
                mViewModel.getMyFootprint(1);
            }
        });

        footprintListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //item点击事件
                FootprintBean.GoodsbrowseListBean bean = footprintListAdapter.getItem(position);
                ActivityToActivity.toActivity(ARouterConfig.home.SHOPPINGDETAILSACTIVITY, "id", bean.getGoods_id());
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mViewModel.getMyFootprint(1);
    }

    @Override
    protected void dataObserver() {
        registerObserver("GET_FOOTPRINT", "success")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        BaseResponse<FootprintBean> data = (BaseResponse<FootprintBean>) result;
                        if (isLoadmore) {
                            pageIndex++;
                            binding.refreshLayout.finishLoadMore();
                            footprintListAdapter.addData(data.getData().getGoodsbrowse_list());
                        } else {
                            pageIndex = 1;
                            binding.refreshLayout.finishRefresh();
                            footprintListAdapter.setNewData(data.getData().getGoodsbrowse_list());
                        }
                        if (!data.isHasmore()) {
                            binding.refreshLayout.setNoMoreData(true);
                        }
                    }
                });
        registerObserver("GET_FOOTPRINT", "err", String.class)
                .observeForever(new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String msg) {
                        ToastUtils.showShort(msg);
                        //获取失败
                        binding.refreshLayout.finishRefresh(false);
                        binding.refreshLayout.finishLoadMore(false);
                    }
                });
        registerObserver("CLEAR_FOOTPRINT", "success")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object result) {
                        //清空成功
                        footprintListAdapter.setNewData(new ArrayList<FootprintBean.GoodsbrowseListBean>());
                    }
                });
        registerObserver("CLEAR_FOOTPRINT", "err", String.class)
                .observeForever(new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String msg) {
                        //清空失败
                        ToastUtils.showShort(msg);
                    }
                });
    }

    @Override
    protected Object getStateEventKey() {
        return "FOOT_PRINT_ACTIVTY";
    }

    @Override
    public void onViewClicked(int viewId) {
        if (viewId == R.id.tv_right) {
            //清空记录
            TextDialog.showBaseDialog(activity, "提示", "确定清空足迹？", new TextDialog.SingleButtonCallback() {
                @Override
                public void onClick() {
                    mViewModel.clearnMyFootprint();
                }
            }).show();
        }
    }
}
