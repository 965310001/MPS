package com.mingpinmall.me.ui.acitivity.property.couponFragment;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.goldze.common.dmvvm.base.bean.BaseResponse;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.BaseRecyclerviewBinding;
import com.mingpinmall.me.ui.adapter.CouponListAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.CouponListBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * 功能描述：已领取的店铺代金券列表
 * 创建人：小斌
 * 创建时间: 2019/4/20
 **/
public class CouponListFragment extends AbsLifecycleFragment<BaseRecyclerviewBinding, MeViewModel> {

    private int pageIndex = 1;
    private boolean isLoadmore = false;
    private CouponListAdapter listAdapter;

    public static CouponListFragment newFragment() {
        return new CouponListFragment();
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        listAdapter = new CouponListAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerView.setAdapter(listAdapter);

        binding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isLoadmore = true;
                mViewModel.getCouponList(pageIndex + 1);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isLoadmore = false;
                lazyLoad();
            }
        });
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver("REFRESH_COUPON", String.class).observeForever(new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                lazyLoad();
            }
        });
        registerObserver("RCCharge_LIST", "success").observeForever(new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object result) {
                BaseResponse<CouponListBean> data = (BaseResponse<CouponListBean>) result;
                if (!data.isHasmore()) {
                    binding.refreshLayout.finishLoadMoreWithNoMoreData();
                }
                if (isLoadmore) {
                    binding.refreshLayout.finishLoadMore();
                    pageIndex++;
                    //TODO 需要对数据进行处理
                    listAdapter.addData(data.getData().getList());
                } else {
                    binding.refreshLayout.finishRefresh();
                    pageIndex = 1;
                    //TODO 需要对数据进行处理
                    listAdapter.setNewData(data.getData().getList());
                }
            }
        });
        registerObserver("RCCharge_LIST", "err", String.class).observeForever(new Observer<String>() {
            @Override
            public void onChanged(@Nullable String msg) {
                ToastUtils.showShort(msg);
                if (isLoadmore) {
                    binding.refreshLayout.finishLoadMore(false);
                } else {
                    binding.refreshLayout.finishRefresh(false);
                }
            }
        });
    }

    @Override
    protected void lazyLoad() {
        mViewModel.getCouponList(1);
    }

    @Override
    protected Object getStateEventKey() {
        return "CouponListFragment";
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.base_recyclerview;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }
}
