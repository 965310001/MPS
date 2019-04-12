package com.mingpinmall.me.ui.acitivity.order.PhysicalObject;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentDefaultRecyclerviewBinding;
import com.mingpinmall.me.ui.acitivity.order.OrderActivity;
import com.mingpinmall.me.ui.adapter.AddressListAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * 功能描述：实物订单-全部待付款页面
 * 创建人：小斌
 * 创建时间: 2019/3/26
 **/
public class WaitPayPhysicalFragment extends AbsLifecycleFragment<FragmentDefaultRecyclerviewBinding, MeViewModel> {

    private int pageIndex = 1;
    private final String TYPE = "state_new";

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
        binding.recyclerView.setAdapter(new AddressListAdapter());
        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getOrderList((String) getStateEventKey(), TYPE, ((OrderActivity) activity).getOrderKey(), 1);
            }
        });
        binding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getOrderList((String) getStateEventKey(), TYPE, ((OrderActivity) activity).getOrderKey(), (pageIndex + 1));
            }
        });
    }

    @Override
    protected void dataObserver() {
        registerObserver(getStateEventKey().toString(), "refresh success")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object o) {
                        //刷新成功
                        binding.refreshLayout.finishRefresh();
                        pageIndex = 1;
                    }
                });
        registerObserver(getStateEventKey().toString(), "refresh err")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object o) {
                        //刷新失败
                        binding.refreshLayout.finishRefresh(false);
                    }
                });
        registerObserver(getStateEventKey().toString(), "loadmore success")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object o) {
                        //获取更多成功
                        binding.refreshLayout.finishLoadMore();
                        pageIndex++;
                    }
                });
        registerObserver(getStateEventKey().toString(), "loadmore err")
                .observeForever(new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object o) {
                        //获取更多失败
                        binding.refreshLayout.finishLoadMore(false);
                    }
                });
    }

    @Override
    protected Object getStateEventKey() {
        return "PAY_PHYSICAL";
    }
}
