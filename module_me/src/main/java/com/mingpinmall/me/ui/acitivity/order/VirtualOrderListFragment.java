package com.mingpinmall.me.ui.acitivity.order;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentDefaultRecyclerviewBinding;
import com.mingpinmall.me.ui.adapter.VirtualOrderListAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.VirtualOrderBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * 功能描述：全部虚拟订单页面
 * 创建人：小斌
 * 创建时间: 2019/3/26
 **/
public class VirtualOrderListFragment extends AbsLifecycleFragment<FragmentDefaultRecyclerviewBinding, MeViewModel> {

    private int pageIndex = 1;
    private String TYPE = "";
    private String EVENT_KEY = "ALL_VIRTUAL";
    private VirtualOrderListAdapter virtualOrderListAdapter;

    public VirtualOrderListFragment() {
    }

    public static VirtualOrderListFragment newInstance(String type, String eventKey) {
        Bundle bundle = new Bundle();
        bundle.putString("TYPE", type);
        bundle.putString("EVENT_KEY", eventKey);

        VirtualOrderListFragment fragment = new VirtualOrderListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        TYPE = args.getString("TYPE");
        EVENT_KEY = args.getString("EVENT_KEY");
        Log.i("网络请求", "setArguments: " + TYPE + " & " + EVENT_KEY);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_default_recyclerview;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        virtualOrderListAdapter = new VirtualOrderListAdapter();
        binding.recyclerView.setAdapter(virtualOrderListAdapter);

        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getVirtualOrderList(EVENT_KEY, TYPE, ((OrderActivity) activity).getOrderKey(), 1);
            }
        });
        binding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getVirtualOrderList(EVENT_KEY, TYPE, ((OrderActivity) activity).getOrderKey(), (pageIndex + 1));
            }
        });
        setListItemClickListener();
    }

    private void setListItemClickListener() {
        virtualOrderListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //item点击事件
            }
        });
        virtualOrderListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //子控件点击事件
            }
        });
    }

    @Override
    protected void dataObserver() {
        registerObserver(EVENT_KEY, EVENT_KEY + "success").observeForever(new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object result) {
                BaseResponse<VirtualOrderBean> data = (BaseResponse<VirtualOrderBean>) result;
                if (binding.refreshLayout.getState() == RefreshState.Refreshing || binding.refreshLayout.getState() == RefreshState.None) {
                    pageIndex = 1;
                    binding.refreshLayout.finishRefresh();
                    virtualOrderListAdapter.setNewData(data.getData().getOrder_list());
                } else if (binding.refreshLayout.getState() == RefreshState.Loading) {
                    pageIndex++;
                    if (data.isHasmore())
                        binding.refreshLayout.finishLoadMore();
                    else
                        binding.refreshLayout.finishLoadMoreWithNoMoreData();
                    virtualOrderListAdapter.addData(data.getData().getOrder_list());
                }
            }
        });
        registerObserver(EVENT_KEY, EVENT_KEY + "err").observeForever(new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object o) {
                if (binding.refreshLayout.getState() == RefreshState.Refreshing) {
                    binding.refreshLayout.finishRefresh(false);
                } else if (binding.refreshLayout.getState() == RefreshState.Loading) {
                    binding.refreshLayout.finishLoadMore(false);
                }
            }
        });
    }

    @Override
    protected void lazyLoad() {
        mViewModel.getVirtualOrderList(EVENT_KEY, TYPE, ((OrderActivity) activity).getOrderKey(), 1);
    }

    @Override
    protected Object getStateEventKey() {
        return "VirtualOrderListFragment";
    }
}
